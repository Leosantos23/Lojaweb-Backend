package com.leandro.lojaweb.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity // Aqui faco o mapeamento com o JPA para criar automaticamente as tabelas do banco de dados
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	// Atributos basicos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Definindo a geracao automatica dos ids das categorias
	private Integer id;
	private String nome;

	// Associacoes e relacionamentos, muitas cidades tem um estado
	@ManyToOne // Relacionamento muitos para um
	@JoinColumn(name = "estado_id") // Mapeamento da associacao no lado cidade. ja com o nome da chave estrangeira de estado
	private Estado estado;

	// Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	public Cidade() {

	}

	// Metodo Construtor com os parametros, - colecao
	public Cidade(Integer id, String nome, Estado estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
