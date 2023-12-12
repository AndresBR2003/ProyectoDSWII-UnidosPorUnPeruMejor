package cibertec.edu.pe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cibertec.edu.pe.models.Donacion;
import cibertec.edu.pe.models.Usuario;

public interface DonacionRepositorio extends JpaRepository<Donacion, Long>{

	List<Donacion> findByUsuario(Usuario usuario);
	
}
