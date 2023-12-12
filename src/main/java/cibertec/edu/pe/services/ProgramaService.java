package cibertec.edu.pe.services;

import java.util.List;

import cibertec.edu.pe.models.Programa;

public interface ProgramaService {

	public List<Programa> listAllActivos();
	public List<Programa> listAll();
	public Programa save(Programa programa);
	public Programa get(Long idPro);
	public void delete(Long idPro);
	public Programa getByNombre(String nombre);
	
}
