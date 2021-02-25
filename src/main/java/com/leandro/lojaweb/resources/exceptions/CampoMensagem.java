package com.leandro.lojaweb.resources.exceptions;

import java.io.Serializable;

public class CampoMensagem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nomeCampo;
	private String mensagem;
	
	//Metodo construtor vazio
	private CampoMensagem () {
		
	}
	//Metodo construtor com os atrubutos
	public CampoMensagem(String nomeCampo, String mensagem) {
		super();
		this.nomeCampo = nomeCampo;
		this.mensagem = mensagem;
	}
	
	//gettes e setters
	public String getNomeCampo() {
		return nomeCampo;
	}
	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
	
	

}
