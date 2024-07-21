package br.com.kafka.ecsabreconta.gateway.input.kafka

import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.DadosPessoasService
import br.com.kafka.ecsabreconta.shared.exception.DadosClienteException
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.kafka.support.Acknowledgment


class KafkaMessageListenerTest{

    private val dadosPessoasService: DadosPessoasService = mock(DadosPessoasService::class.java)
    private val acknowledgment: Acknowledgment = mock(Acknowledgment::class.java)
    private val kafkaMessageListener = KafkaMessageListener(dadosPessoasService)

    @Test
    fun `test successful message processing`() {

        `when`(dadosPessoasService.getDados()).thenReturn(PessoaResponse(
            nome = "gabriel",
            cpf = "91802388664",
            dataNascimento = "06-12-1996"
        ))
        kafkaMessageListener.listen("Test Message", acknowledgment)
        verify(acknowledgment).acknowledge()
        verify(dadosPessoasService).getDados()
    }

    @Test
    fun `test exception handling in message processing`() {
        `when`(dadosPessoasService.getDados()).thenThrow(
            DadosClienteException(
                message = "Forbidden",
                statusCode = 403
            )
        )
        kafkaMessageListener.listen("Test Message", acknowledgment)
        verify(acknowledgment, never()).acknowledge()
        verify(dadosPessoasService).getDados()
    }
}