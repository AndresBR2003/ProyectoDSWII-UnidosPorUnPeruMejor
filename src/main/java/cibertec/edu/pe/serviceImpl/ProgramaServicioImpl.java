package cibertec.edu.pe.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.edu.pe.models.Programa;
import cibertec.edu.pe.repositories.ProgramaRepositorio;
import cibertec.edu.pe.services.ProgramaService;

@Service
public class ProgramaServicioImpl implements ProgramaService{

	@Autowired
	private ProgramaRepositorio programaRepo;

	
	@Override
	public List<Programa> listAllActivos() {
		return programaRepo.findByEstadoPro();
	}

	@Override
	public Programa save(Programa programa) {
		return programaRepo.save(programa);
		
	}

	@Override
	public Programa get(Long idPro) {
		return programaRepo.findById(idPro).get();
	}

	@Override
	public void delete(Long idPro) {
		programaRepo.deleteById(idPro);
		
	}

	@Override
	public List<Programa> listAll() {
		return programaRepo.findAll();
	}

	@Override
	public Programa getByNombre(String nombrePro) {
		return programaRepo.findByNombrePro(nombrePro);
	}

}
