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

import com.nico.proyecto.entity.Turno;
import com.nico.proyecto.service.ITurnoService;
import com.nico.proyecto.util.APIResponse;
import com.nico.proyecto.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/turno")
public class TurnoController {

	@Autowired
	private ITurnoService service;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Turno>>> getAllTurnos() {
		List<Turno> turnos = service.getAll();
		return turnos.isEmpty() ? ResponseUtil.notFound("No se encontraron turnos") :
			ResponseUtil.success(turnos);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Turno>> getTurnoById(@PathVariable("id") Long id) {
		return service.exists(id) ? ResponseUtil.success(service.getById(id)) :
			ResponseUtil.notFound("No se encontró el turnos con el id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<Turno>> createTurno(@Valid @RequestBody Turno turno, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al crear turno");}
		return service.exists(turno.getId()) ? ResponseUtil.badRequest("Ya existe un turno con la id {0}", turno.getId()) :
			ResponseUtil.success(service.save(turno));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<APIResponse<Turno>> updateTurno(@Valid @RequestBody Turno turno, @PathVariable("id") Long id, BindingResult result) {
		turno.setId(id);
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al actualizar turno");}
		return service.exists(id) ? ResponseUtil.success(service.save(turno)) :
			ResponseUtil.badRequest("No existe un turno con la id {0}", id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Turno>> deleteTurno(@PathVariable("id") Long id) {
		if (service.exists(id)) {
			service.delete(id);
			return ResponseUtil.successDeleted("Se eliminó exitosamente el turno con la id {0}", id);}
		else {
			return ResponseUtil.badRequest("Error al eliminar, no existe un turno con la id {0}", id);}
	}
	
	@GetMapping("/cliente/{id}")
	public ResponseEntity<APIResponse<List<Turno>>> getTurnoByCliente(@PathVariable("id") Long id) {
		return ResponseUtil.success(service.encontrarPorCliente(id));
	}
}
