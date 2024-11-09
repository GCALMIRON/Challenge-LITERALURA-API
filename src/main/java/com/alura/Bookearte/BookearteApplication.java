package com.alura.Bookearte;

import com.alura.Bookearte.model.Libro;
import com.alura.Bookearte.model.Autor;
import com.alura.Bookearte.service.LibroService;
import com.alura.Bookearte.service.AutorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Scanner;
import java.util.List;

@SpringBootApplication
public class BookearteApplication implements CommandLineRunner {
	private final Scanner scanner;
	private final LibroService libroService;
	private final AutorService autorService;

	public BookearteApplication(LibroService libroService, AutorService autorService) {
		this.scanner = new Scanner(System.in);
		this.libroService = libroService;
		this.autorService = autorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookearteApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("¡Bienvenido a Bookearte!");
		mostrarBanner();

		while (true) {
			try {
				mostrarMenu();
				int opcion = obtenerOpcion();

				switch (opcion) {
					case 1 -> buscarLibroPorTitulo();
					case 2 -> listarLibrosRegistrados();
					case 3 -> listarAutoresRegistrados();
					case 4 -> listarAutoresPorAnio();
					case 5 -> listarLibrosPorIdioma();
					case 6 -> {
						System.out.println("\nTop 10 libros más descargados:");
						List<Libro> topLibros = libroService.listarPorDescargas();
						topLibros.forEach(libro -> {
							System.out.println("\nTítulo: " + libro.getTitulo());
							System.out.println("Descargas: " + libro.getDescargas());
							System.out.println("Autor(es):");
							libro.getAutores().forEach(autor -> System.out.print(autor.getNombre() + "  "));
							System.out.println();});
						}
					case 0 -> {
						System.out.println("\n¡Gracias por usar Bookearte! ¡Hasta luego!");
						scanner.close();
						return;
					}
					default -> System.out.println("\nOpción no válida. Por favor, intente nuevamente.");
				}
			} catch (Exception e) {
				System.out.println("\nError en la aplicación: " + e.getMessage());
				System.out.println("Por favor, intente nuevamente.");
			}
		}
	}

	private void mostrarBanner() {
		System.out.println("\n" +
				"██████╗  ██████╗  ██████╗ ██╗  ██╗███████╗ █████╗ ██████╗ ████████╗███████╗\n" +
				"██╔══██╗██╔═══██╗██╔═══██╗██║ ██╔╝██╔════╝██╔══██╗██╔══██╗╚══██╔══╝██╔════╝\n" +
				"██████╔╝██║   ██║██║   ██║█████╔╝ █████╗  ███████║██████╔╝   ██║   █████╗  \n" +
				"██╔══██╗██║   ██║██║   ██║██╔═██╗ ██╔══╝  ██╔══██║██╔══██╗   ██║   ██╔══╝  \n" +
				"██████╔╝╚██████╔╝╚██████╔╝██║  ██╗███████╗██║  ██║██║  ██║   ██║   ███████╗\n" +
				"╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝\n");
	}

	private void mostrarMenu() {
		System.out.println("\n=== BOOKEARTE MENU ===");
		System.out.println(" ");
		System.out.println("1. Buscar libro por título");
		System.out.println("2. Listar libros registrados");
		System.out.println("3. Listar autores registrados");
		System.out.println("4. Listar autores por año");
		System.out.println("5. Listar libros por idioma");
		System.out.println("6. TOP 10 libros más descargados");
		System.out.println("0. Salir");
		System.out.print("\nSeleccione una opción: ");
	}

	private int obtenerOpcion() {
		try {
			return Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private void buscarLibroPorTitulo() {
		System.out.print("\nIngrese el título del libro a buscar: ");
		String titulo = scanner.nextLine().trim();

		if (titulo.isEmpty()) {
			System.out.println("El título no puede estar vacío.");
			return;
		}

		try {
			Libro libro = libroService.buscarYGuardarLibro(titulo);
			System.out.println("\n¡Libro guardado exitosamente!");
			mostrarDatosLibro(libro);
		} catch (Exception e) {
			System.out.println("\nError: " + e.getMessage());
		}
	}

	private void listarLibrosRegistrados() {
		List<Libro> libros = libroService.listarTodos();
		if (libros.isEmpty()) {
			System.out.println("\nNo hay libros registrados en la base de datos.");
			return;
		}

		System.out.println("\n=== LIBROS REGISTRADOS ===");
		System.out.println("Total de libros: " + libros.size());
		libros.forEach(libro -> {
			System.out.println("\n-------------------");
			mostrarDatosLibro(libro);
		});
	}

	private void listarAutoresRegistrados() {
		List<Autor> autores = autorService.listarTodos();
		if (autores.isEmpty()) {
			System.out.println("\nNo hay autores registrados en la base de datos.");
			return;
		}

		System.out.println("\n=== AUTORES REGISTRADOS ===");
		System.out.println("Total de autores: " + autores.size());
		autores.forEach(autor -> {
			System.out.println("\n- " + autor.getNombre());
			if (autor.getAnioNacimiento() != null || autor.getAnioFallecimiento() != null) {
				System.out.print("  (");
				if (autor.getAnioNacimiento() != null) {
					System.out.print(autor.getAnioNacimiento());
				}
				System.out.print(" - ");
				if (autor.getAnioFallecimiento() != null) {
					System.out.print(autor.getAnioFallecimiento());
				} else {
					System.out.print("presente");
				}
				System.out.println(")");
			}
			if (!autor.getLibros().isEmpty()) {
				System.out.println("  Libros: " + autor.getLibros().size());
			}
		});
	}

	private void listarAutoresPorAnio() {
		System.out.print("\nIngrese el año a consultar: ");
		try {
			int anio = Integer.parseInt(scanner.nextLine().trim());
			if (anio < 0 || anio > 2024) {
				System.out.println("Por favor, ingrese un año válido.");
				return;
			}

			List<Autor> autores = autorService.listarAutoresPorAnio(anio);
			if (autores.isEmpty()) {
				System.out.println("\nNo se encontraron autores vivos en el año " + anio);
				return;
			}

			System.out.println("\n=== AUTORES VIVOS EN " + anio + " ===");
			System.out.println("Total de autores: " + autores.size());
			autores.forEach(autor -> {
				System.out.println("\n- " + autor.getNombre() +
						" (" + autor.getAnioNacimiento() + " - " +
						(autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "presente") + ")");
			});
		} catch (NumberFormatException e) {
			System.out.println("Por favor, ingrese un año válido.");
		}
	}

	private void listarLibrosPorIdioma() {
		System.out.print("\nIngrese el idioma (es, en, fr, pt): ");
		String idioma = scanner.nextLine().trim().toLowerCase();

		if (!idioma.matches("^(es|en|fr|pt)$")) {
			System.out.println("Idioma no válido. Use: es, en, fr, o pt");
			return;
		}

		List<Libro> libros = libroService.listarPorIdioma(idioma);
		if (libros.isEmpty()) {
			System.out.println("\nNo se encontraron libros en el idioma " + idioma.toUpperCase());
			return;
		}

		System.out.println("\n=== LIBROS EN " + idioma.toUpperCase() + " ===");
		System.out.println("Total de libros: " + libros.size());
		libros.forEach(libro -> {
			System.out.println("\n-------------------");
			mostrarDatosLibro(libro);
		});
	}

	private void mostrarDatosLibro(Libro libro) {
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Número de descargas: " + libro.getDescargas());

		if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
			System.out.println("Autores:");
			libro.getAutores().forEach(autor -> {
				StringBuilder autorStr = new StringBuilder("  - " + autor.getNombre());
				if (autor.getAnioNacimiento() != null || autor.getAnioFallecimiento() != null) {
					autorStr.append(" (");
					if (autor.getAnioNacimiento() != null) {
						autorStr.append(autor.getAnioNacimiento());
					}
					autorStr.append(" - ");
					if (autor.getAnioFallecimiento() != null) {
						autorStr.append(autor.getAnioFallecimiento());
					} else {
						autorStr.append("presente");
					}
					autorStr.append(")");
				}
				System.out.println(autorStr);
			});
		}

		if (libro.getIdiomas() != null && !libro.getIdiomas().isEmpty()) {
			System.out.println("Idiomas disponibles: " + String.join(", ", libro.getIdiomas()));
		}

		if (libro.getTematicas() != null && !libro.getTematicas().isEmpty()) {
			System.out.println("Temáticas: " + String.join(", ", libro.getTematicas()));
		}
	}

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
}
