package com.leandro.lojaweb.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidaErro extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<CampoMensagem> erros = new ArrayList<>();
	
	//Metodo construtor
	public ValidaErro(Integer status, String mensagem, Long timeStamp) {
		super(status, mensagem, timeStamp);
		
	}

	//getters e setters
	public List<CampoMensagem> getErros() {
		return erros;
	}

	//Troquei este metodo de erro no lugar do set.
	public void addErro (String campoNome, String mensagem) {
		erros.add(new CampoMensagem (campoNome, mensagem));
	}
	
	
	
	
	

}
