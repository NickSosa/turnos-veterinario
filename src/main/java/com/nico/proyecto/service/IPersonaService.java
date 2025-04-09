package com.nico.proyecto.service;

import java.util.List;

import com.nico.proyecto.entity.Persona;

public interface IPersonaService {

	List<Persona> getAll();
	
	Persona getById(Long id);
	
	Persona save(Persona persona);
	
	void delete(Long id);
	
	boolean exists(Long id);
}
