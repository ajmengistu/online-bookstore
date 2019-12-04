package com.bookstore.onlinebookstore.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.onlinebookstore.model.Book;
import com.bookstore.onlinebookstore.model.Cart;
import com.bookstore.onlinebookstore.model.Item;
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

	public List<Book> getQueryResults(String query) {
		List<Book> list = bookRepository.findByTitleContainingOrAuthorsContaining(query, query);
		if (list.size() > 4)
			return list.subList(0, 4);
		else
			return list;
	}

	public List<Book> getSearchResults(String query) {
		List<Book> list = bookRepository.findByTitleContainingOrAuthorsContaining(query, query);
		if (list.size() > 25)
			return list.subList(0, 25);
		else
			return list;
	}

	public Book getBookById(String id) {
		return bookRepository.findById(Long.parseLong(id)).get();
	}

	public Book getBookById(Long id) {
		return bookRepository.findById(id).get();
	}

	public List<Book> getBooksByAuthor(String author) {
		return bookRepository.findTop25ByAuthorsContaining(author);
	}

	public List<Book> getBooksByAuthor(Book book) {
		List<Book> booksByAuthor = getBooksByAuthor(book.getAuthors());
		List<Book> popularBooks = getPopularBooks();
		if (booksByAuthor.isEmpty())
			return popularBooks;
		else {
			booksByAuthor.addAll(popularBooks.subList(0, 25 - booksByAuthor.size()));
			return booksByAuthor;
		}
	}

	public void updateBookStock(Cart cart) {
		for (Item item : cart.getShoppingCart()) {
			Book book = getBookById(item.getBook().getId());
			book.setStock(book.getStock() - item.getQuantity());
			bookRepository.save(book);
		}
	}
}