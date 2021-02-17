package com.leandro.lojaweb.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.lojaweb.domain.Categoria;

@RestController//Anotacao controladora REST
@RequestMapping(value="/categorias")//Aqui eu coloco o nome do end point REST
public class CategoriaResource {
	
	@RequestMapping(method=RequestMethod.GET)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, etc)
	public List<Categoria> listar () {
		
		Categoria cat1 = new Categoria(1, "Informatica");
		Categoria cat2 = new Categoria(2, "Escritorio");
		
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}

}
