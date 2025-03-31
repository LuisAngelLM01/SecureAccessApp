package com.appi.jwt_login_thymeleaf.service;

import com.appi.jwt_login_thymeleaf.model.Usuario;
import com.appi.jwt_login_thymeleaf.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;

    public void iniciarRecuperacion(String correo) {
        Optional<Usuario> opt = usuarioRepository.findByCorreo(correo);
        if (opt.isEmpty()) return; // No revelar existencia del correo

        Usuario usuario = opt.get();
        String token = UUID.randomUUID().toString();
        usuario.setResetToken(hashToken(token));
        usuario.setTokenExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
        usuarioRepository.save(usuario);

        emailService.enviarCorreoRecuperacion(usuario.getCorreo(), token);
    }

    public boolean resetearPassword(String token, String nuevoPassword) {
        String hashed = hashToken(token);
        Optional<Usuario> opt = usuarioRepository.findByResetToken(hashed)
                .filter(u -> u.getTokenExpiration().after(new Date()));

        if (opt.isEmpty()) return false;

        Usuario usuario = opt.get();
        usuario.setPassword(passwordEncoder.encode(nuevoPassword));
        usuario.setResetToken(null);
        usuario.setTokenExpiration(null);
        usuarioRepository.save(usuario);
        return true;
    }

    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear el token", e);
        }
    }
}
