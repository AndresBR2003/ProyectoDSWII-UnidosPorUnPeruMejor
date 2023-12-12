package cibertec.edu.pe.serviceImpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cibertec.edu.pe.DTO.UsuarioDTO;
import cibertec.edu.pe.models.Rol;
import cibertec.edu.pe.models.Usuario;
import cibertec.edu.pe.repositories.RolRepositorio;
import cibertec.edu.pe.repositories.UsuarioRepositorio;
import cibertec.edu.pe.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private RolRepositorio rolRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Usuario guardar(UsuarioDTO registro) {
		
		String role = registro.getRol();
		
		try {
			if(role.equals("ROL_ADMIN") || role.equals("ROL_JEFE") || role.equals("ROL_USER")) {
				
				Rol rolUser = rolRepositorio.findByNombre(role);
				
				Usuario usuario = new Usuario(registro.getNombres(), 
						registro.getApellidos(),
						registro.getDni_ce(),
						registro.getCelular(),
						registro.getEmail(),
						passwordEncoder.encode(registro.getPassword()),
						Arrays.asList(rolUser));
				return usuarioRepositorio.save(usuario);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
				
		
	}

	@Override
	public Usuario actualizar(Usuario usuario) {
		return usuarioRepositorio.save(usuario);
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepositorio.findByUsuarioEmail(email);
	}

	@Override
	public Usuario get(Long id) {
		return usuarioRepositorio.findById(id).get();
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return usuarioRepositorio.findAll();
	}

}
