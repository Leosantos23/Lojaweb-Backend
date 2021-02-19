package com.leandro.lojaweb.domain.enums;

public enum TipoCliente {
	/*Eu poderia deixar sem os numeros e atribuicoes entre aspas, e deixar o jpa enumerar automaticamente, porem em caso de outro desenvolvedor
	acrescentar ou tipo de pessoa antes do PESSOAFISICA(1) sem o meu tratamento, ocorreria conflito no banco de dados, NESSE CASO, se houver a 
	manutencao do sistema, de qualquer jeito o novo tipo de pessoa ficaria na posicao 0.
	*/
	PESSOAFISICA(1, "Pessoa Fisica"),
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	//Atributos do tratamento
	private int cod;
	private String descricao;
	
	//Contrutor de tratamento
	private TipoCliente (int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
		
		//Lembrando que so para acessar dados coloco apenas o metodo GET
		public int getCod() {
			return cod;
		}
		
		public String getDescricao() {
			return descricao;
		}
		
		/*Para finalizar a enumeracao,  aqui tem uma implementacao que recebe um codigo que me retorna o objeto tipo cliente ja instanciado
		conforme o codigo que eu passar.
		*/
		
		//Metodo static que pode rodar mesmo sem ter objeto instanciado.
		public static TipoCliente toEnum (Integer cod) {
			
			if (cod == null) {
				return null;
			}
			
			for (TipoCliente x : TipoCliente.values()) {
				
				if (cod.equals(x.getCod())) {
					return x;
				}
				
			}
			
			//Se nao for nenhum
			throw new IllegalArgumentException ("Id invalido: " + cod);
		}
		
	}
	
	
	
	


