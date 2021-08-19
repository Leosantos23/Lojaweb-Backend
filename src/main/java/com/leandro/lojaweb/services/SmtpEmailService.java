package com.leandro.lojaweb.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired // Para injetar a instancia referente la no check list
	private MailSender mailSender;

	// Logger
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);// Para enviar de verdade, ao inves de mostrar no to string, como anteriormente.
		LOG.info("Email enviado!");
	}
}
