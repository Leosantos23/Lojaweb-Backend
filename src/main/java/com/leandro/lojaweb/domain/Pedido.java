package com.leandro.lojaweb.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity // Aqui faco o mapeamento com o JPA para criar automaticamente as tabelas do banco de dados
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Definindo a geracao automatica dos ids
	private Integer id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm") // Para formatar a data bonitinho no JSON
	private Date instante;

	// Relacionamento com pagamento
	// Relacionamento um para um, usei CASCADE, porque senao da um erro de entidade
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") 
	// transiente quando vai salvar um pedido e o pagamento dele, e uma peculiaridade do JPA
	private Pagamento pagamento;

	// Relacionamento com cliente
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	// Relacionamento com endereco
	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;

	// Aqui a classe pedido tem que conhecer os itens associado a ela.
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();// O Set me garante que nao tera o mesmo item repetido no mesmo
													// pedido.

	// Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	public Pedido() {

	}

	// Metodo Construtor com os parametros, - colecao
	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	// Metodo para calcular o total do pedido
	public double getTotalPedido() {
		double soma = 0.0;
		for (ItemPedido ip : itens) {
			soma = soma + ip.getSubTotalPedido();
		}
		return soma;
	}

	// Getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// Metodo to String, de implementacao basica de pedido
	@Override
	public String toString() {

		// Para formatar o valor bonitinho
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		// Para formatar a data do pedido bonitinha
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(sd.format(getInstante()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Status do pagamento: ");
		builder.append(getPagamento().getStatus().getDescricao());
		builder.append("\nDetalhes do pedido:\n");
		// Concatenar
		for (ItemPedido ip : getItens()) {

			builder.append(ip.toString());
		}

		builder.append("Valor total: ");
		builder.append(nf.format(getTotalPedido()));

		return builder.toString();
	}

}
