package com.leandro.lojaweb.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.leandro.lojaweb.security.JWTAuthenticationFilter;
import com.leandro.lojaweb.security.JWTAuthorizationFilter;
import com.leandro.lojaweb.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// Para o banco h2
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;

	// Definir um vetor de strings
	private static final String[] PUBLIC_MATCHERS = {
			// Definir quais caminhos que por padroes estarao liberados
			"/h2-console/**"
			};
	
	// Outro vetor com os caminhos apenas de leitura para aumentar a seguranca
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/estados/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes/**",
			"/auth/forgot/**"
	};

	
	// Agora vou sobescrever o metodo de WebSecurityConfigurerAdapter
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Para tratar o em caso do h2 ser usado
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		// Para que o bean abaixo seja ativado
		http.cors()
		// Para desativar ataques de CSRF
		.and().csrf().disable();
		// Vou comecar acessando o objeto http
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST,PUBLIC_MATCHERS_POST).permitAll()//Defino que é so para o metodo POST.
		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()//Defino que é so para o metodo GET.
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		.anyRequest()
		.authenticated();
		
		// Para passar pelo filtro de autenticacao
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		
		// Para passar pelo filtro de autorizacao
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService ));
		
		// Para assegurar que o back end nao vai criar sessao de usuario
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	// Configuracao basica de cors, que da acesso basico de multiplas fontes para todos caminhos
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		// Vou armazenar em objeto
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		
		// Passo a lista de metodos que vou permitir o cors
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	// Adicionar seguranca a senha
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
	return new BCryptPasswordEncoder();	
	}
	

}
