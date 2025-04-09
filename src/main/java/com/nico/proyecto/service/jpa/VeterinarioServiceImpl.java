package com.nico.proyecto.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.proyecto.entity.Veterinario;
import com.nico.proyecto.repository.VeterinarioRepository;
import com.nico.proyecto.service.IVeterinarioService;

@Service
public class VeterinarioServiceImpl implements IVeterinarioService{

	@Autowired
	private VeterinarioRepository repo;
	
	@Override
	public List<Veterinario> getAll() {
		return repo.findAll();
	}

	@Override
	public Veterinario getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Veterinario save(Veterinario veterinario) {
		return repo.save(veterinario);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public boolean exists(Long id) {
		return id == null ? false : repo.existsById(id);
	}

}
