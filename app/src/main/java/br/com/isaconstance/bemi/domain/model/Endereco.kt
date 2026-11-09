package br.com.isaconstance.bemi.domain.model

data class Endereco(
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val cep: String
)