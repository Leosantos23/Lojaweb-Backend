package com.leandro.lojaweb;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.repositories.CategoriaRepository;

@SpringBootApplication
//Implementei a CommandLineRunner para ja ir inserindo dados diretamente ao banco, pois a primeiro insert no commit interior
//tive de fazer manualmente.
public class LojawebApplication  implements CommandLineRunner{

	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(LojawebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
	}

}
