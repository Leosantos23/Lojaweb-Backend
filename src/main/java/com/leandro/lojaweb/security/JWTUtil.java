package com.leandro.lojaweb.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component // Para que esta classe seja injetada em outras classes como componente.
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
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	// Metodo token valido
	public boolean tokenValido(String token) {
		// Testar se o token e valido
		Claims claims = getClaims(token);
		// Verificar se as clains e validas
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());// Pega a data atual para testar se o token esta expirado
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	// Metodo para pegar o usuario apartir do token
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	// Metodo de reinvidicacoes(clains)
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

}
