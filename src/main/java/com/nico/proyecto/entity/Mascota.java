package com.nico.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mascota extends BaseEntity{

	@NotBlank(message = "No puede estar en blanco")
	private String nombre;
	
	@NotBlank(message = "No puede estar en blanco")
	private String animal;
	
	//@NotBlank(message = "No puede estar en blanco")
	private String raza;
	
	//@NotBlank(message = "No puede estar en blanco")
	private int edad;
	
	//@NotBlank(message = "No puede estar en blanco")
	private double peso;
	
	//@NotBlank(message = "No puede estar en blanco")
	private boolean sexo;
	
	//@NotBlank(message = "No puede estar en blanco")
	private boolean esterilizado;
	
	//@NotBlank(message = "No puede estar en blanco")
	private boolean vacunaRabia;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public boolean isSexo() {
		return sexo;
	}

	public void setSexo(boolean sexo) {
		this.sexo = sexo;
	}

	public boolean isEsterilizado() {
		return esterilizado;
	}

	public void setEsterilizado(boolean esterilizado) {
		this.esterilizado = esterilizado;
	}

	public boolean isVacunaRabia() {
		return vacunaRabia;
	}

	public void setVacunaRabia(boolean vacunaRabia) {
		this.vacunaRabia = vacunaRabia;
	}
}
