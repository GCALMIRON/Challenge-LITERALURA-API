package com.alura.Bookearte.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service

public class ConvierteDatos implements IConvierteDatos {
    private final ObjectMapper objectMapper;

    public ConvierteDatos() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);

            // Verificar si es una respuesta de búsqueda (contiene "results")1
            if (rootNode.has("results") && rootNode.get("results").isArray()) {
                JsonNode results = rootNode.get("results");
                if (results.size() > 0) {
                    // Tomar el primer resultado
                    return objectMapper.treeToValue(results.get(0), clase);
                }
                return null; // No se encontraron resultados
            }

            // Si no es una búsqueda, procesar como un libro individual
            return objectMapper.treeToValue(rootNode, clase);

        } catch (Exception e) {
            throw new RuntimeException("Error al convertir datos: " + e.getMessage());
        }
    }
}