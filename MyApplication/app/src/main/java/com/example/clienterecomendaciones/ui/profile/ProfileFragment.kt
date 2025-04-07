package com.example.clienterecomendaciones.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.clienterecomendaciones.data.model.User
import com.example.clienterecomendaciones.data.network.ApiClient
import com.example.clienterecomendaciones.databinding.FragmentProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var token: String = ""
    private var userId: Int? = null // para conservar el ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireActivity().getSharedPreferences("app_prefs", 0)
        token = prefs.getString("token", "") ?: ""

        cargarPerfil()

        binding.btnGuardar.setOnClickListener {
            val nuevoNombre = binding.etNombre.text.toString()
            val nuevoCorreo = binding.etCorreo.text.toString()
            val nuevaFoto = binding.etFotoUrl.text.toString()

            val usuarioActualizado = User(
                id = userId,
                nombre = nuevoNombre,
                correo = nuevoCorreo,
                fotoPerfil = if (nuevaFoto.isNotEmpty()) nuevaFoto else null
            )

            ApiClient.retrofit.updateProfile("Bearer $token", usuarioActualizado)
                .enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()
                            cargarPerfil()
                        } else {
                            Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun cargarPerfil() {
        ApiClient.retrofit.getProfile("Bearer $token")
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        user?.let {
                            userId = it.id
                            binding.etNombre.setText(it.nombre)
                            binding.etCorreo.setText(it.correo)
                            binding.etFotoUrl.setText(it.fotoPerfil ?: "")

                            if (!it.fotoPerfil.isNullOrEmpty()) {
                                Glide.with(requireContext())
                                    .load(it.fotoPerfil)
                                    .into(binding.ivFotoPerfil)
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
