package br.com.isaconstance.bemi.data.repository

import br.com.isaconstance.bemi.data.remote.RetrofitClient
import br.com.isaconstance.bemi.domain.model.Consulta
import com.google.gson.Gson
import com.google.gson.JsonObject

class ConsultaRepository {

    private val api = RetrofitClient.consultaApi
    private val gson = Gson()

    suspend fun buscarConsultas(): List<Consulta> {

        val resposta = api.buscarConsultas(BIN_ID)

        if (!resposta.isSuccessful) {
            throw Exception("Erro na API: ${resposta.code()}")
        }

        val corpo = resposta.body()
            ?: throw Exception("Resposta vazia da API")

        val registro = corpo.getAsJsonObject("record")
            ?: throw Exception("Registro de consultas não encontrado")

        val lista = registro.getAsJsonArray("consultas")
            ?: throw Exception("Lista de consultas não encontrada")

        return gson.fromJson(
            lista,
            Array<Consulta>::class.java
        ).toList()
    }

    companion object {
        private const val BIN_ID = "6aee96c9ac6210605a744115"
    }
}