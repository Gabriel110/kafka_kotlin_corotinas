package br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.impl

import br.com.kafka.ecsabreconta.core.gateway.output.client.DadosPessoalClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.DadosPessoasService
import br.com.kafka.ecsabreconta.shared.exception.DadosClienteException
import org.springframework.stereotype.Service
import retrofit2.Response

@Service
class DadosPessoasServiceImp(
    private val dadosPessoalClient: DadosPessoalClient
): DadosPessoasService {
    override fun getDados(): PessoaResponse? {
        val response = dadosPessoalClient.getPessoa().execute()
        return response.checkSuccess().body()
    }

    @Throws(DadosClienteException::class)
    private fun <T> Response<T>.checkSuccess(): Response<T> {
        if (!isSuccessful) {
            val errorBody = errorBody()?.string()
            throw DadosClienteException(code(), message(), errorBody)
        }
        return this
    }
}