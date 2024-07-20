package br.com.kafka.ecsabreconta.gateway.input.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaMessageListener {

    @KafkaListener(topics = ["\${spring.kafka.consumer.topic}"])
    fun listen(message: String) {
        println("Received Message: $message")
    }
}
