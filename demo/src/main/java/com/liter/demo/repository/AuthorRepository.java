package com.liter.demo.repository;


import com.liter.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Autor a JOIN FETCH a.livros")
    List<Author> findAllWithLivros();
}
