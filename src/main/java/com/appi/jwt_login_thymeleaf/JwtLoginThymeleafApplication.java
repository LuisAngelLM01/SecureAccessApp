package com.appi.jwt_login_thymeleaf;

import com.appi.jwt_login_thymeleaf.model.Usuario;
import com.appi.jwt_login_thymeleaf.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JwtLoginThymeleafApplication {
	@Bean
	CommandLineRunner init(UsuarioRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByUsername("admin").isEmpty()) {
				repo.save(new Usuario(null, "admin", encoder.encode("admin123"),
						"ADMIN", "tuCorreo@gmail.com"));
				System.out.println("Usuario admin creado correctamente.");
			} else {
				System.out.println("El usuario admin ya existe.");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtLoginThymeleafApplication.class, args);
	}
	//http://localhost:8080/login
}
