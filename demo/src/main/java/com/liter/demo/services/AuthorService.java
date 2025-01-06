package com.liter.demo.services;

import com.liter.demo.model.Author;
import com.liter.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    @Transactional(readOnly = true)
    public List<String> listAuthors() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream().map(autor -> {
            String livros = autor.getBooks().stream()
                    .map(livro -> livro.getTitle())
                    .collect(Collectors.joining(", "));

            return "Autor: " + autor.getName() + "\n" +
                    "Ano de nascimento: " + autor.getYearBorn() + "\n" +
                    "Ano de falecimento: " + (autor.getYearDeath() != null ? autor.getYearDeath() : "N/A") + "\n" +
                    "Livros: [" + livros + "]\n";
        }).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<String> listLivingAuthorsInYear(int year) {
        List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .filter(author -> author.getYearBorn() <= year &&
                        (author.getYearDeath() == null || author.getYearDeath() > year))
                .map(author -> {
                    String books = author.getBooks().stream()
                            .map(book -> book.getTitle())
                            .collect(Collectors.joining(", "));

                    return "Autor: " + author.getName() + "\n" +
                            "Ano de nascimento: " + author.getYearBorn() + "\n" +
                            "Ano de falecimento: " + (author.getYearDeath() != null ? author.getYearDeath() : "N/A") + "\n" +
                            "Livros: [" + books + "]\n";
                })
                .collect(Collectors.toList());
    }
}
