package com.leandro.lojaweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.security.UserSpringSecurity;

@Service
public class UserDetaisServiceImplement implements UserDetailsService {

	@Autowired
	private ClienteRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Implementar essa busca por email
		Cliente cliente = repo.findByEmail(email);

		// testar
		if (cliente == null) {

			throw new UsernameNotFoundException(email);

		}
		// Se der certo tem que retornar a classe, contendo os dados
		return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
