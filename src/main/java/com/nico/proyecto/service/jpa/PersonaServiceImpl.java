package com.nico.proyecto.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.proyecto.entity.Persona;
import com.nico.proyecto.repository.PersonaRepository;
import com.nico.proyecto.service.IPersonaService;

@Service
public class PersonaServiceImpl implements IPersonaService{

	@Autowired
	private PersonaRepository repo;
	
	@Override
	public List<Persona> getAll() {
		return repo.findAll();
	}

	@Override
	public Persona getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Persona save(Persona persona) {
		return repo.save(persona);
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
