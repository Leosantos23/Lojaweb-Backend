package com.leandro.lojaweb.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController//Anotacao controladora REST
@RequestMapping(value="/categorias")//Aqui eu coloco o nome do end point REST
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, etc)
	public String listar () {
		return "O REST esta funcionando!";
	}

}
