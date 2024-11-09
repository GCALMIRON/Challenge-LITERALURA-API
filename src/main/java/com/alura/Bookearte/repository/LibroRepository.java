package com.alura.Bookearte.repository;

import com.alura.Bookearte.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Libro l WHERE LOWER(l.titulo) = LOWER(:titulo)")
    boolean existsByTituloIgnoreCase(@Param("titulo") String titulo);

    Optional<Libro> findByTituloIgnoreCase(String titulo);

    @Query("SELECT DISTINCT l FROM Libro l " +
            "LEFT JOIN FETCH l.autores " +
            "LEFT JOIN FETCH l.tematica " +
            "LEFT JOIN FETCH l.idioma")
    List<Libro> findAllWithDetails();

    @Query("SELECT DISTINCT l FROM Libro l " +
            "LEFT JOIN FETCH l.autores " +
            "LEFT JOIN FETCH l.tematica " +
            "LEFT JOIN FETCH l.idioma " +
            "WHERE :idioma MEMBER OF l.idioma")
    List<Libro> findByIdiomaContaining(@Param("idioma") String idioma);

    @Query("SELECT DISTINCT l FROM Libro l " +
            "LEFT JOIN FETCH l.autores " +
            "LEFT JOIN FETCH l.tematica " +
            "LEFT JOIN FETCH l.idioma " +
            "ORDER BY l.descargas DESC")
    List<Libro> findAllOrderByDescargasDesc();

    @Query("SELECT DISTINCT l FROM Libro l " +
            "LEFT JOIN FETCH l.autores " +
            "LEFT JOIN FETCH l.tematica " +
            "LEFT JOIN FETCH l.idioma " +
            "ORDER BY l.descargas DESC")
    List<Libro> findTop10ByOrderByDescargasDesc(Pageable pageable);
}