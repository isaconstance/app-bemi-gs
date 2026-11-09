package br.com.isaconstance.bemi.domain.model

data class Documento(
    val id: Int,
    val tipo: String,
    val validade: String?,
    val status: String,
    val observacao: String?
)