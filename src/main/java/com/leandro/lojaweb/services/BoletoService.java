package com.leandro.lojaweb.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.leandro.lojaweb.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pgBoleto, Date momentoPedido) {

		Calendar calendario = Calendar.getInstance();
		calendario.setTime(momentoPedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);

		pgBoleto.setDataVencimento(calendario.getTime());

	}

}
