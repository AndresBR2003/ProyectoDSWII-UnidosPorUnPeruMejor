package cibertec.edu.pe.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cibertec.edu.pe.models.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long>{

	Rol findByNombre(String nombre);
	
	@Query("SELECT r FROM Rol r where r.nombre = ?1")
	Collection<Rol> findByNombre1(String name);
}
