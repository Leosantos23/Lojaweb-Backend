package com.leandro.lojaweb.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.leandro.lojaweb.domain.enums.StatusPagamento;

//Aqui faco o mapeamento com o JPA para criar automaticamente as tabelas do banco de dados
@Entity 
//Sera uma tabela para cada, (Pagamento com cartao e boleto)
@Inheritance(strategy = InheritanceType.JOINED) 
// Gerar um campo adicional para escolher o tipo de pagamento
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")

public abstract class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private Integer status;

	// Relacionamento com pedido
	@JsonIgnore // Os pagamentos de um cliente nao vai ser serializados
	@OneToOne // Relacionamento um para um
	@JoinColumn(name = "pedido_id")
	@MapsId
	private Pedido pedido;

	// Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	public Pagamento() {

	}

	// Metodo Construtor com os parametros, - colecao
	public Pagamento(Integer id, StatusPagamento status, Pedido pedido) {
		super();
		this.id = id;
		this.status = (status == null) ? null : status.getCod();// Corrigido
		this.pedido = pedido;
	}

	// Getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatusPagamento getStatus() {
		return StatusPagamento.toEnum(status);
	}

	public void setStatus(StatusPagamento status) {
		this.status = status.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
