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

import com.nico.proyecto.entity.Mascota;
import com.nico.proyecto.service.IMascotaService;
import com.nico.proyecto.util.APIResponse;
import com.nico.proyecto.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/mascota")
public class MascotaController {

	@Autowired
	private IMascotaService service;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Mascota>>> getAllMascotas() {
		List<Mascota> mascotas = service.getAll();
		return mascotas.isEmpty() ? ResponseUtil.notFound("No se encontraron mascotas") :
			ResponseUtil.success(mascotas);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Mascota>> getMascotaById(@PathVariable("id") Long id) {
		return service.exists(id) ? ResponseUtil.success(service.getById(id)) :
			ResponseUtil.notFound("No se encontró la mascota con el id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<Mascota>> createMascota(@Valid @RequestBody Mascota mascota, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al crear mascota");}
		return service.exists(mascota.getId()) ? ResponseUtil.badRequest("Ya existe una mascota con la id {0}", mascota.getId()) :
			ResponseUtil.success(service.save(mascota));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<APIResponse<Mascota>> updateMascota(@Valid @RequestBody Mascota mascota, @PathVariable("id") Long id, BindingResult result) {
		mascota.setId(id);
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al actualizar mascota");}
		return service.exists(id) ? ResponseUtil.success(service.save(mascota)) :
			ResponseUtil.badRequest("No existe una mascota con la id {0}", id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Mascota>> deleteMascota(@PathVariable("id") Long id) {
		if (service.exists(id)) {
			service.delete(id);
			return ResponseUtil.successDeleted("Se eliminó exitosamente la mascota con la id {0}", id);}
		else {
			return ResponseUtil.badRequest("Error al eliminar, no existe una mascota con la id {0}", id);}
	}
}
