package com.liter.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String language
            ;

    private int dowloadsCounts
            ;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = titulo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getDowloadsCounts() {
        return dowloadsCounts;
    }

    public void setDowloadsCounts(int numeroDownloads) {
        this.dowloadsCounts = numeroDownloads;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
}













/*public class Book {
    private String id;
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    private List<String> safeAuthors() {
        return authors != null ? authors : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" +
                "Author(s): " + String.join(", ", safeAuthors()) + "\n" +
                "Publisher: " + publisher + "\n" +
                "Published Date: " + publishedDate + "\n" +
                "Pages: " + pageCount + "\n" +
                "Description: " + (description != null ? description.substring(0, Math.min(200, description.length())) + "..." : "N/A") + "\n";
    }
}
*/
