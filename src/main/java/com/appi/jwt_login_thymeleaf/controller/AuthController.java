package com.appi.jwt_login_thymeleaf.controller;

import com.appi.jwt_login_thymeleaf.dto.LoginRequest;
import com.appi.jwt_login_thymeleaf.dto.RegisterRequest;
import com.appi.jwt_login_thymeleaf.dto.ResetPasswordRequest;
import com.appi.jwt_login_thymeleaf.model.Usuario;
import com.appi.jwt_login_thymeleaf.repository.UsuarioRepository;
import com.appi.jwt_login_thymeleaf.security.JwtUtil;
import com.appi.jwt_login_thymeleaf.service.EmailService;
import com.appi.jwt_login_thymeleaf.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EmailService emailService;
    @Autowired private PasswordResetService passwordResetService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        cargarFormulario(model, new LoginRequest(), new RegisterRequest(), false, null, null);
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute LoginRequest loginRequest, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            model.addAttribute("token", jwt);
            return "home";
        } catch (BadCredentialsException e) {
            cargarFormulario(model, new LoginRequest(), new RegisterRequest(), false, null, "Usuario o contraseña incorrecto");
            return "login";
        }
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest registerRequest, Model model) {
        // Validación básica
        if (registerRequest.getUsername().isBlank() ||
                registerRequest.getPassword().isBlank() ||
                registerRequest.getCorreo().isBlank() ||
                registerRequest.getConfirmPassword().isBlank()) {
            cargarFormulario(model, new LoginRequest(), registerRequest, true, null, "Todos los campos son obligatorios");
            return "login";
        }
        // Confirmar contraseña
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            cargarFormulario(model, new LoginRequest(), registerRequest, true, null, "Las contraseñas no coinciden");
            return "login";
        }
        // Validar existencia
        if (usuarioRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            cargarFormulario(model, new LoginRequest(), registerRequest, true, null, "El nombre del usuario ya existe");
            return "login";
        }
        if (usuarioRepository.findByCorreo(registerRequest.getCorreo()).isPresent()) {
            cargarFormulario(model, new LoginRequest(), registerRequest, true, null, "El correo ya está registrado");
            return "login";
        }
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(registerRequest.getUsername());
        nuevoUsuario.setCorreo(registerRequest.getCorreo());
        nuevoUsuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        nuevoUsuario.setRol("USER");

        usuarioRepository.save(nuevoUsuario);

        cargarFormulario(model, new LoginRequest(), new RegisterRequest(), true, "Usuario registrado exitosamente", null);
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        cargarFormulario(model, new LoginRequest(), new RegisterRequest(), true, null, null);
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("correo", "");
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@ModelAttribute("correo") String correo, Model model) {
        // Validar formato básico de correo
        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            model.addAttribute("error", "Correo inválido");
            return "forgot-password";
        }

        passwordResetService.iniciarRecuperacion(correo);
        model.addAttribute("mensaje", "Se ha enviado un enlace de recuperación a tu cuenta de correo.");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam(value = "token", required = false) String token, Model model) {
        if (token == null || token.isBlank()) {
            model.addAttribute("error", "Falta el token para restablecer la contraseña.");
            return "forgot-password";
        }

        String hashed = passwordResetService.hashToken(token);
        Optional<Usuario> usuario = usuarioRepository.findByResetToken(hashed)
                .filter(u -> u.getTokenExpiration().after(new Date()));

        if (usuario.isPresent()) {
            ResetPasswordRequest reset = new ResetPasswordRequest();
            reset.setToken(token);
            model.addAttribute("resetPasswordRequest", reset);
            return "reset-password";
        } else {
            model.addAttribute("error", "El token es inválido o ha expirado.");
            return "forgot-password";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPasswordRequest reset, Model model) {
        if (!reset.getNuevoPassword().equals(reset.getConfirmarPassword())) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "reset-password";
        }

        boolean exito = passwordResetService.resetearPassword(reset.getToken(), reset.getNuevoPassword());
        if (!exito) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "reset-password";
        }

        cargarFormulario(model, new LoginRequest(), new RegisterRequest(), false, "Contraseña actualizada correctamente", null);
        return "login";
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    // Metodo reutilizable
    private void cargarFormulario(Model model, LoginRequest login, RegisterRequest register,
                                  boolean showRegister, String successMsg, String errorMsg) {
        model.addAttribute("loginRequest", login);
        model.addAttribute("registerRequest", register);
        model.addAttribute("showRegisterForm", showRegister);
        model.addAttribute("registerSuccess", successMsg);
        model.addAttribute("registerError", errorMsg);
    }
}
