package br.com.kafka.ecsabreconta.gateway.input.kafka

import br.com.kafka.ecsabreconta.core.doman.User
import br.com.kafka.ecsabreconta.core.gateway.output.client.usecase.DadosPessoasService
import br.com.kafka.ecsabreconta.core.mapper.UserMapper
import br.com.kafka.ecsabreconta.core.usecase.impl.DynamicCacheServiceImp
import br.com.kafka.ecsabreconta.gateway.output.kafka.Producer
import br.com.kafka.ecsabreconta.shared.exception.AuthClienteException
import br.com.kafka.ecsabreconta.shared.exception.DadosClienteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import java.util.*

@Component
class KafkaMessageListener(
    private val dadosPessoasService: DadosPessoasService,
    private val dynamicCacheServiceImp: DynamicCacheServiceImp,
    private val producer: Producer
) {

    private val logger: Logger = LoggerFactory.getLogger(KafkaMessageListener::class.java)

    companion object {
        val correlation = UUID.randomUUID().toString()
    }

    @KafkaListener(
        topics = ["\${spring.kafka.consumer.topic}"],
        groupId = "\${spring.kafka.consumer.group-id}"
    )
    suspend fun listen(message: ConsumerRecord<String, String>, acknowledgment: Acknowledgment) {
        withContext(Dispatchers.IO) {
            runCatching {
                val dadosPessoalResponse = dadosPessoasService.getDados()
                val getToken = dynamicCacheServiceImp.getJwt(
                    accessToken = "access_token",
                    "ae0a1bfa-f3e6-490a-b6d9-7a9ce4d0e551",
                    "3a547506-9421-4c78-afad-ce94c54e4baa",
                )
                val user = User(
                    "gabriel",
                    "1234",
                    "123"
                )
                logger.info(getToken.toString())
                logger.info(dadosPessoalResponse.toString())
                logger.info("Recebido: ${message.value()}")

                producer.senMensagem("meu-topic", user)

            }.onFailure { exception ->
                when (exception) {
                    is DadosClienteException -> {
                        logger.error("DadosPessoasServiceImp::getDados ${exception.message}, correlatioId: $correlation")
                    }
                    is AuthClienteException -> {
                        logger.error("AuthServiceImp::getToken ${exception.message}, correlatioId: $correlation")
                    }
                    else -> logger.error(
                        "Erro ao consultar pessoa: ${exception.message}, correlatioId: $correlation",
                        exception
                    )
                }
            }.onSuccess {
                logger.info("sucess!")
                acknowledgment.acknowledge()
            }
        }
    }
}
