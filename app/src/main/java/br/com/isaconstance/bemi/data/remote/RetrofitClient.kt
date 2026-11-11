package br.com.isaconstance.bemi.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitClient {

    private const val BASE_URL = "https://api.jsonbin.io/v3/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->

            val request = chain.request()
                .newBuilder()
                .header(
                    "X-Access-Key",
                    ApiConfig.JSONBIN_ACCESS_KEY
                )
                .build()

            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val consultaApi: ConsultaApi =
        retrofit.create(ConsultaApi::class.java)

    val documentoApi: DocumentoApi =
        retrofit.create(DocumentoApi::class.java)
}