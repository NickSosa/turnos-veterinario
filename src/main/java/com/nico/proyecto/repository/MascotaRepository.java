package com.nico.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nico.proyecto.entity.Mascota;

public interface MascotaRepository extends JpaRepository<Mascota, Long>{

}
