package com.leandro.lojaweb.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.leandro.lojaweb.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	// Paraintanciarlano remetente, em aplication.properties
	@Value("${default.sender}")
	private String sender;

	// Implementacoes dos metodos de envio
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);// Chamo o metodo la na interface e passo os parametros.
	}

	// Metodo para gerar o simple mail message
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		// Remetente
		sm.setFrom(sender);
		// Assunto
		sm.setSubject("Pedido confirmado! Codigo: " + obj.getId());
		//Data e horario do servidor
		sm.setSentDate(new Date(System.currentTimeMillis()));
		//Mensagem do email
		sm.setText(obj.toString());
		return sm;
	}

}
