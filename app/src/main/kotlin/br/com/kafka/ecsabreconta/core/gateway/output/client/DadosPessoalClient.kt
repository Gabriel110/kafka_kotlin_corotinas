package br.com.kafka.ecsabreconta.core.gateway.output.client

import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface DadosPessoalClient {

    @GET("/pessoa")
    @Headers("Content-Type: application/json ")
    fun getPessoa(): Call<PessoaResponse>
}