package com.leandro.lojaweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leandro.lojaweb.domain.ItemPedido;

@Repository
/*
 * Com isto sera capaz de realizar varios tipos de acessos e transacoes a dados
 * do banco referente ao objeto Categoria, que por sua vez esta mapeado com a
 * tabela no banco de dados
 */
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
