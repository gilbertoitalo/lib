package com.liter.demo.repository;

import com.liter.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    static Optional<Book> findByTitle(String title) {
        return null;
    }


    List<Book> findByLanguage(String language );


    @Query("SELECT l FROM Book l JOIN FETCH l.authors WHERE l.language = :language")
    List<Book> findByLanguageWithAuthors(@Param("language") String language);
}
