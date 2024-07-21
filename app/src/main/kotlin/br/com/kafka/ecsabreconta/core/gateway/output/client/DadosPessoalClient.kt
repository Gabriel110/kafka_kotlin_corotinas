package br.com.kafka.ecsabreconta.core.gateway.output.client

import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import retrofit2.Call
import retrofit2.http.GET

interface DadosPessoalClient {

    @GET("/pessoa")
    fun getPessoa(): Call<PessoaResponse>
}