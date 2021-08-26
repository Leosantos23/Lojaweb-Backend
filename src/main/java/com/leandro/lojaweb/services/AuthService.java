package com.leandro.lojaweb.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service // Sera uma classe de service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	// Metodo de enviar nova senha ao ususario
	public void sendNewPassword(String email) {
		
		// Primeiro ele vai verificar se o email existe na base de dados
		Cliente cliente = clienteRepository.findByEmail(email);
		// Verifica se o email existe
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}
		
		//Atributos para gerar uma nova senha aleatoria usando o bcrypt
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	// Metodo auxiliar para gerar a nova senha aleatoria
	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	// Metodo auxiliar para gerar um caractere aleatorio
	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}

