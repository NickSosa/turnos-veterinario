package com.nico.proyecto.entity;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Turno extends BaseEntity{

	//@NotBlank(message = "No puede estar en blanco")
	private Date fecha;
	
	//@NotBlank(message = "No puede estar en blanco")
	private Time inicio;
	
	//@NotBlank(message = "No puede estar en blanco")
	private Time fin;
	
	private String status;
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fkveterinario")
	private Veterinario veterinario;
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fkcliente")
	private Cliente cliente;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkmascota")
	private Mascota mascota;

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Time getInicio() {
		return inicio;
	}
	public void setInicio(Time inicio) {
		this.inicio = inicio;
	}
	public Time getFin() {
		return fin;
	}
	public void setFin(Time fin) {
		this.fin = fin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Veterinario getVeterinario() {
		return veterinario;
	}
	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Mascota getMascota() {
		return mascota;
	}
	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}

}
