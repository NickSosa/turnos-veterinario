package com.nico.proyecto.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.proyecto.entity.Local;
import com.nico.proyecto.repository.LocalRepository;
import com.nico.proyecto.service.ILocalService;

@Service
public class LocalServiceImpl implements ILocalService{

	@Autowired
	private LocalRepository repo;
	
	@Override
	public List<Local> getAll() {
		return repo.findAll();
	}

	@Override
	public Local getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Local save(Local local) {
		return repo.save(local);
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
