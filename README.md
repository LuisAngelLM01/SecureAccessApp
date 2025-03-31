### Descripción del Proyecto  
**JWT Login Thymeleaf** es una aplicación web desarrollada con **Spring Boot**, que implementa un sistema completo de autenticación y gestión de usuarios utilizando **JWT (JSON Web Tokens)** para asegurar las sesiones. La interfaz está construida con **Thymeleaf y CSS personalizado**, brindando una experiencia moderna e intuitiva.

---

🔐 **Características principales**:
- Login seguro con JWT  
- Registro de nuevos usuarios  
- Validación de duplicados (usuario y correo)  
- Cifrado de contraseñas con BCrypt  
- Recuperación de contraseña por correo electrónico  
- Restablecimiento de contraseña con token seguro (hash + expiración)  
- Roles de usuario (USER / ADMIN)  
- Protección de rutas según los roles  
- Interfaz responsive y visualmente atractiva con animaciones  
- Soporte para temas claro/oscuro y barra de progreso al iniciar sesión  
- Integración de íconos (Boxicons) y alertas visuales con SweetAlert2  

---

🧪 **Rutas y Funcionalidades**:

| Ruta               | Descripción                                | Protección   |
|--------------------|--------------------------------------------|--------------|
| `/login`           | Formulario de inicio de sesión             | Pública      |
| `/register`        | Formulario de registro de usuario          | Pública      |
| `/forgot-password` | Recuperación de contraseña vía correo      | Pública      |
| `/reset-password`  | Restablecimiento de contraseña con token   | Pública      |
| `/` o `/home`      | Página principal con token JWT             | Protegida    |
| `/admin/panel`     | Panel exclusivo para administradores       | ADMIN        |
| `/user/perfil`     | Perfil del usuario logueado                | USER/ADMIN   |

---

🛠️ **Tecnologías utilizadas**:
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

🔑 **Cómo crear una contraseña de aplicación en Gmail** (para enviar correos desde la aplicación):

1. Activa la verificación en dos pasos:
   - Ve a [https://myaccount.google.com/security](https://myaccount.google.com/security)
   - En la sección **"Iniciar sesión en Google"**, haz clic en **"Verificación en dos pasos"** y actívala.

2. Genera una contraseña de aplicación:
   - Ve a [https://myaccount.google.com/apppasswords](https://myaccount.google.com/apppasswords)
   - Inicia sesión si se solicita.
   - En **"Seleccionar aplicación"**, elige una o selecciona **"Otra (nombre personalizado)"**, por ejemplo: `jwt-login-thymeleaf`.
   - Haz clic en **"Generar"**.
   - Se mostrará una contraseña de 16 caracteres (ej. `abcd efgh ijkl mnop`).
   - Usa esta contraseña generada sin espacios en el archivo application.properties en lugar de tu contraseña real de Gmail para el envío de correos.

> **Nota:** Si pierdes esta contraseña, deberás generar una nueva desde el mismo enlace.

---
