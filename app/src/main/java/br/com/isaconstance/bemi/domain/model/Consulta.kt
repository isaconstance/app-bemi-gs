package br.com.isaconstance.bemi.domain.model

data class Consulta(
    val id: Int,
    val especialidade: String,
    val medico: String,
    val local: String,
    val endereco: Endereco,
    val data: String,
    val hora: String,
    val observacao: String?,
    val status: String
)