package cibertec.edu.pe.jwt.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cibertec.edu.pe.jwt.entities.UsuarioJWT;
import cibertec.edu.pe.jwt.services.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	@Autowired
	private JWTUtils jwtUtils;
	
	public JWTAuthenticationFilter(JWTUtils jwt) {
		this.jwtUtils = jwt;
	}
	
	
	@Autowired
	@Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		// TODO Auto-generated method stub
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = "";
		String password = "";
		UsuarioJWT user = null;
		try {			
			user = new ObjectMapper().readValue(request.getInputStream(), UsuarioJWT.class);
			username = user.getUsername();
			password = user.getPassword();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		username = username.trim();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
		return getAuthenticationManager().authenticate(authToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException  {
		// TODO Auto-generated method stub
		
		Collection<? extends GrantedAuthority> authorities = ((UserDetails) authResult.getPrincipal()).getAuthorities();
	    List<String> roles = authorities.stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.toList());
		String token = jwtUtils.create(((UserDetails) authResult.getPrincipal()).getUsername(),roles);
		
		response.addHeader(JWTUtils.HEADER_STRING, JWTUtils.PREFIX_TOKEN + token);
		Map<String, Object> body = new HashMap<String,Object>();
		body.put("token", token);
		body.put("user", (UserDetails) authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, has iniciado sesion con exito!", ((UserDetails) authResult.getPrincipal()).getUsername()));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");		
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Map<String, Object> body = new HashMap<String,Object>();		
		body.put("mensaje", "Error de Autenticacion: username o password erroneos");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");		
	}
}
