package br.com.isaconstance.bemi.data.repository

import br.com.isaconstance.bemi.data.remote.RetrofitClient
import br.com.isaconstance.bemi.domain.model.Documento
import com.google.gson.Gson

class DocumentoRepository {

    private val api = RetrofitClient.documentoApi
    private val gson = Gson()

    suspend fun buscarDocumentos(): List<Documento> {
        val resposta = api.buscarDocumentos(BIN_ID)

        if (!resposta.isSuccessful) {
            throw Exception("Erro na API: ${resposta.code()}")
        }

        val corpo = resposta.body()
            ?: throw Exception("Resposta vazia da API")

        val registro = corpo.getAsJsonObject("record")
            ?: throw Exception("Registro de documentos não encontrado")

        val lista = registro.getAsJsonArray("documentos")
            ?: throw Exception("Lista de documentos não encontrada")

        return gson.fromJson(
            lista,
            Array<Documento>::class.java
        ).toList()
    }

    companion object {
        private const val BIN_ID = "6aee975cac6210605a7441e5"
    }
}