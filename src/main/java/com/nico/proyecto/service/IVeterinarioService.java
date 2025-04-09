package com.nico.proyecto.service;

import java.util.List;

import com.nico.proyecto.entity.Veterinario;

public interface IVeterinarioService {

	List<Veterinario> getAll();
	
	Veterinario getById(Long id);
	
	Veterinario save(Veterinario veterinario);
	
	void delete(Long id);
	
	boolean exists(Long id);
}
