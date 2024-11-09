package com.alura.Bookearte.service;

import com.alura.Bookearte.model.Autor;
import com.alura.Bookearte.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepository.findAllWithLibros();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutoresPorAnio(int anio) {
        return autorRepository.findAutoresByAnio(anio);
    }

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

}