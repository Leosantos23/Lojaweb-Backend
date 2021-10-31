package com.leandro.lojaweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.lojaweb.domain.Cliente;

@Repository
/*
 * Com isto sera capaz de realizar varios tipos de acessos e transacoes a dados
 * do banco referente ao objeto Categoria, que por sua vez esta mapeado com a
 * tabela no banco de dados
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	// Fazer uma busca por email
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);

}
