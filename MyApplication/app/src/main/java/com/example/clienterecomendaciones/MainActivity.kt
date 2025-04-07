package com.example.clienterecomendaciones

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clienterecomendaciones.databinding.ActivityMainBinding
import com.example.clienterecomendaciones.ui.auth.LoginActivity
import com.example.clienterecomendaciones.ui.profile.ProfileFragment
import com.example.clienterecomendaciones.ui.admin.AdminFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var role: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener rol del usuario desde SharedPreferences
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        role = prefs.getString("role", "") ?: ""

        // Ocultar la opción de Admin si no es ADMIN
        if (role != "ADMIN") {
            binding.bottomNav.menu.findItem(R.id.nav_crud).isVisible = false
        }

        // Cargar pantalla inicial
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfileFragment())
            .commit()

        // Navegación del BottomNav
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_perfil -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment())
                        .commit()
                    true
                }
                R.id.nav_crud -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AdminFragment())
                        .commit()
                    true
                }
                R.id.nav_logout -> {
                    prefs.edit().clear().apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
