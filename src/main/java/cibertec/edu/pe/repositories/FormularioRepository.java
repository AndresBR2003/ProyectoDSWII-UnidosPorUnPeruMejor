package cibertec.edu.pe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cibertec.edu.pe.models.Formulario;
import cibertec.edu.pe.models.Programa;
import cibertec.edu.pe.models.Usuario;

public interface FormularioRepository extends JpaRepository<Formulario, Long>{


	Formulario findByUsuario(Usuario usuario);

	@Query("SELECT f FROM Formulario f WHERE f.estado = true AND f.programa = ?1")
	List<Formulario> findByPrograma(Programa idPro);

}
