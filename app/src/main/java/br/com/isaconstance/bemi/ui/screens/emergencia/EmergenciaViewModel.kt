package br.com.isaconstance.bemi.ui.screens.emergencia

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.isaconstance.bemi.data.local.database.AppDatabase
import br.com.isaconstance.bemi.data.repository.ContatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmergenciaViewModel(context: Context) : ViewModel() {

    private val database = AppDatabase.getInstance(context)
    private val repository = ContatoRepository(database.contatoDao())

    private val _uiState = MutableStateFlow(EmergenciaUiState())
    val uiState: StateFlow<EmergenciaUiState> = _uiState.asStateFlow()

    init {
        observarContatos()
    }

    private fun observarContatos() {
        viewModelScope.launch {
            try {
                repository.listarContatos().collect { contatos ->
                    _uiState.value = EmergenciaUiState(
                        contatos = contatos,
                        carregando = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = EmergenciaUiState(
                    carregando = false,
                    erro = "Não foi possível carregar os contatos."
                )
            }
        }
    }
}