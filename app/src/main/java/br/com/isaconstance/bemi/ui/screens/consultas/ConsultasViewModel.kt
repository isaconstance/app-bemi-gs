package br.com.isaconstance.bemi.ui.screens.consultas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.isaconstance.bemi.data.repository.ConsultaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConsultasViewModel : ViewModel() {

    private val repository = ConsultaRepository()

    private val _uiState =
        MutableStateFlow<ConsultaUiState>(ConsultaUiState.Inicial)

    val uiState: StateFlow<ConsultaUiState> =
        _uiState.asStateFlow()

    init {
        carregarConsultas()
    }

    fun carregarConsultas() {

        viewModelScope.launch {

            _uiState.value = ConsultaUiState.Carregando

            try {
                val consultas = repository.buscarConsultas()

                _uiState.value = if (consultas.isEmpty()) {
                    ConsultaUiState.Vazio
                } else {
                    ConsultaUiState.Sucesso(consultas)
                }

            } catch (e: Exception) {
                _uiState.value = ConsultaUiState.Erro(
                    e.message ?: "Não foi possível carregar as consultas."
                )
            }
        }
    }
}