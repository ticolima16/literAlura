package com.tiagobarboza.LiterAlura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String birth_year;
    private String death_year;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author() {}

    public Author(DadosAutores dadosAutores) {
        this.name = dadosAutores.name();
        this.birth_year = dadosAutores.birth_year();
        this.death_year = dadosAutores.death_year();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }

    public String getDeath_year() {
        return death_year;
    }

    public void setDeath_year(String death_year) {
        this.death_year = death_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = new ArrayList<>();
        books.forEach(b -> {
            b.setAuthor(this);
            this.books.add(b);
        });
    }

    @Override
    public String toString() {
        List<String> books = this.getBooks().stream().map(Book::getTitle).toList();
        return "\n---------------------" +
                "\nName: " + name +
                "\nBirth year: " + birth_year +
                "\nDeath year: " + death_year +
                "\nBooks: " + books +
                "\n---------------------";
    }
}