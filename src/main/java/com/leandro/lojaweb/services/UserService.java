package com.leandro.lojaweb.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.leandro.lojaweb.security.UserSpringSecurity;

public class UserService {

	// Metodo estatico, porque vai ser chamado independente de instanciar a classe UserService
	public static UserSpringSecurity authenticated() {
		try {

			// Retorna pra gente o usuario que estiver logado no sistema
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (

		Exception e) {
			return null;
		}
	}

}
