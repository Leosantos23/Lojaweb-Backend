package com.leandro.lojaweb.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.leandro.lojaweb.services.DBService;
import com.leandro.lojaweb.services.EmailService;
import com.leandro.lojaweb.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	//Para controlar a chave ddl, e nao sobrepor dados no banco
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		
		//Para nao sobrepor o database, e manter um controle na base de dados.
		if (!"create".equals(strategy)) {
			return false;
		}
		
		dbService.instanciateTestDatabase();
		return true;
	}
	
	@Bean//Instanciar la no smtp email service
	public EmailService emailService() {
		return new SmtpEmailService();
	}


}
