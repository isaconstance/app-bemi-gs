package br.com.isaconstance.bemi.ui.screens.consultas

import br.com.isaconstance.bemi.domain.model.Consulta

sealed interface ConsultaUiState {

    data object Inicial : ConsultaUiState

    data object Carregando : ConsultaUiState

    data class Sucesso(
        val consultas: List<Consulta>
    ) : ConsultaUiState

    data object Vazio : ConsultaUiState

    data class Erro(
        val mensagem: String
    ) : ConsultaUiState
}