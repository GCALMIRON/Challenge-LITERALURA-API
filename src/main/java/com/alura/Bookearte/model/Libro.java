package com.alura.Bookearte.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "libro_tematica", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "tematica")
    private Set<String> tematica = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "libro_idioma", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private Set<String> idioma = new HashSet<>();

    private Integer descargas;

    // Constructores
    public Libro() {}

    public Libro(String titulo) {
        this.titulo = titulo;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = new HashSet<>(autores);
    }

    public Set<String> getTematicas() {
        return tematica;
    }

    public Set<String> getIdiomas() {
        return idioma;
    }

    public void setTematicas(List<String> tematica) {
        this.tematica = new HashSet<>(tematica);
    }

    public void setIdiomas(List<String> idioma) {
        this.idioma = new HashSet<>(idioma);
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    // Métodos de ayuda para mantener la bidireccionalidad con Autor
    public void addAutor(Autor autor) {
        autores.add(autor);
        autor.getLibros().add(this);
    }

}