package com.example.clienterecomendaciones.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clienterecomendaciones.data.model.User
import com.example.clienterecomendaciones.databinding.ItemUserBinding

class UserAdapter(
    private var usuarios: List<User>,
    private val onDeleteClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvNombre.text = user.nombre
            binding.tvCorreo.text = user.correo
            binding.btnEliminar.setOnClickListener {
                onDeleteClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = usuarios.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(usuarios[position])
    }

    fun actualizarLista(nuevaLista: List<User>) {
        usuarios = nuevaLista
        notifyDataSetChanged()
    }
}
