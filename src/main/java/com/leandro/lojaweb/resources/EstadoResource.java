package com.leandro.lojaweb.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.lojaweb.domain.Cidade;
import com.leandro.lojaweb.domain.Estado;
import com.leandro.lojaweb.dto.CidadeDTO;
import com.leandro.lojaweb.dto.EstadoDTO;
import com.leandro.lojaweb.services.CidadeService;
import com.leandro.lojaweb.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

	// Injecao de dependencia em estado
	@Autowired
	private EstadoService service;
	
	// Injecao de dependencia em cidade
	@Autowired
	private CidadeService cidadeService;

	// Endpoint de estados
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = service.findAll();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	// Endpoint de cidades
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}

}
