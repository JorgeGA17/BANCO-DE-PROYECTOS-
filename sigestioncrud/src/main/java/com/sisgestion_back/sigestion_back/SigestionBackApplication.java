package com.sisgestion_back.sigestion_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SigestionBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigestionBackApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// Permite cualquier origen (frontend) que haga peticiones
				registry.addMapping("/**") // Aplica a todas las rutas de la API
						.allowedOrigins("http://localhost:4200") // El dominio de tu frontend
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Los métodos permitidos
						.allowedHeaders("*") // Permite todas las cabeceras
						.allowCredentials(true);
			}
		};
	}
}
