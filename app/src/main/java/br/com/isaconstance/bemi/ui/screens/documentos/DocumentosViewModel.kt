package br.com.isaconstance.bemi.ui.screens.documentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.isaconstance.bemi.data.repository.DocumentoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DocumentosViewModel : ViewModel() {

    private val repository = DocumentoRepository()

    private val _uiState =
        MutableStateFlow<DocumentoUiState>(DocumentoUiState.Inicial)

    val uiState: StateFlow<DocumentoUiState> =
        _uiState.asStateFlow()

    init {
        carregarDocumentos()
    }

    fun carregarDocumentos() {
        viewModelScope.launch {
            _uiState.value = DocumentoUiState.Carregando

            try {
                val documentos = repository.buscarDocumentos()

                _uiState.value = if (documentos.isEmpty()) {
                    DocumentoUiState.Vazio
                } else {
                    DocumentoUiState.Sucesso(documentos)
                }

            } catch (e: Exception) {
                _uiState.value = DocumentoUiState.Erro(
                    e.message ?: "Não foi possível carregar os documentos."
                )
            }
        }
    }
}