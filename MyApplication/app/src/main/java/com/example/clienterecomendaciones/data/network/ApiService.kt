// Archivo: ApiService.kt
package com.example.clienterecomendaciones.data.network

import retrofit2.Call
import retrofit2.http.*
import com.example.clienterecomendaciones.data.model.LoginRequest
import com.example.clienterecomendaciones.data.model.LoginResponse
import com.example.clienterecomendaciones.data.model.RegisterRequest
import com.example.clienterecomendaciones.data.model.User



// Esta es la interfaz directa, sin necesidad de una clase envolvente
interface ApiService {

    @POST("api/auth/login")
    fun login(@Body credentials: LoginRequest): Call<LoginResponse>

    @POST("api/register")
    fun register(@Body request: RegisterRequest): Call<Void>

    @GET("api/perfil")
    fun getProfile(@Header("Authorization") token: String): Call<User>

    @PUT("api/perfil")
    fun updateProfile(@Header("Authorization") token: String, @Body user: User): Call<User>

    @GET("api/usuarios")
    fun getAllUsers(@Header("Authorization") token: String): Call<List<User>>

    @DELETE("api/usuarios/{id}")
    fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Void>

    // Puedes agregar aquí los endpoints para libros, usuarios, etc.
}
