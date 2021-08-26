package com.leandro.lojaweb.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leandro.lojaweb.security.JWTUtil;
import com.leandro.lojaweb.security.UserSpringSecurity;
import com.leandro.lojaweb.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;


	// Metodo de implementacao para dar refresh (resetar) o token, porem o usuario tem que estar logado no sistema
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

}
