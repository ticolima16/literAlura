package com.tiagobarboza.LiterAlura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Resposta(
        @JsonAlias("results") List<DadosLivro> results
) { }