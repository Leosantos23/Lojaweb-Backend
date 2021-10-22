package com.leandro.lojaweb.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.domain.ItemPedido;
import com.leandro.lojaweb.domain.PagamentoComBoleto;
import com.leandro.lojaweb.domain.Pedido;
import com.leandro.lojaweb.domain.enums.StatusPagamento;
import com.leandro.lojaweb.repositories.ItemPedidoRepository;
import com.leandro.lojaweb.repositories.PagamentoRepository;
import com.leandro.lojaweb.repositories.PedidoRepository;
import com.leandro.lojaweb.security.UserSpringSecurity;
import com.leandro.lojaweb.services.exceptions.AuthorizationException;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired // Aqui eu instancio o repositorio (repo) abaixo, que na qual sera
				// automaticamente instanciada pelo SPRING, Pelo mecanismo de injecao de
				// dependencias, ou inversao de controle.
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ClienteService clienteService;

	// Injetar uma instancia para email service
	@Autowired
	private EmailService emailService;

	// Aqui vou fazer uma funcao de buscar a Categoria por ID.
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public @Valid Pedido insert(@Valid Pedido obj) {
		// Setar o id desse objeto para nulo, para garantir que realmente estou
		// inserindo um novo pedido.
		obj.setId(null);

		// Setar o cliente ao pedido.
		obj.setCliente(clienteService.find(obj.getCliente().getId()));

		// Setar o instante desse pedido como sendo um new date, que garante uma nova
		// data com o momento atual.
		obj.setInstante(new Date());
		// Status do pagamento
		obj.getPagamento().setStatus(StatusPagamento.PENDENTE);
		// Associacao de mao dupla
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgBoleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgBoleto, obj.getInstante());
		}

		// Salvar o pedido no banco
		obj = repo.save(obj);
		// Salvar o pagamento no banco
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		// emailService.sendOrderConfirmationEmail(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

		UserSpringSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");

		}

		//Para fazer uma consulta e retornar uma pagina de dados, e preciso fazer outro objeto do tipo PAGEREQUEST
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);

	}
}
