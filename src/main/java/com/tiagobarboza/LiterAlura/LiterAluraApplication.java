package com.tiagobarboza.LiterAlura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tiagobarboza.LiterAlura.application.Principal;
import com.tiagobarboza.LiterAlura.repository.AuthorRepository;
import com.tiagobarboza.LiterAlura.repository.LivroRepository;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner{
	
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal menu = new Principal(livroRepository, authorRepository);
		menu.showMenu();
		
	}

}
