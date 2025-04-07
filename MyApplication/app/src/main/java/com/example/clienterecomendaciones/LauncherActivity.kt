package com.example.clienterecomendaciones

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Guardar datos de sesión "ficticia"
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        prefs.edit()
            .putString("token", "FAKE_ADMIN_TOKEN") // Token ficticio
            .putString("role", "ADMIN")              // Rol de admin
            .putString("correo", "admin@demo.com")   // Correo de prueba si lo usas
            .apply()

        // Ir directamente a MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
