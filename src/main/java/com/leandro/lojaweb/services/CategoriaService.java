package com.leandro.lojaweb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.dto.CategoriaDTO;
import com.leandro.lojaweb.repositories.CategoriaRepository;
import com.leandro.lojaweb.services.exceptions.DataIntegrityException;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	/*
	 * Aqui eu instancio o repositorio (repo) abaixo, que na qual sera
	 * automaticamente instanciada pelo SPRING, Pelo mecanismo de injecao de
	 * dependencias, ou inversao de controle.
	 */
	@Autowired
	private CategoriaRepository repo;// Aqui declaro uma dependencia de um objeto do tipo categoria.

	// Aqui vou fazer uma funcao de buscar a Categoria por ID
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	// Metodo insert
	public Categoria insert(Categoria obj) {

		obj.setId(null);// Garantindo que o novo objeto sera nulo
		return repo.save(obj);// Esse metodo tera de retornar o repositorio
	}

	// Metodo update
	public Categoria update(Categoria obj) {

		Categoria newObj = find(obj.getId());// Busca e se der erro, ja lanca uma excessao
		updateData(newObj, obj);// Foi criado outro metodo auxiliar
		return repo.save(newObj);// Esse metodo tera de retornar o repositorio

	}

	public void delete(Integer id) {

		find(id);// Busca e se der erro, ja lanca uma excessao
		try {
			repo.deleteById(id);// Aqui apaga pelo id
		} catch (DataIntegrityViolationException e) {

			throw new DataIntegrityException("Nao e possivel excluir uma categoria que possui produtos disponiveis!");

		}

	}

	// Metodo listar todas Categorias
	public List<Categoria> findAll() {

		return repo.findAll();
	}

	// Metodo responsavel por paginar as categorias e mostrar organizadas osando o PAGE
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		// Para fazer uma consulta e retornar uma pagina de dados, e preciso fazer outro objeto do tipo PAGEREQUEST
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	// Metodo auxiliar, para instanciar uma categoria apartir de um DTO
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}

	// Metodo auxiliar para atualizacao de categorias
	private void updateData(Categoria newObj, Categoria obj) {

		newObj.setNome(obj.getNome());

	}

}
