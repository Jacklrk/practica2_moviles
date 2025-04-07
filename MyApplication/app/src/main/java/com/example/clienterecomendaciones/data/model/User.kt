package com.example.clienterecomendaciones.data.model

data class User(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val fotoPerfil: String? = null // URL o base64
)
