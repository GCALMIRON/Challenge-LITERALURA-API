# Challenge-LITERALURA-API
Aplicación "Bookearte" para cumplir el challenge Literalura de la formación en backend de ALURA LATAM

# 📚 Bookearte - Sistema de Gestión Bibliotecaria

## 📋 Descripción
Bookearte es una aplicación de gestión bibliotecaria desarrollada en Java Spring que permite consultar y almacenar información de libros desde una API externa en una base de datos PostgreSQL. El sistema ofrece diversas funcionalidades para la gestión y consulta de libros y autores.

![pic1](https://github.com/user-attachments/assets/9a1118f7-a7b3-4135-8801-6757a940fb7f)


## 🚀 Características
- Búsqueda de libros en API externa
- Almacenamiento en base de datos PostgreSQL
- Consulta de libros registrados
- Consulta de autores registrados
- Filtros por año e idioma
- Ranking de libros más descargados

## 🛠️ Tecnologías Utilizadas
- Java Spring
- PostgreSQL
- API REST
- Maven

## 📁 Estructura del Proyecto
```
src/
├── main/
│   └── java/
│       └── com/bookearte/
│           ├── BookearteApplication.java
│           ├── model/
│           │   ├── Autor.java
│           │   ├── Libro.java
│           │   └── DatosLibro.java
│           ├── repository/
│           │   ├── AutorRepository.java
│           │   └── LibroRepository.java
│           └── service/
│               ├── AutorService.java
│               ├── ConsumoAPI.java
│               ├── ConvierteDatos.java
│               ├── LibroService.java
│               └── IConvierteDatos.java
```

## 📋 Requisitos Previos
- Java JDK 11 o superior
- PostgreSQL
- Maven
- IntelliJ IDEA

## ⚙️ Configuración
1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/bookearte.git
```

2. Configura la base de datos PostgreSQL en `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

## 🎯 Funcionalidades
El sistema cuenta con un menú de consola que ofrece las siguientes opciones:

1. 🔍 Buscar libro
2. 📚 Listar libros registrados
3. 👥 Listar autores registrados
4. 📅 Listar autores por año
5. 🌍 Listar libros por idioma
6. 🏆 Top 10 libros más descargados

## 💾 Estructura de la Base de Datos
```sql
CREATE TABLE libros (
    id BIGINT PRIMARY KEY,
    titulo VARCHAR(255),
    autor TEXT[],
    tematica TEXT[],
    idioma TEXT[]
);
```

## 🤝 Contribuir
Las contribuciones son bienvenidas. Para contribuir:

1. Haz un Fork del proyecto
2. Crea una nueva rama (`git checkout -b feature/nueva-caracteristica`)
3. Commit tus cambios (`git commit -m 'Añade nueva característica'`)
4. Push a la rama (`git push origin feature/nueva-caracteristica`)
5. Abre un Pull Request

## 📝 Licencia
Este proyecto está bajo la Licencia [MIT](https://choosealicense.com/licenses/mit/)

## ✉️ Contacto
Ger - gcalmiron43@gmail.com
Link del proyecto: https://github.com/GCALMIRON/Challenge-LITERALURA-API
