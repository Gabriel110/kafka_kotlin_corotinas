package br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.impl

import br.com.kafka.ecsabreconta.core.gateway.output.client.DadosPessoalClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.DadosPessoasService
import br.com.kafka.ecsabreconta.shared.exception.DadosClienteException
import org.springframework.stereotype.Service

@Service
class DadosPessoasServiceImp(
    private val dadosPessoalClient: DadosPessoalClient
): DadosPessoasService {
    override fun getDados(): PessoaResponse? {
        val response = dadosPessoalClient.getPessoa().execute()
        if (response.isSuccessful)
            return response.body()
        else
            throw DadosClienteException(statusCode = response.code(), message = response.message())
    }
}