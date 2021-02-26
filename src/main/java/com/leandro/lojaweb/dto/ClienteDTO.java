package com.leandro.lojaweb.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.leandro.lojaweb.domain.Cliente;

public class ClienteDTO  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message= "Campo obrigatorio!")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres!")
	private String nome;
	
	@NotEmpty(message= "Campo obrigatorio!")
	@Email(message= "Email invalido!")
	private String email;
	
	//Metodo Construtor vazio, que instancio um objeto sem jogar nada para os atributos principais
	//Alem disso algumas bibliotecas precisa dele.
	private ClienteDTO () {
		
	}
	
	//Metodo construtor DTO
	public ClienteDTO (Cliente obj) {
		
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
		
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
