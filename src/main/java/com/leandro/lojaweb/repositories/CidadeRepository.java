package com.leandro.lojaweb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.lojaweb.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly=true)
	// Query para implementacao da consulta no banco de dados.
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);

}
