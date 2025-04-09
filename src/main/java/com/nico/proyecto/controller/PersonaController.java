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

import com.nico.proyecto.entity.Persona;
import com.nico.proyecto.service.IPersonaService;
import com.nico.proyecto.util.APIResponse;
import com.nico.proyecto.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/persona")
public class PersonaController {

	@Autowired
	private IPersonaService service;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Persona>>> getAllPersonas() {
		List<Persona> personas = service.getAll();
		return personas.isEmpty() ? ResponseUtil.notFound("No se encontraron personas") :
			ResponseUtil.success(personas);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Persona>> getPersonaById(@PathVariable("id") Long id) {
		return service.exists(id) ? ResponseUtil.success(service.getById(id)) :
			ResponseUtil.notFound("No se encontró persona con el id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<Persona>> createPersona(@Valid @RequestBody Persona persona, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al crear cliente");}
		return service.exists(persona.getId()) ? ResponseUtil.badRequest("Ya existe una persona con la id {0}", persona.getId()) :
			ResponseUtil.success(service.save(persona));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<APIResponse<Persona>> updatePersona(@Valid @RequestBody Persona persona, @PathVariable("id") Long id, BindingResult result) {
		persona.setId(id);
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al actualizar persona");}
		return service.exists(id) ? ResponseUtil.success(service.save(persona)) :
			ResponseUtil.badRequest("No existe una persona con la id {0}", id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Persona>> deletePersona(@PathVariable("id") Long id) {
		if (service.exists(id)) {
			service.delete(id);
			return ResponseUtil.successDeleted("Se eliminó exitosamente la persona con la id {0}", id);}
		else {
			return ResponseUtil.badRequest("Error al eliminar, no existe una persona con la id {0}", id);}
	}
}
