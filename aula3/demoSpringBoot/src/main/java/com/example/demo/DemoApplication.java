package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;


@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/hello")
    public ResponseEntity<Saudacao> hello(@RequestParam(value = "name") String name) {
    if(name==null || name.isEmpty()){
		return ResponseEntity.badRequest().build();
	}
	return ResponseEntity.ok(new Saudacao("Hello, " + name + "!"));
	}

}

record Saudacao(String content) {}