package com.leandro.lojaweb.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.lojaweb.domain.Produto;
import com.leandro.lojaweb.dto.ProdutoDTO;
import com.leandro.lojaweb.resources.utils.URL;
import com.leandro.lojaweb.services.ProdutoService;

@RestController//Anotacao controladora REST
@RequestMapping(value="/produtos")//Aqui eu coloco o nome do end point REST
public class ProdutoResource {
	
	@Autowired//Para instanciar automaticamente
	private ProdutoService service;
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, etc)
	public ResponseEntity<Produto> find (@PathVariable Integer id) {
		
		Produto obj = service.find(id);//Aqui chamo o obj o service ao metodo  buscar, repassando o id.
		return ResponseEntity.ok().body(obj);
	}
	
	//Search
	//Listar todas os produtos, em paginas.
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage (
			
			@RequestParam(value="nome", defaultValue= "") String nome,
			@RequestParam(value="categorias", defaultValue= "") String categorias,
			@RequestParam(value="pagina", defaultValue= "0") Integer pagina,
			@RequestParam(value="linhas", defaultValue= "24") Integer linhas, 
			@RequestParam(value="ordem", defaultValue= "nome") String ordem, 
			@RequestParam(value="direcao", defaultValue= "ASC") String direcao) {
		
		String nomeDecoded = URL.decodeParam(nome);
		List <Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, pagina, linhas, ordem, direcao);//Aqui busco a lista do banco e  terei de converter para uma pagina DTO.
		//Com este codigo abaixo, eu consigo converter uma pagina, para outra pagina.
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		//Abaixo passo o argumento listDTO para meu response.
		return ResponseEntity.ok().body(listDTO);
	}

}
