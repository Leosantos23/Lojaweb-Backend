package com.leandro.lojaweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leandro.lojaweb.services.S3Service;

@SpringBootApplication
//Implementei a CommandLineRunner para ja ir inserindo dados diretamente ao banco, pois a primeiro insert no commit interior
//tive de fazer manualmente.
public class LojawebApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(LojawebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		s3Service.uploadFile("D:\\Shelby2010.jpg");

	}

}
