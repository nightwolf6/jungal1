package com.example.demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
@EntityScan("com.example.demo.Application.Model")
@EnableJpaRepositories("com.example.demo.Application.Repository")
public class DemoApplication {

	public static void main(String[] args) {
		// Cargar variables desde .env
		Dotenv dotenv = Dotenv.configure()
				.directory("/src/main/resources")
				.load();
		System.setProperty("DB_HOST", System.getenv("DB_HOST"));
		System.setProperty("DB_PORT", System.getenv("DB_PORT"));
		System.setProperty("DB_NAME", System.getenv("DB_NAME"));
		System.setProperty("DB_USER", System.getenv("DB_USER"));
		System.setProperty("DB_PASSWORD", System.getenv("DB_PASSWORD"));
		System.setProperty("SERVER_PORT", System.getenv("SERVER_PORT"));
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600);
			}

			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/images/**")
						.addResourceLocations("file:C:/PROYECTO FINAL/images/");
			}
		};
	}

}
