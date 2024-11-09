package com.alura.Bookearte.service;

import com.alura.Bookearte.model.Libro;
import com.alura.Bookearte.model.Autor;
import com.alura.Bookearte.model.DatosLibro;
import com.alura.Bookearte.repository.LibroRepository;
import com.alura.Bookearte.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos convierteDatos;

    public LibroService(LibroRepository libroRepository,
                        AutorRepository autorRepository,
                        ConsumoAPI consumoAPI,
                        ConvierteDatos convierteDatos) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.consumoAPI = consumoAPI;
        this.convierteDatos = convierteDatos;
    }

    @Transactional
    public Libro buscarYGuardarLibro(String titulo) {
        // Verificar si el libro ya existe (ignorando mayúsculas/minúsculas)
        Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(titulo);
        if (libroExistente.isPresent()) {
            System.out.println("El libro '" + titulo + "' ya existe en la base de datos");
            return libroExistente.get();
        }

        // Buscar en la API externa
        String json = consumoAPI.buscarLibroPorTitulo(titulo);

        // Log del JSON recibido
        //System.out.println("JSON recibido de la API: " + json);

        DatosLibro datosLibro = convierteDatos.obtenerDatos(json, DatosLibro.class);

        if (datosLibro == null) {
            throw new RuntimeException("Libro no encontrado en la API externa");
        }

        // Verificar nuevamente por el título exacto retornado por la API
        if (libroRepository.existsByTituloIgnoreCase(datosLibro.titulo())) {
            throw new RuntimeException("El libro ya existe en la base de datos");
        }

        // Log de los datos convertidos
        //System.out.println("Descargas recibidas de la API: " + datosLibro.descargas());

        // Logs para debug
        //System.out.println("Título recibido: " + datosLibro.titulo());
        //System.out.println("Temáticas recibidas: " + datosLibro.tematica());
        //System.out.println("Idiomas recibidos: " + datosLibro.idioma());

        // Crear una nueva instancia de Libro y asignar los datos
        Libro libro = new Libro();
        libro.setTitulo(datosLibro.titulo());
        libro.setDescargas(datosLibro.descargas());

        // Log posterior a asignar descargas
        //System.out.println("Descargas asignadas al libro: " + libro.getDescargas());

        // Otros logs después de la asignación
        //System.out.println("Asignando temáticas...");
        //libro.setTematicas(datosLibro.tematica());
        //System.out.println("Temáticas asignadas: " + libro.getTematicas());

        //System.out.println("Asignando idiomas...");
        libro.setIdiomas(datosLibro.idioma());
        //System.out.println("Idiomas asignados: " + libro.getIdiomas());

        // Convertir y guardar autores
        List<Autor> autores = getOrSaveAutores(datosLibro.autores());
        libro.setAutores(autores);

        // Guardar el libro y agregar log del resultado
        Libro libroGuardado = libroRepository.save(libro);
        System.out.println("Libro guardado con ID: " + libroGuardado.getId());
        System.out.println("Temáticas guardadas: " + libroGuardado.getTematicas());
        System.out.println("Idiomas guardados: " + libroGuardado.getIdiomas());

        // Después de guardar
        System.out.println("Descargas guardadas en BD: " + libroGuardado.getDescargas());

        return libroGuardado;
    }

    private List<Autor> getOrSaveAutores(List<DatosLibro.Autor> autorData) {
        List<Autor> autores = new ArrayList<>();
        for (DatosLibro.Autor a : autorData) {
            // Buscar si el autor ya existe en la base de datos
            Optional<Autor> autorOptional = autorRepository.findByNombre(a.name());
            if (autorOptional.isPresent()) {
                // Si el autor ya existe, agregarlo a la lista
                autores.add(autorOptional.get());
            } else {
                // Si el autor no existe, crear uno nuevo y guardarlo
                Autor nuevoAutor = new Autor(a.name(), a.birthYear(), a.deathYear());
                Autor autorGuardado = autorRepository.save(nuevoAutor);
                autores.add(autorGuardado);
            }
        }
        return autores;
    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepository.findAllWithDetails();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarPorIdioma(String idioma) {
        return libroRepository.findByIdiomaContaining(idioma);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarPorDescargas() {
        // Crear un PageRequest para limitar a 10 resultados
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Libro> libros = libroRepository.findTop10ByOrderByDescargasDesc(pageRequest);

        // Podemos agregar un log para verificar
        //libros.forEach(libro -> {
        //    System.out.println(String.format("Libro: %s - Descargas: %d",
        //            libro.getTitulo(),
        //            libro.getDescargas()));
        //});
        return libros;
    }
}