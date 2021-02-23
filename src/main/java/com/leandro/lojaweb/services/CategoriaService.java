package com.leandro.lojaweb.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.repositories.CategoriaRepository;
import com.leandro.lojaweb.services.exceptions.DataIntegrityException;
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

	//Metodo insert.
	public Categoria insert(Categoria obj) {
		
		obj.setId(null);//Garantindo que o novo objeto sera nulo.
		return repo.save(obj);//Esse metodo tera de retornar o repositorio.
	}

	//Metodo update
	public Categoria update(Categoria obj) {
		
		buscar(obj.getId());//Busca e se der erro, ja lanca uma excessao.
		return repo.save(obj);//Esse metodo tera de retornar o repositorio.
		
	}

	public void delete(Integer id) {
		
		buscar(id);//Busca e se der erro, ja lanca uma excessao.
		try {
		repo.deleteById(id);//Aqui apaga pelo id
		}
		catch (DataIntegrityViolationException e) {
			
			throw new DataIntegrityException("Nao e possivel excluir uma categoria que possui produtos disponiveis!");
			
		}
		
	}

}
