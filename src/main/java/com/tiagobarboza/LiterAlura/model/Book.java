package com.tiagobarboza.LiterAlura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    private Author author;
    private List<String> languages = new ArrayList<>();
    private int downloads;

    public Book() { }

    public Book(DadosLivro livrosDados) {
        this.title = livrosDados.title();
        this.languages = livrosDados.languages();
        this.downloads = livrosDados.downloads();
        this.author = new Author(livrosDados.author().get(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "\n---------------------" +
                "\nTitle: " + title +
                "\nAuthor: " + author.getName() +
                "\nLanguages: " + languages +
                "\nDownloads: " + downloads +
                "\n---------------------";
    }
}