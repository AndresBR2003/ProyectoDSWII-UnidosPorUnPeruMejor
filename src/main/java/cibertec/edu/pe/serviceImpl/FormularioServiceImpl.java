package cibertec.edu.pe.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.edu.pe.models.Formulario;
import cibertec.edu.pe.models.Programa;
import cibertec.edu.pe.models.Usuario;
import cibertec.edu.pe.repositories.FormularioRepository;
import cibertec.edu.pe.services.FormularioService;

@Service
public class FormularioServiceImpl implements FormularioService{
	
	@Autowired
	private FormularioRepository formuRepo;

	@Override
	public Formulario save(Formulario formulario) {
		// TODO Auto-generated method stub
		return formuRepo.save(formulario);
	}


	@Override
	public Formulario findByUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return formuRepo.findByUsuario(usuario);
	}


	@Override
	public List<Formulario> listFormsByProgram(Programa idPro) {
		// TODO Auto-generated method stub
		return formuRepo.findByPrograma(idPro);
	}


	@Override
	public Formulario findById(Long id) {
		return formuRepo.findById(id).get();
	}

	
	

}
