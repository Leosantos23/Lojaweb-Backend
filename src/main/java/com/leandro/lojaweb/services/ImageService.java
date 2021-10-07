package com.leandro.lojaweb.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leandro.lojaweb.resources.exceptions.FileException;

// Classe que sera um servico responsavel de funcionalidades de imagens
@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {

		// Primeiro eu pego a extensao do arquivo.
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());

		// Verifica as extensoes
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas!");
		}
		// Agora vou tentar obter um buffer de imagem (BufferedImage) , atraves do multpartfile (MultipartFile).
		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if ("png".equals(ext)) {
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo!");
		}
	}
	
	// Metodo responsavem em converter png para jpg.
	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	
	// Metodo responsavel em retornar um ImputStream, que e o objeto que encapsula leitura apartir de um BufferedImage.
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo!");
		}
	}
	
	// Metodo especifico para tratamento de recorte da imagem.
	public BufferedImage cropSquare(BufferedImage sourceImg) {
		
		// Descobrir qual e o minimo da imagem, ou seja,  a largura ou a altura.
		// Usando uma estrutura de condicao ternaria usando a ? e :
		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
		
		// Apos descobrir qual e o tamanho minimo, mando cortar (cropar) a minha imagem.
		return Scalr.crop(
			sourceImg, 
			// Largura - a metade do minimo.
			(sourceImg.getWidth()/2) - (min/2), 
			// Altura - a metade do minimo.
			(sourceImg.getHeight()/2) - (min/2), 
			min, 
			min);		
	}
	
	// Metodo especifico para tratamento de redimensionamento da imagem.
	// Ele vai receber uma imagem e definir o tamanho que eu quero que ela seja cortada.
	public BufferedImage resize(BufferedImage sourceImg, int size) {
		// Chamo a funcao resize da classe scalr.
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}




}
