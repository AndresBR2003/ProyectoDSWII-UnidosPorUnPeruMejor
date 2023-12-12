package cibertec.edu.pe.jwt.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cibertec.edu.pe.models.Rol;
import cibertec.edu.pe.models.Usuario;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private final Usuario usuario;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return usuario.getRoles().stream()
                .map(Rol::getNombre)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.usuario.getUsuarioPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.usuario.getUsuarioEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
