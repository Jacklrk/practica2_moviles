package com.example.clienterecomendaciones.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clienterecomendaciones.MainActivity
import com.example.clienterecomendaciones.R
import com.example.clienterecomendaciones.data.model.LoginRequest
import com.example.clienterecomendaciones.data.model.LoginResponse
import com.example.clienterecomendaciones.data.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnIrARegistro: Button

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etCorreo = findViewById(R.id.etCorreo)
        etContrasena = findViewById(R.id.etContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnIrARegistro = findViewById(R.id.btnIrARegistro)

        btnIniciarSesion.setOnClickListener {
            val correo = etCorreo.text.toString()
            val contrasena = etContrasena.text.toString()

            val request = LoginRequest(correo, contrasena)

            ApiClient.retrofit.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token ?: ""
                        val role = response.body()?.role ?: ""

                        // Guardar token y rol
                        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                        prefs.edit().putString("token", token).putString("role", role).apply()

                        Toast.makeText(this@LoginActivity, "Bienvenido", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        btnIrARegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
