package com.leandro.lojaweb.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Para o banco h2
	@Autowired
	private Environment env;

	// Definir um vetor de strings
	private static final String[] PUBLIC_MATCHERS = {
			// Definir quais caminhos que por padroes estarao liberados
			"/h2-console/**"};
	
	//Outro vetor com os caminhos apenasde leitura para aumentar a seguranca
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/status/**"
	};


	// Agora vou sobescrever o metodo de WebSecurityConfigurerAdapter
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Para tratar o em caso do h2ser usado
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		//Para que o bean abaixo seja ativado
		http.cors()
		//Para desativarataques de CSRF
		.and().csrf().disable();
		// Vou comecar acessando o objeto http
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()//Defino que é so para o metodo GET.
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		.anyRequest()
		.authenticated();
		//Para assegurar que o back end nao vai criar sessao de usuario
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	// Configuracao basica de cors, que da acesso basico de multiplas fontes para todos caminhos
	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	//Adicionar seguranca a senha
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
	return new BCryptPasswordEncoder();	
	}
	

}
