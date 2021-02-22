package com.leandro.lojaweb.domain;

import javax.persistence.Entity;

import com.leandro.lojaweb.domain.enums.StatusPagamento;

@Entity//Aqui faco o mapeamento com o JPA para criar automaticamente as tabelas do banco de dados
public class PagamentoComCartao extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;
	
	//Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	private PagamentoComCartao() {
		
	}

	//Metodo Construtor com os parametros, - colecao, baseado na SuperClasse Pagamento
	public PagamentoComCartao(Integer id, StatusPagamento status, Pedido pedido, Integer numeroDeParcelas) {
		super(id, status, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	//getters e setters
	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
}
