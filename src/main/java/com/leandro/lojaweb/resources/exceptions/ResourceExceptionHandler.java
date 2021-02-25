package com.leandro.lojaweb.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leandro.lojaweb.services.exceptions.DataIntegrityException;
import com.leandro.lojaweb.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//Exception Not Found
	@ExceptionHandler(ObjectNotFoundException.class)//Para indicar que e um tratador da excecao.
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError erro = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
		
	}
	
	//Exception Data
	@ExceptionHandler(DataIntegrityException.class)//Para indicar que e um tratador da excecao.
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		StandardError erro = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		
	}
	
	//Exception MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)//Para indicar que e um tratador da excecao.
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ValidaErro erro = new ValidaErro(HttpStatus.BAD_REQUEST.value(), "Erro de validacao", System.currentTimeMillis());
		
		//Aqui terei de percorrer o erro gerado, para imprimir na tela personalizado.
		for (FieldError x : e .getBindingResult().getFieldErrors()) {
			erro.addErro(x.getField(), x.getDefaultMessage());
		}
			
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
		
	}
}
