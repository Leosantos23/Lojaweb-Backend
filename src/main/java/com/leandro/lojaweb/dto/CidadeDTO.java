package com.leandro.lojaweb.dto;

import java.io.Serializable;

import com.leandro.lojaweb.domain.Cidade;

public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
    
	// Atributos de cidade dto
	private Integer id;
	private String nome;

	// Metodo construtor sem parametros
	public CidadeDTO() {
	}

	// Metodo construtor com parametros
	public CidadeDTO(Cidade obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

	// Getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
