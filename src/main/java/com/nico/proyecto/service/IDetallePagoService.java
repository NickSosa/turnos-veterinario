package com.nico.proyecto.service;

import java.util.List;

import com.nico.proyecto.entity.DetallePago;

public interface IDetallePagoService {

	List<DetallePago> getAll();
	
	DetallePago getById(Long id);
	
	DetallePago save(DetallePago detallepago);
	
	void delete(Long id);
	
	boolean exists(Long id);
}
