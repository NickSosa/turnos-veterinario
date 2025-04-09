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
public class Veterinario extends Persona{

	//@NotBlank(message = "No puede estar en blanco")
	private Time inicioTurno;
	
	//@NotBlank(message = "No puede estar en blanco")
	private Time finTurno;
	
	//@NotBlank(message = "No puede estar en blanco")
	private String especializacion;

	public Time getInicioTurno() {
		return inicioTurno;
	}

	public void setInicioTurno(Time inicioTurno) {
		this.inicioTurno = inicioTurno;
	}

	public Time getFinTurno() {
		return finTurno;
	}

	public void setFinTurno(Time finTurno) {
		this.finTurno = finTurno;
	}

	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}
}
