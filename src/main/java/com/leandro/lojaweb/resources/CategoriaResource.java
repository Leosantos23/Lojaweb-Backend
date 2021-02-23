package com.leandro.lojaweb.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.services.CategoriaService;


@RestController//Anotacao controladora REST
@RequestMapping(value="/categorias")//Aqui eu coloco o nome do end point REST
public class CategoriaResource {
	
	@Autowired//Para instanciar automaticamente
	private CategoriaService service;
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, PUT etc).
	public ResponseEntity<Categoria> find (@PathVariable Integer id) {
		
		Categoria obj = service.buscar(id);//Aqui chamo o obj o service ao metodo  buscar, repassando o id.
		return ResponseEntity.ok().body(obj);
	}
	
	/*Este metodo tera de chamar a opcao que SALVAR/ INSERIR uma nova categoria no banco de dados ja com POST, este metodo recebera uma categoria no 
	 * formato JSON , e inseris esta categoria no banco.
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert (@RequestBody Categoria obj) {
		
		obj = service.insert(obj);
		//Uma boa pratica de engenharia de software, e referenciar tambem a URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	//Este metodo tem a funcionalidade de ATUALIZAR uma categoria no banco de dados com o PUT.
	@RequestMapping(value= "/{id}", method=RequestMethod.PUT)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, PUT etc).
	public ResponseEntity<Void> update (@RequestBody Categoria obj, @PathVariable Integer id ){
		
		obj.setId(id);//Para garantir que a categoria que sera atualizada e a que eu passar o id.
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	//Este metodo tem a funcionalidade de APAGAR uma categoria no banco de dados com o DELETE.
	@RequestMapping(value= "/{id}", method=RequestMethod.DELETE)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, PUT etc).
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		
	service.delete(id);
	return ResponseEntity.noContent().build();
	
	}
	
	
	

}
