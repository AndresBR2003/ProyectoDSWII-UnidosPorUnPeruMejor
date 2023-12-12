package cibertec.edu.pe.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.edu.pe.models.Donacion;
import cibertec.edu.pe.repositories.DonacionRepositorio;
import cibertec.edu.pe.services.DonacionesService;

@Service
public class DonacionesServiceImpl implements DonacionesService{
	
	@Autowired
	private DonacionRepositorio donaRepo;



	@Override
	public Donacion save(Donacion donacion) {
		return donaRepo.save(donacion);
	}



	@Override
	public List<Donacion> findAll() {
		return donaRepo.findAll();
	}

}
