package com.leandro.lojaweb.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.leandro.lojaweb.domain.enums.Perfil;

public class UserSpringSecurity implements UserDetails {
	private static final long serialVersionUID = 1L;

	// atributos
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authorities;

	// Construtor vazio
	public UserSpringSecurity() {

	}

	// Construtor com argumentos
	public UserSpringSecurity(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}

	public Integer getId() {

		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {

		return senha;
	}

	@Override
	public String getUsername() {

		return email;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;// Falo que minha conta nao vai expirar.
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;// Falo que minha conta tambem nao esta bloqueada.
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;// Falo que minha credenciais tambem nao esta expirada.
	}

	@Override
	public boolean isEnabled() {

		return true;// Falo que minha conta tambem esta ativa.
	}

}
