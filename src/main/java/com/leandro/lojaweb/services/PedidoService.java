package com.leandro.lojaweb.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Pedido;
import com.leandro.lojaweb.repositories.PedidoRepository;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired//Aqui eu instancio o repositorio (repo) abaixo, que na qual sera automaticamente instanciada pelo SPRING.
	//Pelo mecanismo de injecao de dependencias, ou inversao de controle.
	private PedidoRepository repo;//Aqui declaro uma dependencia de um objeto do tipo categoria.
	
	//Aqui vou fazer uma funcao de buscar a Categoria por ID.
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}

}
