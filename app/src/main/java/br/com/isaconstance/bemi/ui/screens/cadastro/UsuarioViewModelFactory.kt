package br.com.isaconstance.bemi.ui.screens.cadastro

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.isaconstance.bemi.data.local.database.AppDatabase
import br.com.isaconstance.bemi.data.repository.UsuarioRepository

class UsuarioViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(UsuarioViewModel::class.java)) {
            val database = AppDatabase.getInstance(context)
            val repository = UsuarioRepository(database.usuarioDao())

            @Suppress("UNCHECKED_CAST")
            return UsuarioViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconhecido")
    }
}
