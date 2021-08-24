package com.leandro.lojaweb.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component// Para que esta classe seja injetada em outras classes como componente.
public class JWTUtil {

	// chave token
	@Value("${jwt.secret}")
	private String secret;

	// Expiracao token
	@Value("${jwt.expiration}")
	private Long expiration;

	// Metodo gerador do token
	public String generateToken(String username) {
		// Agora que faz o uso da biblioteca jwt
		return Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

}
