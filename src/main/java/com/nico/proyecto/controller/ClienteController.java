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

import com.nico.proyecto.entity.Cliente;
import com.nico.proyecto.service.IClienteService;
import com.nico.proyecto.util.APIResponse;
import com.nico.proyecto.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="/api/cliente")
public class ClienteController {

	@Autowired
	private IClienteService service;
	
	@GetMapping
	public ResponseEntity<APIResponse<List<Cliente>>> getAllClientes() {
		List<Cliente> clientes = service.getAll();
		return clientes.isEmpty() ? ResponseUtil.notFound("No se encontraron clientes") :
			ResponseUtil.success(clientes);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Cliente>> getClienteById(@PathVariable("id") Long id) {
		return service.exists(id) ? ResponseUtil.success(service.getById(id)) :
			ResponseUtil.notFound("No se encontró el cliente con el id {0}", id);
	}
	
	@PostMapping
	public ResponseEntity<APIResponse<Cliente>> createCliente(@Valid @RequestBody Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al crear cliente");}
		return service.exists(cliente.getId()) ? ResponseUtil.badRequest("Ya existe un cliente con la id {0}", cliente.getId()) :
			ResponseUtil.success(service.save(cliente));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<APIResponse<Cliente>> updateCliente(@Valid @RequestBody Cliente cliente, @PathVariable("id") Long id, BindingResult result) {
		cliente.setId(id);
		if (result.hasErrors()) {
			return ResponseUtil.badRequest("Error de validación al actualizar cliente");}
		return service.exists(id) ? ResponseUtil.success(service.save(cliente)) :
			ResponseUtil.badRequest("No existe un cliente con la id {0}", id);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Cliente>> deleteCliente(@PathVariable("id") Long id) {
		if (service.exists(id)) {
			service.delete(id);
			return ResponseUtil.successDeleted("Se eliminó exitosamente el cliente con la id {0}", id);}
		else {
			return ResponseUtil.badRequest("Error al eliminar, no existe un cliente con la id {0}", id);}
	}
	
	@GetMapping("/dni/{dni}")
	public ResponseEntity<APIResponse<Cliente>> getClienteByDni(@PathVariable("dni") String dni) {
		return ResponseUtil.success(service.encontrarPorDni(dni));
	}
}
