package com.leandro.lojaweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // Aqui faco o mapeamento com o JPA para criar automaticamente as tabelas do banco de dados
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos basicos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Definindo a geracao automatica dos ids das categorias
	private Integer id;
	private String nome;

	// Associacoes e relacionamentos, um estado tem varias cidades
	@JsonIgnore // Aqui nao deixo serializar as cidades, sera omitido
	@OneToMany(mappedBy = "estado") // O Inverso da associacao em cidade
	private List<Cidade> cidades = new ArrayList<>();

	// Metodo Construtor vazio, que instancio um objeto sem jogar nada para os
	// Atributos principais
	public Estado() {

	}

	// Metodo Construtor com os parametros, - colecao
	public Estado(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	// HashCode e Equals, em java para que dois objetos possam ser comparados pelo seu conteudo e nao pela memoria
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
