package com.nico.proyecto.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.proyecto.entity.Mascota;
import com.nico.proyecto.repository.MascotaRepository;
import com.nico.proyecto.service.IMascotaService;

@Service
public class MascotaServiceImpl implements IMascotaService{

	@Autowired
	private MascotaRepository repo;
	
	@Override
	public List<Mascota> getAll() {
		return repo.findAll();
	}

	@Override
	public Mascota getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Mascota save(Mascota mascota) {
		return repo.save(mascota);
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
