package com.appi.jwt_login_thymeleaf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/panel")
    public String adminPanel() {
        return "Bienvenido al panel de administración";
    }
}
