package com.pachedev.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pachedev.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    List<Book> findByAuthorAndPages(String author, Integer pages);

    List<Book> findByPagesBetween(Integer min, Integer max);
}
