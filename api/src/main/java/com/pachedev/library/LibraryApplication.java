package com.pachedev.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Library API application.
 *
 * Boots the Spring Boot context and starts the embedded server.
 *
 * @author Daniel Pacheco
 */
@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
}
