## 📌 Descripción del Proyecto

**JWT Login Thymeleaf** es una aplicación web desarrollada con **Spring Boot**, que implementa un sistema completo de autenticación y gestión de usuarios utilizando **JWT (JSON Web Tokens)** para asegurar las sesiones. La interfaz está construida con **Thymeleaf** y CSS personalizado, brindando una experiencia moderna e intuitiva.

### 🔐 Características principales:

- **Login seguro con JWT**
- **Registro de nuevos usuarios**
- **Validación de duplicados (usuario y correo)**
- **Cifrado de contraseñas con BCrypt**
- **Recuperación de contraseña por correo electrónico**
- **Restablecimiento de contraseña con token seguro (hash + expiración)**
- **Roles de usuario (USER / ADMIN)**
- **Protección de rutas según roles**
- **Interfaz responsive y visualmente atractiva con animaciones**
- **Soporte para temas claro/oscuro y barra de progreso al iniciar sesión**
- **Integración de íconos (Boxicons) y alertas visuales con SweetAlert2**

### 🧪 Rutas y Funcionalidades:

| Ruta                | Descripción                                 | Protección |
|---------------------|---------------------------------------------|------------|
| `/login`            | Formulario de inicio de sesión              | Pública    |
| `/register`         | Formulario de registro de usuario           | Pública    |
| `/forgot-password`  | Recuperación de contraseña vía correo       | Pública    |
| `/reset-password`   | Restablecimiento de contraseña con token    | Pública    |
| `/` o `/home`       | Página principal con token JWT              | Protegida  |
| `/admin/panel`      | Panel exclusivo para administradores        | ADMIN      |
| `/user/perfil`      | Perfil del usuario logueado                 | USER/ADMIN |

### 🛠️ Tecnologías utilizadas:

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- Thymeleaf
- Oracle Database
- SweetAlert2
- Boxicons
- Bootstrap 5

---
