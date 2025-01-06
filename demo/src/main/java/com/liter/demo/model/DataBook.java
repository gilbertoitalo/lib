package com.liter.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DataAuthor> authors,
        @JsonAlias("languages") List<String> language,
        @JsonAlias("download_count") Integer dowloadsCounts
) {

    @Override

    public String toString() {

        String autores = authors.stream()
                .map(DataAuthor::name)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Desconhecido");

        return "-------------- Livro ----------------\n" +
                "Título: " + title + "\n" +
                "Autor: " + autores + "\n" +
                "Idioma: " + String.join(", ", language) + "\n" +
                "Número de Downloads: " + dowloadsCounts + "\n" +
                "--------------------------------------";
    }
}