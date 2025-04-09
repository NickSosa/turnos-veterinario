package com.nico.proyecto.entity;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Local extends BaseEntity{
	
	@NotBlank(message = "No puede estar en blanco")
	private String nombre;
	
	@NotBlank(message = "No puede estar en blanco")
	private Time apertura;
	
	@NotBlank(message = "No puede estar en blanco")
	private Time cierre;
	
	@NotBlank(message = "No puede estar en blanco")
	private Time tamBloque;
	
	@NotBlank(message = "No puede estar en blanco")
	private int diasDisponibles;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Time getApertura() {
		return apertura;
	}

	public void setApertura(Time apertura) {
		this.apertura = apertura;
	}

	public Time getCierre() {
		return cierre;
	}

	public void setCierre(Time cierre) {
		this.cierre = cierre;
	}

	public Time getTamBloque() {
		return tamBloque;
	}

	public void setTamBloque(Time tamBloque) {
		this.tamBloque = tamBloque;
	}

	public int getDiasDisponibles() {
		return diasDisponibles;
	}

	public void setDiasDisponibles(int diasDisponibles) {
		this.diasDisponibles = diasDisponibles;
	}
}
