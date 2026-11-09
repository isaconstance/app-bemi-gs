package br.com.isaconstance.bemi.ui.screens.avisos

data class AvisoItem(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val detalhes: String,
    val tipo: TipoAviso,
    val lido: Boolean = false
)

enum class TipoAviso{
    CONSULTA,
    DOCUMENTO,
    SEGURANCA
}

sealed interface AvisosUiState {
    data object Carregando : AvisosUiState

    data class Sucesso(
        val avisos: List<AvisoItem>
    ) : AvisosUiState

    data object Vazio : AvisosUiState

    data class Erro(val mensagem: String) : AvisosUiState
}