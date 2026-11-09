package br.com.isaconstance.bemi.data.remote

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsultaApi {

    @GET("b/{binId}/latest")
    suspend fun buscarConsultas(
        @Path("binId") binId: String
    ): Response<JsonObject>
}