package com.tiagobarboza.LiterAlura.repository;




import com.tiagobarboza.LiterAlura.model.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Book, Long> {
    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE b.title LIKE %:title%")
    Boolean verifiedBDExistence(@Param("title") String title);

    @Query(value = "SELECT * FROM books WHERE :language = ANY (books.languages)", nativeQuery = true)
    List<Book> findBooksByLanguage(@Param("language") String language);

}