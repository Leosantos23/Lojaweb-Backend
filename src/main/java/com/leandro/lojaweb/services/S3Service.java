package com.leandro.lojaweb.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	// Metodo basico do servico
	public URI uploadFile(MultipartFile multipartFile) {

		try {

			// Crio uma variavel para receber o nome deste arquivo vindo pelo multipartfile.
			String fileName = multipartFile.getOriginalFilename();

			// Instancio atraves do inputstream, ele encapsula o processamento de leitura apartir de uma origem.
			InputStream is = multipartFile.getInputStream();

			// Instancio uma string contendo a informacao do tipo de arquivo que foi enviado.
			String contentType = multipartFile.getContentType();

			// Retorna os tres dados.
			return uploadFile(is, fileName, contentType);

		} catch (IOException e) {
			throw new RuntimeException("Erro de IO: " + e.getMessage());
		}
	}

	// Metodo para aumentar a modularidade aqui do meu servico, criando uma
	// sobrecarga do metodo acima, porem recebendo outros parametros.
	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("Iniciando upload...");
			s3client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload finalizado!");
			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL para URI!");
		}
	}

}
