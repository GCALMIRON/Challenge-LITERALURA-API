package com.alura.Bookearte.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<Autor> autores,
        @JsonAlias("subjects") List<String> tematica,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Integer descargas
) {
    public List<String> tematica() {
        return tematica;
    }

    public List<String> idioma() {
        return idioma;
    }

    public record Autor(
            String name,
            @JsonAlias("birth_year") Integer birthYear,
            @JsonAlias("death_year") Integer deathYear
    ) {}
}