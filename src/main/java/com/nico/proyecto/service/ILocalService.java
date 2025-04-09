package com.nico.proyecto.service;

import java.util.List;

import com.nico.proyecto.entity.Local;

public interface ILocalService {

	List<Local> getAll();
	
	Local getById(Long id);
	
	Local save(Local local);
	
	void delete(Long id);
	
	boolean exists(Long id);
}
