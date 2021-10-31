package com.leandro.lojaweb.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // Aqui faco tambem o mapeamento dizendo que sera uma entidade tambem
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	// Esta classe tera como id um objeto do tipo item PK (Primary Key)
	@JsonIgnore // Nao sera serializado.
	@EmbeddedId // Utilizei uma classe aux para representa-la
	private ItemPedidoPK id = new ItemPedidoPK();

	private Double desconto;
	private Integer quantidade;
	private Double preco;

	// Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	@SuppressWarnings("unused")
	private ItemPedido() {

	}

	// Metodo Construtor com os parametros, - colecao
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido);// Atribuo o pedido que veio como argumento
		id.setProduto(produto);// Atribuo o produto que veio como argumento
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	/* Para eu ter acesso direto ao pedido e produto fora da minnha classe item pedido, 
	 * Faz mais sentido do que acessar o id e depois o item do pedido */

	// Metodo para calcular o subtotal dos pedidos
	public double getSubTotalPedido() {
		return (preco - desconto) * quantidade;
	}

	@JsonIgnore // Nao sera serializado
	public Pedido getPedido() {
		return id.getPedido();
	}

	// Sera serializado
	public Produto getProduto() {
		return id.getProduto();
	}

	// Void SET
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}

	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	// Getters e setters
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// Metodo to String, de implementacao basica de item pedido
	@Override
	public String toString() {

		// Para formatar o valor bonitinho
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", Quantidade: ");
		builder.append(getQuantidade());
		builder.append(", Valor: ");
		builder.append(nf.format(getPreco()));
		builder.append(", Subtotal: ");
		builder.append(nf.format(getSubTotalPedido()));
		builder.append("\n");
		return builder.toString();
	}

}
