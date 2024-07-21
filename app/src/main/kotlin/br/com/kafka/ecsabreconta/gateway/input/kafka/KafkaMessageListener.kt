package br.com.kafka.ecsabreconta.gateway.input.kafka

import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.DadosPessoasService
import br.com.kafka.ecsabreconta.shared.exception.DadosClienteException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.util.*

@Component
class KafkaMessageListener(
    private val dadosPessoasService: DadosPessoasService
) {

    private val logger: Logger = LoggerFactory.getLogger(KafkaMessageListener::class.java)

    companion object {
        val correlation = UUID.randomUUID().toString()
    }

    @KafkaListener(topics = ["\${spring.kafka.consumer.topic}"])
    fun listen(message: String, acknowledgment: Acknowledgment) {
        runCatching {
            val dadosPessoalResponse = dadosPessoasService.getDados()
            logger.info(dadosPessoalResponse.toString())
            logger.info("Recebido: $message")
        }.onFailure { exception ->
            when(exception){
                is DadosClienteException -> {
                    logger.error("DadosPessoasServiceImp::getDados ${exception.message}, correlatioId: $correlation")
                } else -> logger.error("Erro ao consultar pessoa: ${exception.message}, correlatioId: $correlation", exception)
            }
        }.onSuccess {
            acknowledgment.acknowledge()
        }
    }
}
