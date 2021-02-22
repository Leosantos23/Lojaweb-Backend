package com.leandro.lojaweb;

import java.text.SimpleDateFormat;
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
import com.leandro.lojaweb.domain.ItemPedido;
import com.leandro.lojaweb.domain.Pagamento;
import com.leandro.lojaweb.domain.PagamentoComBoleto;
import com.leandro.lojaweb.domain.PagamentoComCartao;
import com.leandro.lojaweb.domain.Pedido;
import com.leandro.lojaweb.domain.Produto;
import com.leandro.lojaweb.domain.enums.StatusPagamento;
import com.leandro.lojaweb.domain.enums.TipoCliente;
import com.leandro.lojaweb.repositories.CategoriaRepository;
import com.leandro.lojaweb.repositories.CidadeRepository;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.repositories.EnderecoRepository;
import com.leandro.lojaweb.repositories.EstadoRepository;
import com.leandro.lojaweb.repositories.ItemPedidoRepository;
import com.leandro.lojaweb.repositories.PagamentoRepository;
import com.leandro.lojaweb.repositories.PedidoRepository;
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
	private PedidoRepository pedidoRepository;
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private PagamentoRepository pagamentoRepository;
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private EnderecoRepository enderecoRepository;
	
	//Aqui chamo o repositorio
	@Autowired//Para ser instanciado automaticamente
	private ItemPedidoRepository itemPedidoRepository;

	
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
		
		//Formatar a data
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//Instancias de Pedidos
		Pedido ped1 = new Pedido (null, sdf.parse("19/02/2021 10:55"), cli1, e1);
		Pedido ped2 = new Pedido (null, sdf.parse("20/02/2021 11:55"), cli1, e2);
		
		//Instancias de pagamentos
		Pagamento pg1 = new PagamentoComCartao(null, StatusPagamento.PAGO, ped1, 6);
		ped1.setPagamento(pg1);
		
		Pagamento pg2 = new PagamentoComBoleto(null, StatusPagamento.PENDENTE, ped2, sdf.parse("20/03/2021 00:00"), null);
		ped2.setPagamento(pg2);
		
		//Associar o cliente com os pedidos dele
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		//Salvar os pedidos e pagamentos
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pg1, pg2));
		
		//Instancias de pedidos
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		//Associar cada pedido com os itens dele
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//Associar cada produto com os seus itens
        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));
        
        //Salvar no banco de dados
        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
        	
	}

}
