package br.com.kafka.ecsabreconta.core.gateway.output.client

import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DadosPessoalClient {

    @GET("/pessoa")
    @Headers("Content-Type: application/json ")
    fun getPessoa(
        @Query("nome") name: String = "gabriel"
    ): Call<PessoaResponse>
}