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

	public List<Book> getTopRatedBooks() {
		List<Book> list = bookRepository.findTop25ByOrderByAverageRatingDesc(); // list of top rated books random order
		Collections.shuffle(list);
		return list;
	}

	public List<Book> getTopRatedBooksByYear() {
		return bookRepository.findTop25RatedBooksByYearDesc(); // list of top rated books by year
	}

	public List<Book> getAncientLiteratureBooks() {
		List<Book> list = bookRepository.findTop25ByOrderByPubYearAsc();
		Collections.shuffle(list);
		return list; // list of ancient literature books
	}

	public List<Book> getPopularBooks() {
		List<Book> list = bookRepository.findTop25ByOrderByRatingsDesc();
		Collections.shuffle(list);
		return list; // list of top 25 popular books in random order
	}

	public List<Book> getSearchResults(String query) {
		List<Book> list = bookRepository.findByTitleContainingOrAuthorsContaining(query, query);
		if (list.size() > 4)
			return list.subList(0, 4);
		else
			return list;
	}
}