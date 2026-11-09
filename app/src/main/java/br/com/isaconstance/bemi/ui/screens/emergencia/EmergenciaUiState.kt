package br.com.isaconstance.bemi.ui.screens.emergencia

import br.com.isaconstance.bemi.domain.model.Contato

data class EmergenciaUiState(
    val contatos: List<Contato> = emptyList(),
    val carregando: Boolean = true,
    val erro: String? = null
)