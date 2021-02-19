package com.leandro.lojaweb.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired//Aqui eu instancio o repositorio (repo) abaixo, que na qual sera automaticamente instanciada pelo SPRING.
	//Pelo mecanismo de injecao de dependencias, ou inversao de controle.
	private ClienteRepository repo;//Aqui declaro uma dependencia de um objeto do tipo categoria.
	
	//Aqui vou fazer uma funcao de buscar a Cliente por ID.
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}

}
