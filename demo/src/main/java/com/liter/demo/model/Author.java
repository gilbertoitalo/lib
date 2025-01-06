package com.liter.demo.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer yearBorn;

    private Integer yearDeath;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = name;
    }

    public Integer getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(Integer yearBorn) {
        this.yearBorn = yearBorn;
    }

    public Integer getYearDeath() {
        return yearDeath;
    }

    public void setYearDeath(Integer yearDeath) {
        this.yearDeath = yearDeath;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book>  books) {
        this.books = books;
    }


    public void addBook(Book book) {
        this.books.add(book);
        book.getAuthors().add(this); // Ver erro later
    }

}
