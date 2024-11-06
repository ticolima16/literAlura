package com.tiagobarboza.LiterAlura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiagobarboza.LiterAlura.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE a.name LIKE %:name%")
    Optional<Author> findByName(@Param("name") String name);

    @Query("SELECT a FROM Author a WHERE :year BETWEEN CAST(a.birth_year AS integer) AND CAST(a.death_year AS integer)")
    List<Author> findAuthorsAlive(@Param("year") int year);
}
