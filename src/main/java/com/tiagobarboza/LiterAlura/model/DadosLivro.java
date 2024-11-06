package com.tiagobarboza.LiterAlura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro (
		 @JsonAlias("Title") String title,
	        @JsonAlias("authors") List<DadosAutores> author,
	        @JsonAlias("languages") List<String> languages,
	        @JsonAlias("download_count") int downloads
	) { }
