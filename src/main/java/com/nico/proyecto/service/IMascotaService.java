package com.nico.proyecto.service;

import java.util.List;

import com.nico.proyecto.entity.Mascota;

public interface IMascotaService {

	List<Mascota> getAll();
	
	Mascota getById(Long id);
	
	Mascota save(Mascota mascota);
	
	void delete(Long id);
	
	boolean exists(Long id);
}
