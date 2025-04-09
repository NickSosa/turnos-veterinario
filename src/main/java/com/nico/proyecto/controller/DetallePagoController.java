package com.nico.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nico.proyecto.entity.DetallePago;
import com.nico.proyecto.service.IDetallePagoService;
import com.nico.proyecto.util.APIResponse;
import com.nico.proyecto.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/detallepago")
public class DetallePagoController {

	@Autowired
	private IDetallePagoService service;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<DetallePago>>> getAllDetallepago() {
		List<DetallePago> detalles = service.getAll();
		return detalles.isEmpty() ? ResponseUtil.notFound("No se encontraron detalles de pago") :
			ResponseUtil.success(detalles);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<DetallePago>> getDetallepagoById(@PathVariable("id") Long id) {
		return service.exists(id) ? ResponseUtil.success(service.getById(id)) :
			ResponseUtil.notFound("No se encontró detalles de pago con el id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<DetallePago>> createDetallepago(@Valid @RequestBody DetallePago detallepago, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al crear detalles de pago");}
		return service.exists(detallepago.getId()) ? ResponseUtil.badRequest("Ya existe un cliente con la id {0}", detallepago.getId()) :
			ResponseUtil.success(service.save(detallepago));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<APIResponse<DetallePago>> updateDetallepago(@Valid @RequestBody DetallePago detallepago, @PathVariable("id") Long id, BindingResult result) {
		detallepago.setId(id);
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al actualizar detalles de pago");}
		return service.exists(id) ? ResponseUtil.success(service.save(detallepago)) :
			ResponseUtil.badRequest("No existe detalles de pago con la id {0}", id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<DetallePago>> deleteDetallepago(@PathVariable("id") Long id) {
		if (service.exists(id)) {
			service.delete(id);
			return ResponseUtil.successDeleted("Se eliminó exitosamente detalles de pago con la id {0}", id);}
		else {
			return ResponseUtil.badRequest("Error al eliminar, no existe detalles de pago con la id {0}", id);}
	}
}
