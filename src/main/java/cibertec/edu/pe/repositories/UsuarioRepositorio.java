package cibertec.edu.pe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cibertec.edu.pe.models.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{

	public Usuario findByUsuarioEmail(String email);
	
	public List<Usuario> findAll();
}
