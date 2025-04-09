package com.nico.proyecto.service.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nico.proyecto.entity.DetallePago;
import com.nico.proyecto.repository.DetallePagoRepository;
import com.nico.proyecto.service.IDetallePagoService;

@Service
public class DetallePagoServiceImpl implements IDetallePagoService{

	@Autowired
	private DetallePagoRepository repo;
	
	@Override
	public List<DetallePago> getAll() {
		return repo.findAll();
	}

	@Override
	public DetallePago getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public DetallePago save(DetallePago detallepago) {
		return repo.save(detallepago);
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
