package br.com.kafka.ecsabreconta.gateway.input.kafka

import br.com.kafka.ecsabreconta.core.gateway.output.client.response.PessoaResponse
import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.DadosPessoasService
import br.com.kafka.ecsabreconta.core.usecase.impl.DynamicCacheServiceImp
import br.com.kafka.ecsabreconta.shared.exception.AuthClienteException
import br.com.kafka.ecsabreconta.shared.exception.DadosClienteException
import kotlinx.coroutines.test.runTest
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.kafka.support.Acknowledgment


class KafkaMessageListenerTest{

    @Mock
    private lateinit var acknowledgment: Acknowledgment

    @Mock
    private lateinit var dadosPessoasService: DadosPessoasService

    @Mock
    private lateinit var dynamicCacheServiceImp: DynamicCacheServiceImp

    @InjectMocks
    private lateinit var kafkaMessageListener: KafkaMessageListener

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test successful message processing`() = runTest {
        `when`(dynamicCacheServiceImp.getJwt(
            anyString(),
            anyString(),
            anyString()
        )).thenReturn("123")
        `when`(dadosPessoasService.getDados()).thenReturn(PessoaResponse(
            nome = "gabriel",
            cpf = "91802388664",
            dataNascimento = "06-12-1996"
        ))
        val record = ConsumerRecord("my-topic", 0, 0, "key", "Test Message")
        kafkaMessageListener.listen(record, acknowledgment)

        verify(dadosPessoasService).getDados()
        verify(acknowledgment).acknowledge()
    }

    @Test
    fun `test DadosClienteException handling in message processing`() = runTest {
        `when`(dynamicCacheServiceImp.getJwt(
            anyString(),
            anyString(),
            anyString()
        )).thenReturn("123")
        `when`(dadosPessoasService.getDados()).thenThrow(
            DadosClienteException(
                message = "Forbidden",
                statusCode = 403,
                errorBody = ""
            )
        )
        val record = ConsumerRecord("my-topic", 0, 0, "key", "Test Message")
        kafkaMessageListener.listen(record, acknowledgment)
        verify(acknowledgment, never()).acknowledge()
        verify(dadosPessoasService).getDados()
    }


    @Test
    fun `test AuthClienteException  handling in message processing`() = runTest {
        `when`(dynamicCacheServiceImp.getJwt(anyString(), anyString(), anyString()
        )).thenThrow(
            AuthClienteException(
                message = "Forbidden",
                statusCode = 403,
                errorBody = ""
            )
        )
        `when`(dadosPessoasService.getDados()).thenReturn(PessoaResponse(
            nome = "gabriel",
            cpf = "91802388664",
            dataNascimento = "06-12-1996"
        ))
        val record = ConsumerRecord("my-topic", 0, 0, "key", "Test Message")
        kafkaMessageListener.listen(record, acknowledgment)
        verify(acknowledgment, never()).acknowledge()
        verify(dadosPessoasService).getDados()
    }


    @Test
    fun `test GenericException  handling in message processing`() = runTest {
        `when`(dynamicCacheServiceImp.getJwt(anyString(), anyString(), anyString()
        )).thenThrow(RuntimeException("xxxxx"))
        `when`(dadosPessoasService.getDados()).thenReturn(PessoaResponse(
            nome = "gabriel",
            cpf = "91802388664",
            dataNascimento = "06-12-1996"
        ))
        val record = ConsumerRecord("my-topic", 0, 0, "key", "Test Message")
        kafkaMessageListener.listen(record, acknowledgment)
        verify(acknowledgment, never()).acknowledge()
        verify(dadosPessoasService).getDados()
    }
}