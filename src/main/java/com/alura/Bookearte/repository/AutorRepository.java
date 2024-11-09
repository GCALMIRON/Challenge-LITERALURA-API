package com.alura.Bookearte.repository;

import com.alura.Bookearte.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros " +
            "WHERE a.anioNacimiento <= :anio AND (a.anioFallecimiento >= :anio OR a.anioFallecimiento IS NULL)")
    List<Autor> findAutoresByAnio(int anio);
}
