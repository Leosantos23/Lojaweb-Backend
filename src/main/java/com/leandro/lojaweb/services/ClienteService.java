package com.leandro.lojaweb.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Cidade;
import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.domain.Endereco;
import com.leandro.lojaweb.domain.enums.TipoCliente;
import com.leandro.lojaweb.dto.ClienteDTO;
import com.leandro.lojaweb.dto.ClienteNewDTO;
import com.leandro.lojaweb.repositories.CidadeRepository;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.repositories.EnderecoRepository;
import com.leandro.lojaweb.services.exceptions.DataIntegrityException;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired//Aqui eu instancio o repositorio (repo) abaixo, que na qual sera automaticamente instanciada pelo SPRING.
	//Pelo mecanismo de injecao de dependencias, ou inversao de controle.
	private ClienteRepository repo;//Aqui declaro uma dependencia de um objeto do tipo cliente.
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	//Aqui vou fazer uma funcao de buscar a Cliente por ID.
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
	
	//Aqui ficara o insert
	//Metodo insert.
	@Transactional
	public Cliente insert(Cliente obj) {
		
		obj.setId(null);//Garantindo que o novo objeto sera nulo.
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;//Esse metodo tera de retornar o repositorio.
	}
	
	//Metodo update
	public Cliente update(Cliente obj) {
		
		Cliente newObj = buscar (obj.getId());//Busca e se der erro, ja lanca uma excessao.
		updateData(newObj, obj);//Foi criado outro metodo auxiliar
		return repo.save(newObj);//Esse metodo tera de retornar o repositorio.
		
	}


	public void delete(Integer id) {
		
		buscar(id);//Busca e se der erro, ja lanca uma excessao.
		try {
		repo.deleteById(id);//Aqui apaga pelo id
		}
		catch (DataIntegrityViolationException e) {
			
			throw new DataIntegrityException("Nao e possivel excluir, porque existe pedidos relacionados a este cliente!");
			
		}
		
	}

	//Metodo listar todas Clientes
	public List<Cliente> buscarTodos() {
		
		return repo.findAll();
	}
	
	//Metodo responsavel por paginar os clientes e mostrar organizadas osando o PAGE
	public Page<Cliente> buscarPagina(Integer pagina, Integer linhas, String ordem, String direcao){
		//Para fazer uma consulta e retornar uma pagina de dados, e preciso fazer outro objeto do tipo PAGEREQUEST
		PageRequest pageRequest = PageRequest.of(pagina, linhas, Direction.valueOf(direcao),ordem);
		return repo.findAll(pageRequest);
	}
	
	//Metodo auxiliar, para instanciar um cliente apartir de um DTO, 
	public Cliente fromDTO (ClienteDTO objDTO) {
	
	 return new Cliente (objDTO.getId(), objDTO.getNome(),objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	
	//Metodo auxiliar para atualizacao de cliente
	private void updateData(Cliente newObj, Cliente obj) {
	
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());	
	}
	
}
