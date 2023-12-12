package cibertec.edu.pe.jwt.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cibertec.edu.pe.jwt.services.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization");
		
		if(token != null && !token.isBlank() && token.startsWith(JWTUtils.PREFIX_TOKEN)) {
			UsernamePasswordAuthenticationToken username = this.jwtUtils.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(username);
		}
		filterChain.doFilter(request, response);
	}

}
