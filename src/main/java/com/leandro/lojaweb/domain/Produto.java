package com.leandro.lojaweb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity//aqui faco tambem o mapeamento dizendo que sera uma entidade tambem.
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	
	@JsonIgnore//Mostra que do outro lado da associacao ja foi buscada, portanto nao busca mais.
	//Aqui faco associacoes de um ou mais produto para muitas categorias
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA", 
	joinColumns = @JoinColumn(name="produto_id"),
	inverseJoinColumns = @JoinColumn(name= "categoria_id"))
	private List <Categoria> categorias = new ArrayList<>();
	
	//Aqui a classe pedido tem que conhecer os itens associado a ela.
	@JsonIgnore//Nao sera serializado
	@OneToMany(mappedBy= "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();//O Set me garante que nao tera o mesmo item repetido no mesmo pedido.
	
	//Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	public Produto() {
		
	}

	//Metodo Construtor com os parametros, - colecao
	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	//Um produto conhece os pedidos dele, 
	@JsonIgnore//Foi nescessario ignorar a serializacao, senao sera serializado os pedidos associados ao produto,
	//resultando em referencia ciclica. Pois tudo com get e serializado.
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		
		//Vou percorrer minha lista de itens
		for(ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
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

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
