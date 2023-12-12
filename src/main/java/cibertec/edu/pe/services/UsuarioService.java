package cibertec.edu.pe.services;

import java.util.List;

import cibertec.edu.pe.DTO.UsuarioDTO;
import cibertec.edu.pe.models.Usuario;

public interface UsuarioService {

	public Usuario guardar(UsuarioDTO registro);
	
	public Usuario actualizar (Usuario usuario);
	
	public Usuario findByEmail(String email);
	
	public Usuario get(Long id);
	
	public List<Usuario> listarUsuarios();
}
