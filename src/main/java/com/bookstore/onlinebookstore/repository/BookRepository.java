package com.bookstore.onlinebookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookstore.onlinebookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	public List<Book> findTop25ByOrderByRatingsDesc();

	public List<Book> findByTitleContaining(String title);

	public List<Book> findByTitleContainingOrAuthorsContaining(String title, String authors);

	public List<Book> findTop25ByOrderByAverageRatingDesc();

	@Query(value = "WITH temp AS (SELECT b.book_id, b.authors, b.title, b.average_rating, b.book_format, b.image, b.isbn, b.price, b.pub_year, b.ratings, b.stock"
			+ " FROM (SELECT pub_year, MAX(average_rating) AS average_rating FROM books GROUP BY pub_year ORDER BY pub_year DESC LIMIT 25) AS x"
			+ " INNER JOIN books AS b ON x.pub_year=b.pub_year AND x.average_rating=b.average_rating)"
			+ " SELECT * FROM temp ORDER BY pub_year DESC;", nativeQuery = true)
	public List<Book> findTop25RatedBooksByYearDesc();

	public List<Book> findTop25ByOrderByPubYearAsc();

	public List<Book> findTop25ByAuthorsContaining(String author);
}