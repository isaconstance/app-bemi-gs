package br.com.isaconstance.bemi.ui.screens.avisos

import br.com.isaconstance.bemi.domain.model.Aviso

sealed interface AvisosUiState {
    data object Carregando : AvisosUiState

    data class Sucesso(
        val avisos: List<Aviso>
    ) : AvisosUiState

    data object Vazio : AvisosUiState

    data class Erro(val mensagem: String) : AvisosUiState
}