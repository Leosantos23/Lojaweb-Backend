package com.leandro.lojaweb.services;

import org.springframework.mail.SimpleMailMessage;

import com.leandro.lojaweb.domain.Pedido;

//Interface de servico de email, define quais operacoes nosso servico de email deve oferecer.
public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
		

}
