package com.nico.proyecto.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.proyecto.entity.Cliente;
import com.nico.proyecto.repository.ClienteRepository;
import com.nico.proyecto.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public List<Cliente> getAll() {
		return repo.findAll();
	}

	@Override
	public Cliente getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Cliente save(Cliente cliente) {
		return repo.save(cliente);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public boolean exists(Long id) {
		return id == null ? false : repo.existsById(id);
	}

	@Override
	public Cliente encontrarPorDni(String dni) {
		return repo.findByDniLike(dni);
	}

}
