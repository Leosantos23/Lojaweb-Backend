package com.leandro.lojaweb.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.dto.CategoriaDTO;
import com.leandro.lojaweb.services.CategoriaService;

@RestController // Anotacao controladora REST
@RequestMapping(value = "/categorias") // Aqui eu coloco o nome do end point REST
public class CategoriaResource {

	@Autowired // Para instanciar automaticamente
	private CategoriaService service;

	// Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET,
	// POST, PUT etc)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);// Aqui chamo o obj o service ao metodo buscar, repassando o id
		return ResponseEntity.ok().body(obj);
	}

	/*
	 * Este metodo tera de chamar a opcao que SALVAR/ INSERIR uma nova categoria no
	 * banco de dados ja com POST, este metodo recebera uma categoria no formato
	 * JSON , e inseris esta categoria no banco.
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')") // Autorizo somente o admin
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) {
		// Aqui antes terei de converter um objeto DTO para um objeto ENTITY
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		// Uma boa pratica de engenharia de software, e referenciar tambem a URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	/*
	 * Este metodo tem a funcionalidade de ATUALIZAR uma categoria no banco de dados
	 * com o PUT Para que este metodo seja REST tenho que associar a algum verbo
	 * HTTP (GET, POST, PUT etc)
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('ADMIN')") // Autorizo somente o admin
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
		// Aqui antes terei de converter um objeto DTO para um objeto ENTITY
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);// Para garantir que a categoria que sera atualizada e a que eu passar o id
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	/*
	 * Este metodo tem a funcionalidade de APAGAR uma categoria no banco de dados
	 * com o DELETE Para que este metodo seja REST tenho que associar a algum verbo
	 * HTTP (GET, POST, PUT etc)
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('ADMIN')") // Autorizo somente o admin
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	// Listar todas as categorias, sem mostrar os produtos com o DTO
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();// Aqui busco a lista do banco e terei de converter para uma lista DTO
		// Com este codigo abaixo, eu consigo converter uma lista, para outra lista
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		// Abaixo passo o argumento listDTO para meu response
		return ResponseEntity.ok().body(listDTO);
	}

	// Listar todas as categorias, em paginas
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		// Aqui busco a lista do banco e terei de converter para uma pagina DTO
		Page<Categoria> page = service.findPage(linesPerPage, linesPerPage, orderBy, direction);
		// Com este codigo abaixo, eu consigo converter uma pagina, para outra pagina
		Page<CategoriaDTO> pageDTO = page.map(obj -> new CategoriaDTO(obj));
		// Abaixo passo o argumento listDTO para meu response
		return ResponseEntity.ok().body(pageDTO);
	}

}
