package br.com.kafka.ecsabreconta.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import java.lang.reflect.Field

@ExtendWith(MockitoExtension::class)
@PropertySource("classpath:application-test.yaml")
class KafkaConsumerConfigTest {

    @InjectMocks
    private lateinit var kafkaConsumerConfig: KafkaConsumerConfig

    @BeforeEach
    fun setUp() {
        kafkaConsumerConfig.apply {
            bootstrapServers = "localhost:9092"
            groupId = "test-group"

        }
    }

    @Test
    fun testConsumerFactory() {
        val consumerFactory = kafkaConsumerConfig.consumerFactory()
        assertEquals(DefaultKafkaConsumerFactory::class.java, consumerFactory::class.java)
    }

    @Test
    fun testKafkaListenerContainerFactory() {
        val factory = kafkaConsumerConfig.kafkaListenerContainerFactory()
        assertEquals(ConcurrentKafkaListenerContainerFactory::class.java, factory::class.java)
        assertEquals(ContainerProperties.AckMode.MANUAL, factory.containerProperties.ackMode)

        val concurrencyField: Field = ConcurrentKafkaListenerContainerFactory::class.java.getDeclaredField("concurrency")
        concurrencyField.isAccessible = true
        val concurrency = concurrencyField.get(factory) as Int
        assertEquals(2, concurrency)
    }
}
