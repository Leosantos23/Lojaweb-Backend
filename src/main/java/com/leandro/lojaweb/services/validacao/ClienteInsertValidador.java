package com.leandro.lojaweb.services.validacao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.leandro.lojaweb.domain.enums.TipoCliente;
import com.leandro.lojaweb.dto.ClienteNewDTO;
import com.leandro.lojaweb.repositories.ClienteRepository;
import com.leandro.lojaweb.resources.exceptions.CampoMensagem;
import com.leandro.lojaweb.services.validacao.utils.ValidaCpfCnpj;

public class ClienteInsertValidador implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<CampoMensagem> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
				&& !ValidaCpfCnpj.ValidaCPF(objDto.getCpfOuCnpj())) {
			list.add(new CampoMensagem("cpfOuCnpj", "CPF invalido!"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
				&& !ValidaCpfCnpj.validaCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new CampoMensagem("cpfOuCnpj", "CNPJ invalido"));
		}

		for (CampoMensagem e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}