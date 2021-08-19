package com.leandro.lojaweb.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired // Para injetar a instancia referente la no check list
	private MailSender mailSender;
	
	@Autowired//Inject de java mail sender
	private JavaMailSender javaMailSender;
	

	// Logger
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);// Para enviar de verdade, ao inves de mostrar no to string, como anteriormente.
		LOG.info("Email enviado!");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {

		LOG.info("Enviando email...");
		javaMailSender.send(msg);//Agora terei de usar a injection
		LOG.info("Email enviado!");

	}
}
