package com.example.clienterecomendaciones.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clienterecomendaciones.data.model.User
import com.example.clienterecomendaciones.data.network.ApiClient
import com.example.clienterecomendaciones.databinding.FragmentAdminBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UserAdapter
    private var token: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token = requireActivity().getSharedPreferences("app_prefs", 0)
            .getString("token", "") ?: ""

        adapter = UserAdapter(emptyList()) { user ->
            eliminarUsuario(user)
        }

        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUsuarios.adapter = adapter

        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        ApiClient.retrofit.getAllUsers("Bearer $token").enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    adapter.actualizarLista(response.body() ?: emptyList())
                } else {
                    Toast.makeText(requireContext(), "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarUsuario(user: User) {
        user.id?.let {
            ApiClient.retrofit.deleteUser("Bearer $token", it).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Usuario eliminado", Toast.LENGTH_SHORT).show()
                        cargarUsuarios()
                    } else {
                        Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
