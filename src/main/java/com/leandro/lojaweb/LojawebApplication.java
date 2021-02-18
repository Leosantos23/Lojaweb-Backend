package com.leandro.lojaweb;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.domain.Cidade;
import com.leandro.lojaweb.domain.Estado;
import com.leandro.lojaweb.domain.Produto;
import com.leandro.lojaweb.repositories.CategoriaRepository;
import com.leandro.lojaweb.repositories.CidadeRepository;
import com.leandro.lojaweb.repositories.EstadoRepository;
import com.leandro.lojaweb.repositories.ProdutoRepository;

@SpringBootApplication
//Implementei a CommandLineRunner para ja ir inserindo dados diretamente ao banco, pois a primeiro insert no commit interior
//tive de fazer manualmente.
public class LojawebApplication  implements CommandLineRunner{

	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private CategoriaRepository categoriaRepository;
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private ProdutoRepository produtoRepository;
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private EstadoRepository estadoRepository;
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private CidadeRepository cidadeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(LojawebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto (null, "Computador", 2000.00);
		Produto p2 = new Produto (null, "Impressora", 800.00);
		Produto p3 = new Produto (null, "Mouse", 80.00);
		
		//aqui faco as associacoes das categorias
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//aqui faco as associacoes dos produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));//Aqui salva todas categorias
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));//Aqui salva todos produtos
		
		//Aqui faco as instancias dos estados
		Estado est1 = new Estado (null, "Minas Gerais"); 
		Estado est2 = new Estado (null, "Sao Paulo"); 
		
		//Aqui faco as instancias das cidades
		Cidade c1 = new Cidade(null,"Uberlandia", est1);
		Cidade c2 = new Cidade(null,"Sao Paulo", est2);
		Cidade c3 = new Cidade(null,"Campinas", est2);
		
		//Associacoes dos estados
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		//Salvar os estados no banco de dados
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		//Salvar as cidades no banco de dados
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
	}
	

}
