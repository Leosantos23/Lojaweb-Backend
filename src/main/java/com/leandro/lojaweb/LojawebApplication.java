package com.leandro.lojaweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//Implementei a CommandLineRunner para ja ir inserindo dados diretamente ao banco, pois a primeiro insert no commit interior
//tive de fazer manualmente.
public class LojawebApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {

	}

}
