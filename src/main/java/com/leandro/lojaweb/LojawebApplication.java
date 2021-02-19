package com.leandro.lojaweb;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leandro.lojaweb.domain.Categoria;
import com.leandro.lojaweb.domain.Cidade;
import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.domain.Endereco;
import com.leandro.lojaweb.domain.Estado;
import com.leandro.lojaweb.domain.Produto;
import com.leandro.lojaweb.domain.enums.TipoCliente;
import com.leandro.lojaweb.repositories.CategoriaRepository;
import com.leandro.lojaweb.repositories.CidadeRepository;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.repositories.EnderecoRepository;
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
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private ClienteRepository clienteRepository;
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private EnderecoRepository enderecoRepository;

	
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
		
		//Instancias de Clientes
		Cliente cli1 = new Cliente(null, "Leandro Moreira", "leandro@gmail.com", "97778845632", TipoCliente.PESSOAFISICA);
		
		//Aqui instancio os telefones do cliente
		cli1.getTelefones().addAll(Arrays.asList("999090935", "992213331"));
		
		//Instancias de endereco
		Endereco e1 = new Endereco(null, "Rua rp10", "s/n", "Qd 21, Lt 06", "Jardim Paraiso", "75456224", cli1, c1 );
		Endereco e2 = new Endereco(null, "Rua rp13", "s/n", "Qd 20, Lt 26", "Jardim Flores", "75454724", cli1, c2 );
		
		//Associar o cliente tem que conhecer o endereco dele
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		//Salvar os cliente no banco de dados
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		//Salvar os enderecos no banco de dados
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
	}
	

}
