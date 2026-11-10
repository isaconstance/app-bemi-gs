package br.com.isaconstance.bemi.ui.screens.emergencia

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.isaconstance.bemi.data.local.database.AppDatabase
import br.com.isaconstance.bemi.data.repository.ContatoRepository
import br.com.isaconstance.bemi.domain.model.Contato
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EmergenciaViewModel(context: Context) : ViewModel() {

    private val database = AppDatabase.getInstance(context)
    private val repository = ContatoRepository(database.contatoDao())

    private val _uiState = MutableStateFlow(EmergenciaUiState())
    val uiState: StateFlow<EmergenciaUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            inserirContatos()
            observarContatos()
        }
    }

    private suspend fun inserirContatos() {
        val contatos = repository.listarContatos().first()

        if (contatos.isEmpty()) {
            repository.cadastrarContato(
                Contato(
                    nome = "Ana",
                    telefone = "(11) 00000-0000",
                    parentesco = "Filha",
                    favorito = true
                )
            )

            repository.cadastrarContato(
                Contato(
                    nome = "Carlos",
                    telefone = "(11) 00000-0001",
                    parentesco = "Filho"
                )
            )
        }
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