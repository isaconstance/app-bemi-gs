package br.com.isaconstance.bemi.ui.screens.documentos

import br.com.isaconstance.bemi.domain.model.Documento

sealed interface DocumentoUiState {

    data object Inicial : DocumentoUiState

    data object Carregando : DocumentoUiState

    data class Sucesso(
        val documentos: List<Documento>
    ) : DocumentoUiState

    data object Vazio : DocumentoUiState

    data class Erro(
        val mensagem: String
    ) : DocumentoUiState
}