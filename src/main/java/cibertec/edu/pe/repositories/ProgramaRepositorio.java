package cibertec.edu.pe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cibertec.edu.pe.models.Programa;

public interface ProgramaRepositorio extends JpaRepository<Programa, Long>{
	
	@Query("SELECT p FROM Programa p WHERE p.estadoPro = true")
	public List<Programa> findByEstadoPro();
	
	public Programa findByNombrePro(String nombrePro);

	
}
