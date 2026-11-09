package br.com.isaconstance.bemi.domain.model

data class Aviso(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val detalhes: String,
    val tipo: TipoAviso,
    val lido: Boolean = false
)

enum class TipoAviso {
    CONSULTA,
    DOCUMENTO,
    SEGURANCA
}