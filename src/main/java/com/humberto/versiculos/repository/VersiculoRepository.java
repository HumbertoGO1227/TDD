package com.humberto.versiculos.repository;

import com.humberto.versiculos.model.Versiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VersiculoRepository extends JpaRepository<Versiculo, Long> {
    List<Versiculo> findByData(LocalDate data);
}
