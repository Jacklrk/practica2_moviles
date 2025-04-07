package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Registro
    public boolean register(User user) {
        if (userRepository.existsByCorreo(user.getCorreo())) {
            return false; // Ya existe
        }
        // Encriptar contraseña
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        userRepository.save(user);
        return true;
    }

    // Login
    public Optional<User> authenticate(String correo, String contrasena) {
        Optional<User> userOpt = userRepository.findByCorreo(correo);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(contrasena, user.getContrasena())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    // Obtener perfil por correo (token más adelante)
    public Optional<User> findByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }
    public boolean update(User user) {
        Optional<User> userOpt = userRepository.findByCorreo(user.getCorreo());
        if (userOpt.isPresent()) {
            User existente = userOpt.get();
            existente.setNombre(user.getNombre());
            existente.setFotoPerfil(user.getFotoPerfil());
            // No actualizamos el rol ni contraseña por seguridad
            userRepository.save(existente);
            return true;
        }
        return false;
    }
    public boolean deleteByCorreo(String correo) {
        Optional<User> userOpt = userRepository.findByCorreo(correo);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }
    
    
}
