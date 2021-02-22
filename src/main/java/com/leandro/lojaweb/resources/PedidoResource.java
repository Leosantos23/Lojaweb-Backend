package com.leandro.lojaweb.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.lojaweb.domain.Pedido;
import com.leandro.lojaweb.services.PedidoService;

@RestController//Anotacao controladora REST
@RequestMapping(value="/pedidos")//Aqui eu coloco o nome do end point REST
public class PedidoResource {
	
	@Autowired//Para instanciar automaticamente
	private PedidoService service;
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, etc)
	public ResponseEntity<?> find (@PathVariable Integer id) {
		
		Pedido obj = service.buscar(id);//Aqui chamo o obj o service ao metodo  buscar, repassando o id.
		return ResponseEntity.ok().body(obj);
	}

}
