package com.leandro.lojaweb.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.repositories.CategoriaRepository;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired//Aqui eu instancio o repositorio (repo) abaixo, que na qual sera automaticamente instanciada pelo SPRING.
	//Pelo mecanismo de injecao de dependencias, ou inversao de controle.
	private CategoriaRepository repo;//Aqui declaro uma dependencia de um objeto do tipo categoria.
	
	//Aqui vou fazer uma funcao de buscar a Categoria por ID.
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}

}
