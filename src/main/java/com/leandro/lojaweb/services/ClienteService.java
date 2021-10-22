package com.leandro.lojaweb.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.leandro.lojaweb.domain.Cidade;
import com.leandro.lojaweb.domain.Cliente;
import com.leandro.lojaweb.domain.Endereco;
import com.leandro.lojaweb.domain.enums.Perfil;
import com.leandro.lojaweb.domain.enums.TipoCliente;
import com.leandro.lojaweb.dto.ClienteDTO;
import com.leandro.lojaweb.dto.ClienteNewDTO;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.repositories.EnderecoRepository;
import com.leandro.lojaweb.security.UserSpringSecurity;
import com.leandro.lojaweb.services.exceptions.AuthorizationException;
import com.leandro.lojaweb.services.exceptions.DataIntegrityException;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired // Aqui eu instancio o repositorio (repo) abaixo, que na qual sera automaticamente instanciada pelo SPRING.
	// Pelo mecanismo de injecao de dependencias, ou inversao de controle.
	private ClienteRepository repo;// Aqui declaro uma dependencia de um objeto do tipo cliente.

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	// Puxo o valor que esta la no aplication.properties para a variavel abaixo.
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	// Puxo o valor que esta la no aplication.properties para a variavel abaixo.
	@Value("${img.profile.size}")
	private Integer size;

	// Aqui vou fazer uma funcao de buscar a Cliente por ID.
	public Cliente find(Integer id) {

		UserSpringSecurity user = UserService.authenticated();
		// Testar com if
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}

		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	// Aqui ficara o insert
	// Metodo insert.
	@Transactional
	public Cliente insert(Cliente obj) {

		obj.setId(null);// Garantindo que o novo objeto sera nulo.
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;// Esse metodo tera de retornar o repositorio.
	}

	// Metodo update
	public Cliente update(Cliente obj) {

		Cliente newObj = find(obj.getId());// Busca e se der erro, ja lanca uma excessao.
		updateData(newObj, obj);// Foi criado outro metodo auxiliar
		return repo.save(newObj);// Esse metodo tera de retornar o repositorio.

	}

	public void delete(Integer id) {

		find(id);// Busca e se der erro, ja lanca uma excessao.
		try {
			repo.deleteById(id);// Aqui apaga pelo id
		} catch (DataIntegrityViolationException e) {

			throw new DataIntegrityException(
					"Nao e possivel excluir, porque existe pedidos relacionados a este cliente!");

		}

	}

	// Metodo listar todas Clientes
	public List<Cliente> findAll() {

		return repo.findAll();
	}

	// Metodo responsavel por paginar os clientes e mostrar organizadas usando o
	// PAGE
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		// Para fazer uma consulta e retornar uma pagina de dados, e preciso fazer outro
		// objeto do tipo PAGEREQUEST
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	// Metodo para fazer a busca por email
	public Cliente findByEmail(String email) {
		UserSpringSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado!");
		}
	
		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	// Metodo auxiliar, para instanciar um cliente apartir de um DTO,
	public Cliente fromDTO(ClienteDTO objDTO) {

		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());

		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	// Metodo auxiliar para atualizacao de cliente
	private void updateData(Cliente newObj, Cliente obj) {

		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	// Metodo para fazer o upload da foto de perfil do cliente
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSpringSecurity user = UserService.authenticated();

		// Verificar se tem alguem logado
		if (user == null) {
			throw new AuthorizationException("Acesso negado!");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		
		// Recorto a imagem.
		jpgImage = imageService.cropSquare(jpgImage);
		// Redimensiono a imagem.
		jpgImage = imageService.resize(jpgImage, size);


		// Montar o nome do arquivo personalizado com base no cliente que esta logado.
		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

	}

}
