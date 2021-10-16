package com.leandro.lojaweb.dto;

import java.io.Serializable;

import com.leandro.lojaweb.domain.Estado;

public class EstadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	//Atributos de estado
	private Integer id;
	private String nome;
	
	// Metodo construtor sem parametros
	public EstadoDTO() {
	}
	// Metodo construtor com parametros
	public EstadoDTO(Estado obj) {
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
