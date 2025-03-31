package com.appi.jwt_login_thymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired private JavaMailSender mailSender;

    public void enviarCorreoRecuperacion(String toEmail, String token) {
        String asunto = "Recuperación de contraseña";
        String mensaje = "Hola, haz clic en el siguiente enlace para restablecer tu contraseña:\n" +
                "http://localhost:8080/reset-password?token=" + token + "\n\n" +
                "Este enlace expirará dentro de 15 minutos.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(asunto);
        message.setText(mensaje);
        message.setFrom("lopezmrls.angel@gmail.com");

        mailSender.send(message);
    }
}
