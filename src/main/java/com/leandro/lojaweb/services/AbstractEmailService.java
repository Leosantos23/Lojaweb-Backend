package com.leandro.lojaweb.services;

import java.util.Date;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.leandro.lojaweb.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	// Para instanciar la no remetente, em aplication.properties
	@Value("${default.sender}")
	private String sender;

	// Instanciar o engine
	@Autowired
	private TemplateEngine templateEngine;

	// Instanciar o java mail sender
	@Autowired
	private JavaMailSender javaMailSender;

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
		sm.setSubject("Pedido confirmado! Numero: " + obj.getId());
		// Data e horario do servidor
		sm.setSentDate(new Date(System.currentTimeMillis()));
		// Mensagem do email
		sm.setText(obj.toString());
		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		// Apartir do objeto context eu vou enviar o meu objeto pedido para o template
		// html
		context.setVariable("pedido", obj);
		// Agora tenho que processar o template para ele me retornar o html na forma de
		// string
		// Para isso vou precisar de uma instancia do template engine.
		return templateEngine.process("email/confirmacaoPedido", context);

	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		// Aqui trato a excessao
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);// Chamo o metodo la na interface e passo os parametros.
			
		} catch (MessagingException e) {
			// Porem se der erro mando o email em texto plano mesmo.
			sendOrderConfirmationEmail(obj);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		// Para eu instanciar um objeto mimessage, vou precisar injetar um objeto do
		// tipo javaMailSender
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		// Para eu ter o poder de atribuir valores a essa mensagem, vou precisar usar
		// MimeMessageHelper
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);

		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Codigo: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}

}
