package com.leandro.lojaweb.domain;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity//aqui faco tambem o mapeamento dizendo que sera uma entidade tambem.
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Esta classe tera como id um objeto do tipo item PK (Primary Key)
	@JsonIgnore//Nao sera serializado.
	@EmbeddedId//Utilizei uma classe aux para representa-la
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	//Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	@SuppressWarnings("unused")
	private ItemPedido() {
		
	}

	//Metodo Construtor com os parametros, - colecao
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido);//atribuo o pedido que veio como argumento
		id.setProduto(produto);//atribuo o produto que veio como argumento
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	//Para eu ter acesso direto ao pedido e produto fora da minnha classe item pedido.
	//Faz mais sentido do que acessar o id e depois o item do pedido.
	
	@JsonIgnore//Nao sera serializado.
	public Pedido getPedido() {
		return id.getPedido();
	}
	//sera serializado.
	public Produto getProduto() {
		return id.getProduto();
	}
	
	//getters e setters
	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	//HashCode e Equals, em java para que dois objetos possam ser comparados pelo seu conteudo e nao pela memoria.
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
