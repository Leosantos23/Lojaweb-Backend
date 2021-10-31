package com.leandro.lojaweb.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter {

	// Neste metodo de inicio eu nao quero executar nada
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	// Neste filtro eu vou expor o cabecalho header location nas respostas
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/*Aqui faco um CASTING de adaptacao na resposta usando HttpServletResponse
		 * declarando uma variavel res, recebendo um casting (HttpServletResponse),
		 * do parametro response para ele se tornar um HttpServletResponse
		 */
		HttpServletResponse res = (HttpServletResponse) response;
		
		// Agora apartir da variavel res, ai sim eu posso chamar o metodo addHeader de ServletResponse
		res.addHeader("access-control-expose-headers", "location");
		// Terminar o metodo uso o objeto chain
		chain.doFilter(request, response);
	}

	// Neste metodo de destruir eu nao vou executar nada por enquanto
	@Override
	public void destroy() {
	}

}
