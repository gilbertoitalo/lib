package com.liter.demo.services;

import com.liter.demo.model.Author;
import com.liter.demo.model.Book;
import com.liter.demo.model.DataBook;
import com.liter.demo.repository.AuthorRepository;
import com.liter.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void saveBook(DataBook dataBook) {

        Optional<Book> existingBook = BookRepository.findByTitle(dataBook.title());

        if (existingBook.isPresent()) {
            System.out.println("O livro '" + dataBook.title() + "' já está registrado no banco de dados.");
            return;
        }

        Book book = new Book(); 
        book.setTitle(dataBook.title());
        book.setDowloadsCounts(dataBook.dowloadsCounts());
        
        book.setLanguage(dataBook.language().isEmpty() ? null : dataBook.language().get(0));

        List<Author> authors = dataBook.authors().stream().map(dataAuthor -> {
            return authorRepository.findByName(dataAuthor.name())
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setName(dataAuthor.name());
                        newAuthor.setYearBorn(dataAuthor.yearBorn());
                        newAuthor.setYearDeath(dataAuthor.yearDeath());
                        return newAuthor;
                    });
        }).collect(Collectors.toList());


        authorRepository.saveAll(authors.stream().filter(author -> author.getId() == null).collect(Collectors.toList()));
        book.setAuthors(authors);


        bookRepository.save(book);
    }


    @Transactional(readOnly = true)
    public List<String> listBooks() {
        List<Book> books = bookRepository.findAll();


        for (Book book : books) {
            book.getAuthors().size();
        }


        return books.stream().map(book -> {
            String authors = book.getAuthors().stream()
                    .map(Author::getName)
                    .collect(Collectors.joining(", "));

            return "-------------- Livro ----------------\n" +
                    "Título: " + book.getTitle() + "\n" +
                    "Autores: " + authors + "\n" +
                    "Idioma: " + book.getLanguage() + "\n" +
                    "Número de Downloads: " + book.getDowloadsCounts() + "\n" +
                    "--------------------------------------";
        }).collect(Collectors.toList());
    }


    public Optional<Book> searchByTitle(String title) {
        return BookRepository.findByTitle(title);
    }


    @Transactional(readOnly = true)
    public List<Book> searchBooksByLanguage(String language) {
        List<Book> books = bookRepository.findByLanguageWithAuthors(language);


        return books;
    }
}