package com.nico.proyecto.service;

import java.util.List;

import com.nico.proyecto.entity.Turno;

public interface ITurnoService {

	List<Turno> getAll();
	
	Turno getById(Long id);
	
	Turno save(Turno turno);
	
	void delete(Long id);
	
	boolean exists(Long id);
	
	List<Turno> encontrarPorCliente(Long id);
}
