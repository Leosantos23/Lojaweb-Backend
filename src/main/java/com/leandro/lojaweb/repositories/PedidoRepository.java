package com.leandro.lojaweb.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.domain.Pedido;

@Repository
/*
 * Com isto sera capaz de realizar varios tipos de acessos e transacoes a dados
 * do banco referente ao objeto Categoria, que por sua vez esta mapeado com a
 * tabela no banco de dados
 */
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	// Consulta para retornar um page de pedido utilizando os padroes de nomes do framework
	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
	
}
