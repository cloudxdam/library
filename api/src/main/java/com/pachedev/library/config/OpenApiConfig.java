package com.pachedev.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI().info(new Info().title("Library API").version("1.0")
                .description("REST API for managing books, users and loans"));
    }
}
