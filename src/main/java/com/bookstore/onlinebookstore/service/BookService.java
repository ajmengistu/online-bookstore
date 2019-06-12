package com.bookstore.onlinebookstore.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	// list of top rated books

	// list of top rated books by year

	// list of ancient literature books

	public List<Book> getPopularBooks() {
		List<Book> list = bookRepository.findTop25ByOrderByRatingsDesc();
		Collections.shuffle(list);
		return list; // list of top 25 popular books in random order
	}
}
