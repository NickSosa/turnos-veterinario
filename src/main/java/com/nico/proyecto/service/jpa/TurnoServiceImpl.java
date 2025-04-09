package com.nico.proyecto.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.proyecto.entity.Turno;
import com.nico.proyecto.repository.TurnoRepository;
import com.nico.proyecto.service.ITurnoService;

@Service
public class TurnoServiceImpl implements ITurnoService{

	@Autowired
	private TurnoRepository repo;
	
	@Override
	public List<Turno> getAll() {
		return repo.findAll();
	}

	@Override
	public Turno getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Turno save(Turno turno) {
		return repo.save(turno);
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
	public List<Turno> encontrarPorCliente(Long id) {
		return repo.findByClienteId(id);
	}

}
