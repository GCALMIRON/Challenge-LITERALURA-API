package com.alura.Bookearte.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service

public class ConsumoAPI {
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient client;

    public ConsumoAPI() {
        this.client = HttpClient.newHttpClient();
    }

    public String obtenerDatos(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .header("User-Agent", "Java HttpClient")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error en la API. Status: " + response.statusCode());
            }

            if (response.body() == null || response.body().trim().isEmpty()) {
                throw new RuntimeException("La API devolvió una respuesta vacía");
            }

            return response.body();

        } catch (Exception e) {
            System.out.println("Error al consumir la API: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al consumir la API: " + e.getMessage());
        }
    }

    public String buscarLibroPorTitulo(String titulo) {
        try {
            String tituloEncoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String url = BASE_URL + "?search=" + tituloEncoded;
            return obtenerDatos(url);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar libro por título: " + e.getMessage());
        }
    }
}
