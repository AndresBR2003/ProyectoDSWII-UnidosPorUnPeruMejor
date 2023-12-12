package cibertec.edu.pe.services;

import java.util.List;

import cibertec.edu.pe.models.Formulario;
import cibertec.edu.pe.models.Programa;
import cibertec.edu.pe.models.Usuario;

public interface FormularioService {

	public Formulario save(Formulario formulario);
	public Formulario findByUsuario(Usuario usuario);
	public List<Formulario> listFormsByProgram(Programa idPro);
	public Formulario findById(Long id);
}
