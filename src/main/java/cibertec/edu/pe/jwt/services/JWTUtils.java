package cibertec.edu.pe.jwt.services;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

	public String ACCESS_TOKEN_SECRET;
	public static final long EXPIRATION_DATE = 14000000L;
	public static final String PREFIX_TOKEN = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public SecretKey FINAL_KEY;
	
	@Autowired
	public void getAccessToken(@Value("${security.jwt.secret-key}") String secret) {
		this.ACCESS_TOKEN_SECRET = secret;
		this.FINAL_KEY = Keys.hmacShaKeyFor(this.ACCESS_TOKEN_SECRET.getBytes());
	}
	
	public String create(String username, List<String> roles) throws IOException{
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);
		claims.put("roles", roles);
		
		String token = Jwts.builder().subject(username).claims(claims)
				.signWith(FINAL_KEY)
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)).compact();
		
		return token;
	}
	
	public Claims getClaims(String token) {
		Claims claims = Jwts.parser().verifyWith(FINAL_KEY).build()
				.parseSignedClaims(resolve(token)).getPayload();
		return claims;
	}
	
	public String resolve(String token) {
		if(token != null && token.startsWith(PREFIX_TOKEN)) {
			return token.replace(PREFIX_TOKEN,"");
		}
		return null;
	}
	
	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		try {			
			Claims claims = this.getClaims(token);
			String username = claims.getSubject();
			List<GrantedAuthority> authorities = extractAuthorities(claims); 
			return new UsernamePasswordAuthenticationToken(username, null, authorities);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	private List<GrantedAuthority> extractAuthorities(Claims claims) {
	    // Extraer roles del token y convertirlos en objetos GrantedAuthority
	    List<String> roles = claims.get("roles", List.class);

	    if (roles != null) {
	        return roles.stream()
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList());
	    }

	    return Collections.emptyList();
	}
}
