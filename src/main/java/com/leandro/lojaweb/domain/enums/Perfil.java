package com.leandro.lojaweb.domain.enums;

public enum Perfil {
	
	ADMIN (1, "ROLE_ADMIN"),// Conforme o framework exige.
	CLIENTE (2, "ROLE_CLIENTE");// Conforme o framework exige.
	
	//Atributos do tratamento
		private int cod;
		private String descricao;
		
		//Contrutor de tratamento
		private Perfil (int cod, String descricao) {
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
			public static Perfil toEnum (Integer cod) {
				
				if (cod == null) {
					return null;
				}
				
				for (Perfil x : Perfil.values()) {
					
					if (cod.equals(x.getCod())) {
						return x;
					}
					
				}
				
				//Se nao for nenhum
				throw new IllegalArgumentException ("Id invalido: " + cod);
			}

}
