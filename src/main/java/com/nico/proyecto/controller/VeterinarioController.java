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

import com.nico.proyecto.entity.Veterinario;
import com.nico.proyecto.service.IVeterinarioService;
import com.nico.proyecto.util.APIResponse;
import com.nico.proyecto.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/veterinario")
public class VeterinarioController {

	@Autowired
	private IVeterinarioService service;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Veterinario>>> getAllVeterinarios() {
		List<Veterinario> veterinarios = service.getAll();
		return veterinarios.isEmpty() ? ResponseUtil.notFound("No se encontraron veterinarios") :
			ResponseUtil.success(veterinarios);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Veterinario>> getVeterinarioById(@PathVariable("id") Long id) {
		return service.exists(id) ? ResponseUtil.success(service.getById(id)) :
			ResponseUtil.notFound("No se encontró al veterinario con el id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<Veterinario>> createVeterinario(@Valid @RequestBody Veterinario veterinario, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al crear cliente");}
		return service.exists(veterinario.getId()) ? ResponseUtil.badRequest("Ya existe un veterinario con la id {0}", veterinario.getId()) :
			ResponseUtil.success(service.save(veterinario));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<APIResponse<Veterinario>> updateVeterinario(@Valid @RequestBody Veterinario veterinario, @PathVariable("id") Long id, BindingResult result) {
		veterinario.setId(id);
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al actualizar veterinario");}
		return service.exists(id) ? ResponseUtil.success(service.save(veterinario)) :
			ResponseUtil.badRequest("No existe un veterinario con la id {0}", id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Veterinario>> deleteVeterinario(@PathVariable("id") Long id) {
		if (service.exists(id)) {
			service.delete(id);
			return ResponseUtil.successDeleted("Se eliminó exitosamente el veterinario con la id {0}", id);}
		else {
			return ResponseUtil.badRequest("Error al eliminar, no existe un veterinario con la id {0}", id);}
	}
}
