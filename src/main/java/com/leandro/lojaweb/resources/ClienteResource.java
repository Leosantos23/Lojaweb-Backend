package com.leandro.lojaweb.resources;

import java.util.List;
import java.util.stream.Collectors;

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

import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.dto.ClienteDTO;
import com.leandro.lojaweb.services.ClienteService;

@RestController//Anotacao controladora REST
@RequestMapping(value="/clientes")//Aqui eu coloco o nome do end point REST
public class ClienteResource {
	
	@Autowired//Para instanciar automaticamente
	private ClienteService service;
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, etc)
	public ResponseEntity<Cliente> find (@PathVariable Integer id) {
		
		Cliente obj = service.buscar(id);//Aqui chamo o obj o service ao metodo  buscar, repassando o id.
		return ResponseEntity.ok().body(obj);
	}
	
	//Este metodo tem a funcionalidade de ATUALIZAR uma categoria no banco de dados com o PUT.
		@RequestMapping(value= "/{id}", method=RequestMethod.PUT)//Para que este metodo seja REST tenho que associar a algum verbo HTTP (GET, POST, PUT etc).
		public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id ){
			//Aqui antes terei de converter um objeto DTO para um objeto ENTITY
			Cliente obj = service.fromDTO(objDTO);
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
		
		//Listar todas as categorias, sem mostrar os produtos com o DTO.
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<List<ClienteDTO>> buscarTodas () {
			List<Cliente> list = service.buscarTodos();//Aqui busco a lista do banco e  terei de converter para uma lista DTO.
			//Com este codigo abaixo, eu consigo converter uma lista, para outra lista.
			List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
			//Abaixo passo o argumento listDTO para meu response.
			return ResponseEntity.ok().body(listDTO);
		}
		
		//Listar todas as categorias, em paginas.
		@RequestMapping(value="/pagina", method=RequestMethod.GET)
		public ResponseEntity<Page<ClienteDTO>> buscarPagina (
				@RequestParam(value="pagina", defaultValue= "0") Integer pagina,
				@RequestParam(value="linhas", defaultValue= "24") Integer linhas, 
				@RequestParam(value="ordem", defaultValue= "nome") String ordem, 
				@RequestParam(value="direcao", defaultValue= "ASC") String direcao) {
			Page<Cliente> page = service.buscarPagina(pagina, linhas, ordem, direcao);//Aqui busco a lista do banco e  terei de converter para uma pagina DTO.
			//Com este codigo abaixo, eu consigo converter uma pagina, para outra pagina.
			Page<ClienteDTO> pageDTO = page.map(obj -> new ClienteDTO(obj));
			//Abaixo passo o argumento listDTO para meu response.
			return ResponseEntity.ok().body(pageDTO);
		}

}
