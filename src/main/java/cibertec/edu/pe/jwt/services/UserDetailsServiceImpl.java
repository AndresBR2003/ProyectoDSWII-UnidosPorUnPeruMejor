package cibertec.edu.pe.jwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cibertec.edu.pe.jwt.entities.UserDetailsImpl;
import cibertec.edu.pe.models.Usuario;
import cibertec.edu.pe.repositories.UsuarioRepositorio;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UsuarioRepositorio usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		try {
			if(!username.isBlank()) {
				Usuario user = this.usuarioRepository.findByUsuarioEmail(username);
				return new UserDetailsImpl(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return null;
	}

}
