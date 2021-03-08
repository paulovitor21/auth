package br.com.professorisidro.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class FreeController {
	
	@GetMapping("/free")
	public String testeFree() {
		//System.out.println("acessei uma url sem autorizacao");
		return "Retornou sem necessidade de autorizacao";
	}
	
	

}
