package br.com.professorisidro.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.professorisidro.auth.model.Usuario;
import br.com.professorisidro.auth.security.JWTUtil;
import br.com.professorisidro.auth.security.UserToken;

@RestController
@CrossOrigin
public class AuthController {

	@PostMapping("/login")
	public ResponseEntity<UserToken> authenticate(@RequestBody Usuario usuario) {
		
		System.out.println("Estou no POST DO LOGIN");
		if (usuario.getUsername().equals("isidro")) {
			if (usuario.getPassword().equals("1234")) {
				UserToken token = new UserToken();
				token.setStrToken(JWTUtil.createToken(usuario.getUsername()));
				
				return ResponseEntity.ok(token);
			}
			return ResponseEntity.status(401).build();

		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/authorized")
	public String sayAuthorized() {
		return "Authorized Endpoint";
	}

}
