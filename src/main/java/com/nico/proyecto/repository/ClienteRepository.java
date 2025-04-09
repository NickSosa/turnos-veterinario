package com.nico.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nico.proyecto.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Cliente findByDniLike(String dni);
}
