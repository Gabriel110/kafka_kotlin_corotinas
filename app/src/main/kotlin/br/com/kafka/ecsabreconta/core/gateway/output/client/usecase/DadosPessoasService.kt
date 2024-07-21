package br.com.kafka.ecsabreconta.core.gateway.output.client.usecase

import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse

interface DadosPessoasService {

    fun getDados(): PessoaResponse?
}