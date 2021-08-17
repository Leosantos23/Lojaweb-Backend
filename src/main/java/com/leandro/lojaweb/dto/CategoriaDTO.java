package com.leandro.lojaweb.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.leandro.lojaweb.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Campo obrigatorio!")
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres!")
	private String nome;

	// Metodo Construtor vazio, que instancio um objeto sem jogar nada para os
	// atributos principais
	// Alem disso algumas bibliotecas precisa dele.
	public CategoriaDTO() {

	}

	// Metodo construtor DTO
	public CategoriaDTO(Categoria obj) {

		id = obj.getId();
		nome = obj.getNome();

	}

	// getters e setters
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
