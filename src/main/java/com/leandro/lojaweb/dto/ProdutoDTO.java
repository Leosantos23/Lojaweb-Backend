package com.leandro.lojaweb.dto;

import java.io.Serializable;

import com.leandro.lojaweb.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private Double preco;
	
	//Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	//Alem disso algumas bibliotecas precisa dele.
	public ProdutoDTO() {
		
	}

	//Metodo Construtor DTO
	public ProdutoDTO(Produto obj) {
	
		id = obj.getId();
		nome = obj.getNome();
		preco = obj.getPreco();
	}

	//getters e setters
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
	

}
