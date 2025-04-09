package com.nico.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nico.proyecto.entity.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long>{
	List<Turno> findByClienteId(Long id);
}
