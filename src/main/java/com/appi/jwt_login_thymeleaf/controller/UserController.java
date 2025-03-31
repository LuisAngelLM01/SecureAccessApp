package com.appi.jwt_login_thymeleaf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/perfil")
    public String userProfile() {
        return "Este es tu perfil de usuario";
    }
}
