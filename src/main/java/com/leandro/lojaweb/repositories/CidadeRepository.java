package com.leandro.lojaweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandro.lojaweb.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
