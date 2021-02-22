package com.leandro.lojaweb.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.leandro.lojaweb.domain.enums.StatusPagamento;

@Entity//Aqui faco o mapeamento com o JPA para criar automaticamente as tabelas do banco de dados
public class PagamentoComBoleto extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Date dataVencimento;
	private Date dataPagamento;
	
	//Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	private PagamentoComBoleto() {
		
	}
	
	//Metodo Construtor com os parametros, - colecao, baseado na SuperClasse Pagamento
	public PagamentoComBoleto(Integer id, StatusPagamento status, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, status, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	//getters e setters
	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
}