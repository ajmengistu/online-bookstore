package com.bookstore.onlinebookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.onlinebookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	public List<Book> findTop25ByOrderByRatingsDesc();
	public List<Book> findByTitleContaining(String title);
	public List<Book> findByTitleContainingOrAuthorsContaining(String title, String authors);
}
