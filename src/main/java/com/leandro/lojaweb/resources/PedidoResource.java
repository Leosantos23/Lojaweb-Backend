package com.leandro.lojaweb.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leandro.lojaweb.domain.Pedido;
import com.leandro.lojaweb.services.PedidoService;

@RestController // Anotacao controladora REST
@RequestMapping(value = "/pedidos") // Aqui eu coloco o nome do end point REST
public class PedidoResource {

	@Autowired // Para instanciar automaticamente
	private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // Para que este metodo seja REST tenho que associar a
																	// algum verbo HTTP (GET, POST, etc)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {

		Pedido obj = service.buscar(id);// Aqui chamo o obj o service ao metodo buscar, repassando o id.
		return ResponseEntity.ok().body(obj);
	}

	/*
	 * Este metodo tera de chamar a opcao que SALVAR/ INSERIR um novo pedido no
	 * banco de dados ja com POST, este metodo retornara um pedido no formato JSON ,
	 * e inserir este pedido no banco.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {

		obj = service.insert(obj);
		// Uma boa pratica de engenharia de software, e referenciar tambem a URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	// Busca de pedidos por pagina
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
