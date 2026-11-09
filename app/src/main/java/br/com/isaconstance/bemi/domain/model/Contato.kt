package br.com.isaconstance.bemi.domain.model

data class Contato(
    val id: Int = 0,
    val nome: String,
    val telefone: String,
    val parentesco: String,
    val favorito: Boolean = false
)