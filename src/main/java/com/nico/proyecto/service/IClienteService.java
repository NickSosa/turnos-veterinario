package com.nico.proyecto.service;

import java.util.List;

import com.nico.proyecto.entity.Cliente;

public interface IClienteService {

	List<Cliente> getAll();
	
	Cliente getById(Long id);
	
	Cliente save(Cliente cliente);
	
	void delete(Long id);
	
	boolean exists(Long id);
	
	Cliente encontrarPorDni(String dni);
}
