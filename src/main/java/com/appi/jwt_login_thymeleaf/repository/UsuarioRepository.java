package com.appi.jwt_login_thymeleaf.repository;

import com.appi.jwt_login_thymeleaf.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByResetToken(String resetToken);
}
