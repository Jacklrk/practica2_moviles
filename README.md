# 🔐 Sistema de Autenticación Básico: Android Studio + Spring Boot + MySQL

Este proyecto implementa un sistema de login y registro de usuarios usando una aplicación móvil en **Android Studio** (Kotlin) que se comunica con un backend en **Spring Boot**, utilizando **MySQL** como base de datos.

## 📱 Aplicación Android

### Tecnologías utilizadas:
- Kotlin
- Retrofit (para consumo del backend REST)
- Glide (para carga de fotos de perfil)
- SharedPreferences (para guardar token y rol de sesión)

### Funcionalidades:
- Pantalla de inicio de sesión con validación.
- Registro de nuevos usuarios.
- Acceso a perfil con actualización y visualización de foto.
- Visualización de menú dinámico según el rol (`ADMIN` o `USER`).
- Navegación con `BottomNavigationView`.

## ☁️ Backend Spring Boot

### Tecnologías utilizadas:
- Spring Web
- Spring Security (opcional, con JWT)
- Spring Data JPA
- MySQL
- JJWT (para token JWT)

### Endpoints disponibles:

| Método | Endpoint             | Descripción                          |
|--------|----------------------|--------------------------------------|
| POST   | `/api/register`      | Registro de usuario nuevo            |
| POST   | `/api/auth/login`    | Autenticación y generación de token  |
| GET    | `/api/perfil`        | Consulta del perfil por correo       |
| PUT    | `/api/perfil`        | Actualización de perfil              |
| DELETE | `/api/usuario/{correo}` | Eliminación de usuario            |

### Estructura del proyecto:

- `model/User.java`: entidad de usuario.
- `repository/UserRepository.java`: operaciones JPA.
- `service/UserService.java`: lógica de autenticación, registro y CRUD.
- `controller/AuthController.java`: endpoints REST.
- `security/`: configuración de seguridad (opcional JWT).

### Configuración de MySQL

En tu `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mi_app
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
