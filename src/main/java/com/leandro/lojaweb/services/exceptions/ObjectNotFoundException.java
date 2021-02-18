package com.leandro.lojaweb.services.exceptions;

//Classe reponsavel para tratamento de erros ou excecoes
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	//Metodo responsavel em mostrar a mensagem de erro ao usuario
	public ObjectNotFoundException (String mensagem) {
		super (mensagem);
	}
	
	//Outro metodo responsavel em mostrar uma outra causa do problema ou erro ao usuario
	public ObjectNotFoundException (String mensagem, Throwable causa) {
		super (mensagem, causa);
	}

}
