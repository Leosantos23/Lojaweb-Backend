package com.leandro.lojaweb.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.services.ClienteService;

@RestController//Anotacao controladora REST
@RequestMapping(value="/clientes")//Aqui eu coloco o nome do end point REST
public class ClienteResource {
	
	@Autowired//Para instanciar automaticamente
	private ClienteService service;
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, etc)
	public ResponseEntity<?> find (@PathVariable Integer id) {
		
		Cliente obj = service.buscar(id);//Aqui chamo o obj o service ao metodo  buscar, repassando o id.
		return ResponseEntity.ok().body(obj);
	}

}
