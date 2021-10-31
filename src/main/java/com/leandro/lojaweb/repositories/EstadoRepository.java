package com.leandro.lojaweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.lojaweb.domain.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	// Repositorio para retornar todos estados ordenados por nome
	@Transactional(readOnly = true)

	public List<Estado> findAllByOrderByNome();

}
