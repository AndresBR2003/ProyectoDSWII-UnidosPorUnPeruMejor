package cibertec.edu.pe.services;

import java.util.List;

import cibertec.edu.pe.models.Donacion;

public interface DonacionesService {

	public Donacion save(Donacion donacion);
	
	public List<Donacion> findAll();
}
