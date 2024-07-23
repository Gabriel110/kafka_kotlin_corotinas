package br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.impl

import br.com.kafka.ecsabreconta.core.gateway.output.client.DadosPessoalClient
import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import br.com.kafka.ecsabreconta.shared.exception.DadosClienteException
import okhttp3.ResponseBody
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DadosPessoasServiceImpTest {

    private val dadosPessoalClient: DadosPessoalClient = mock(DadosPessoalClient::class.java)
    private val call: Call<PessoaResponse> = mock(Call::class.java) as Call<PessoaResponse>
    private val dadosPessoasServiceImp = DadosPessoasServiceImp(dadosPessoalClient)

    @Test
    fun `test successful data retrieval`() {
        val pessoaResponse = PessoaResponse(
            nome = "gabriel",
            cpf = "91802388664",
            dataNascimento = "06-12-1996"
        )
        val response = Response.success(pessoaResponse)
        `when`(call.execute()).thenReturn(response)
        `when`(dadosPessoalClient.getPessoa()).thenReturn(call)
        val result = dadosPessoasServiceImp.getDados()
        assertEquals(pessoaResponse, result, "Expected PessoaResponse did not match")
    }

    @Test
    fun `test data retrieval with failure response`() {
        val response = Response.error<PessoaResponse>(500, mock(ResponseBody::class.java))
        `when`(call.execute()).thenReturn(response)
        `when`(dadosPessoalClient.getPessoa()).thenReturn(call)

        val exception = assertFailsWith<DadosClienteException> {
            dadosPessoasServiceImp.getDados()
        }

        assertEquals(exception.message.toString(), "API Error: StatusCode: 500, Error: Response.error(), Body: null")
    }
}
