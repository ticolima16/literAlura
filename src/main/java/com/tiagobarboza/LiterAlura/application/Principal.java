package com.tiagobarboza.LiterAlura.application;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.tiagobarboza.LiterAlura.model.Author;
import com.tiagobarboza.LiterAlura.model.Book;
import com.tiagobarboza.LiterAlura.model.DadosAutores;
import com.tiagobarboza.LiterAlura.model.DadosLivro;
import com.tiagobarboza.LiterAlura.model.Resposta;
import com.tiagobarboza.LiterAlura.repository.AuthorRepository;
import com.tiagobarboza.LiterAlura.repository.LivroRepository;
import com.tiagobarboza.LiterAlura.services.ConsumoAPI;
import com.tiagobarboza.LiterAlura.services.ConverteDados;

public class Principal {
	private Scanner scString = new Scanner(System.in);
	private Scanner keyboard = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados converteDados = new ConverteDados();
    private LivroRepository livroRespository;
    private AuthorRepository authorRepository;
	
    
    
	public Principal(LivroRepository livroRespository, AuthorRepository authorRepository) {
		this.livroRespository = livroRespository; 
		this.authorRepository = authorRepository;
	}

	public void showMenu() {
		int op =-1;
		do {
			System.out.println("--------------------------------------------");
			System.out.println("1- Buscar livro pelo titulo");
			System.out.println("2- Listar livros registrado");
			System.out.println("3- Listar autores registrado");
			System.out.println("4- Listar autores vivo em determinado ano");
			System.out.println("5- Listar livros em um determinado idioma");
			System.out.println("0- Sair");
			System.out.println("--------------------------------------------");
			System.out.print("Escolha o numero da opcao: ");
			op = keyboard.nextInt();
			
			switch (op) {
			case 1:
				buscarTituloLivro();
				break;
			case 2:
				livrosRegistrado();
				break;
			case 3:
				autoresRegistrado();
				break;
			case 4:
				buscarAutoreVivoPorAno();
				break;
			case 5:
				buscarLivroPorLinguagem();
				break;
			default:
				System.out.println("Opcao invalida! ");
			}
		} while (op != 0);
		
	}

    private void buscarTituloLivro() {
        String BASE_URL = "https://gutendex.com/books/?search=";
        System.out.println("\nEntre com o nome do livro que deseja buscar:");
        String title = scString.nextLine();

        if (!title.isBlank() && !isANumber(title)) {

            var json = consumoAPI.obterDados(BASE_URL + title.replace(" ", "%20"));
            var data = converteDados.obterDados(json, Resposta.class);
            Optional<DadosLivro> searchBook = data.results()
                    .stream()
                    .filter(b -> b.title().toLowerCase().contains(title.toLowerCase()))
                    .findFirst();

            if (searchBook.isPresent()) {
                DadosLivro dadosLivro = searchBook.get();

                if (!verifiedBookExistence(dadosLivro)) {
                    Book book = new Book(dadosLivro);
                    DadosAutores authorData = dadosLivro.author().get(0);
                    Optional<Author> optionalAuthor = authorRepository.findByName(authorData.name());

                    if (optionalAuthor.isPresent()) {
                        Author existingAuthor = optionalAuthor.get();
                        book.setAuthor(existingAuthor);
                        existingAuthor.getBooks().add(book);
                        authorRepository.save(existingAuthor);
                    } else {
                        Author newAuthor = new Author(authorData);
                        book.setAuthor(newAuthor);
                        newAuthor.getBooks().add(book);
                        authorRepository.save(newAuthor);
                    }

                    livroRespository.save(book);

                } else {
                    System.out.println("\nLivro adicionado ao banco");
                }

            } else {
                System.out.println("\nLivro nao encontrado");
            }

        } else {
            System.out.println("\nEntrada invalida");
        }

    }

    private void livrosRegistrado() {
        List<Book> books = livroRespository.findAll();

        if(!books.isEmpty()) {
            System.out.println("\n----- Livro registrado -----");
            books.forEach(System.out::println);
        } else {
            System.out.println("\nNada aqui");
        }

    }

    private void autoresRegistrado() {
        List<Author> authors = authorRepository.findAll();

        if(!authors.isEmpty()) {
            System.out.println("\n----- Autores registrados -----");
            authors.forEach(System.out::println);
        } else {
            System.out.println("\nNada aqui");
        }

    }

    private boolean verifiedBookExistence(DadosLivro bookData) {
        Book book = new Book(bookData);
        return livroRespository.verifiedBDExistence(book.getTitle());
    }

    private void buscarAutoreVivoPorAno() {
        System.out.println("\nEntre com o ano que deseja pesquisar: ");

        if (keyboard.hasNextInt()) {
            var year = keyboard.nextInt();
            List<Author> authors = authorRepository.findAuthorsAlive(year);

            if (!authors.isEmpty()) {
                System.out.println("\n----- Autores registrado vivo " + year + " -----");
                authors.forEach(System.out::println);
            } else {
                System.out.println("\nNenhum resultado, entre com outro ano");
            }

        } else {
            System.out.println("\nEntrada invalida");
            keyboard.next();
        }

    }

    private void buscarLivroPorLinguagem() {
        var option = -1;
        String language = "";

        System.out.println("\nSelecione a linguagem que deseja consultar ");
        var languagesMenu = """
               \n
               1 - English
               2 - French
               3 - German
               4 - Portuguese
               5 - Spanish
               """;

        System.out.println(languagesMenu);

        if (keyboard.hasNextInt()) {
            option = keyboard.nextInt();

            switch (option) {
                case 1:
                    language = "en";
                    break;
                case 2:
                    language = "fr";
                    break;
                case 3:
                    language = "de";
                    break;
                case 4:
                    language = "pt";
                    break;
                case 5:
                    language = "es";
                    break;
                default:
                    System.out.println("\nEntrada invalida");
            }

            System.out.println("\nLivros registrados:");
            List<Book> books = livroRespository.findBooksByLanguage(language);

            if (!books.isEmpty()) {
                books.forEach(System.out::println);
            } else {
                System.out.println("\nNenhum resultado, escolha outro ano ");
            }

        } else {
            System.out.println("\nEntrada invalida");
            keyboard.next();
        }

    }

    private boolean isANumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

 
}
