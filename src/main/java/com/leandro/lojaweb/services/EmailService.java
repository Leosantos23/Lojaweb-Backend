package com.leandro.lojaweb.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.leandro.lojaweb.domain.Pedido;

//Interface de servico de email, define quais operacoes nosso servico de email deve oferecer.
public interface EmailService {
	
	//Para funcionar o email em texto plano
	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
	
	//Para funcionar o email html
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
		

}
