package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Permitir acceso desde la app Android
public class AuthController {

    @Autowired
    private UserService userService;

    // Endpoint para registro
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        boolean creado = userService.register(user);
        if (creado) {
            return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado exitosamente"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo ya está en uso"));
        }
    }

    // Endpoint para login
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String correo = credentials.get("correo");
        String contrasena = credentials.get("contrasena");

        Optional<User> userOpt = userService.authenticate(correo, contrasena);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Aquí más adelante irá el token JWT
            return ResponseEntity.ok(Map.of(
                    "token", "TOKEN_DE_EJEMPLO", // reemplazaremos con JWT real luego
                    "role", user.getRol(),
                    "correo", user.getCorreo()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
        }
    }

    // Endpoint para obtener perfil (protegido más adelante)
    @GetMapping("/perfil")
    public ResponseEntity<?> perfil(@RequestParam String correo) {
        Optional<User> userOpt = userService.findByCorreo(correo);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return ResponseEntity.ok(Map.of(
                    "nombre", user.getNombre(),
                    "correo", user.getCorreo(),
                    "rol", user.getRol(),
                    "fotoPerfil", user.getFotoPerfil()
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(@RequestBody User user) {
        return userService.update(user) ?
            ResponseEntity.ok("Perfil actualizado") :
            ResponseEntity.badRequest().body("No se pudo actualizar");
    }
    @DeleteMapping("/usuario/{correo}")
    public ResponseEntity<?> eliminar(@PathVariable String correo) {
        return userService.deleteByCorreo(correo) ?
            ResponseEntity.ok("Usuario eliminado") :
            ResponseEntity.notFound().build();
    }


}
