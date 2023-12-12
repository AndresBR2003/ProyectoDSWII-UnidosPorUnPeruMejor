package cibertec.edu.pe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cibertec.edu.pe.jwt.filters.JWTAuthenticationFilter;
import cibertec.edu.pe.jwt.filters.JWTAuthorizationFilter;
import cibertec.edu.pe.jwt.services.JWTUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTAuthorizationFilter authorizationFilter;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Bean
	static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager auth) throws Exception{
		JWTAuthenticationFilter jwtAuthFilter = new JWTAuthenticationFilter(jwtUtils);
		jwtAuthFilter.setAuthenticationManager(auth);
		jwtAuthFilter.setFilterProcessesUrl("/login");
		
		return http.csrf(crsf -> crsf.disable())
				.authorizeHttpRequests(request -> request
				.requestMatchers("/registro/**").permitAll()
				.requestMatchers("/programa/desactivar").hasAnyAuthority("ROL_ADMIN")
				.requestMatchers("/programa/actualizar").hasAnyAuthority("ROL_ADMIN")
				.requestMatchers("/programa/registrar").hasAnyAuthority("ROL_ADMIN")
				.requestMatchers("/programa/listadoAll").hasAnyAuthority("ROL_ADMIN")
				.requestMatchers("/programa/listadoActivos").hasAnyAuthority("ROL_USER", "ROL_ADMIN")
				.requestMatchers("/formulario/guardar").hasAnyAuthority("ROL_USER")
				.requestMatchers("/formulario/listadoXprograma/**").hasAnyAuthority("ROL_JEFE", "ROL_ADMIN")
				.requestMatchers("/formulario/actualizarEstadoFormulario/**").hasAnyAuthority("ROL_JEFE")
				.requestMatchers("/donacion/donar").hasAnyAuthority("ROL_USER")
				.requestMatchers("/donacion/listado").hasAnyAuthority("ROL_ADMIN")
				.anyRequest().authenticated())
				.addFilter(jwtAuthFilter)
				.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();
	}
	
	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder())
				.and()
				.build();
	}
}
