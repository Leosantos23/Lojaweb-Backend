package com.leandro.lojaweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leandro.lojaweb.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>  {

}
