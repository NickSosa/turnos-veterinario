package com.nico.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Persona extends BaseEntity{

	//@NotNull(message = "No puede estar en blanco")
	private String nombre;
	
	//@NotNull(message = "No puede estar en blanco")
	private String apellido;
	
	//@NotNull(message = "No puede estar en blanco")
	private String dni;
	
	//@NotNull(message = "No puede estar en blanco")
	private String telefono;
	
	//@NotBlank(message = "No puede estar en blanco")
	private String mail;
	
	//@NotNull(message = "No puede estar en blanco")
	private String direccion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
