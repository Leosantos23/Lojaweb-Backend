package com.leandro.lojaweb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.domain.Produto;
import com.leandro.lojaweb.repositories.CategoriaRepository;
import com.leandro.lojaweb.repositories.ProdutoRepository;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	/*
	 * Aqui eu instancio o repositorio (repo) abaixo, que na qual sera
	 * automaticamente instanciada pelo SPRING, Pelo mecanismo de injecao de
	 * dependencias, ou inversao de controle
	 */
	@Autowired
	private ProdutoRepository repo;

	/*
	 * Aqui eu instancio o repositorio (repo) abaixo, que na qual sera
	 * automaticamente instanciada pelo SPRING, Pelo mecanismo de injecao de
	 * dependencias, ou inversao de controle
	 */
	@Autowired
	private CategoriaRepository categoriaRepository;

	// Aqui vou fazer uma funcao de buscar a Categoria por ID
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	// Funcao de search (procurar)
	public Page<Produto> search(String nome, List<Integer> ids, Integer pagina, Integer linhas, String ordem,
			String direcao) {
		// Para fazer uma consulta e retornar uma pagina de dados, e preciso fazer outro objeto do tipo PAGEREQUEST
		PageRequest pageRequest = PageRequest.of(pagina, linhas, Direction.valueOf(direcao), ordem);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

	}

}
