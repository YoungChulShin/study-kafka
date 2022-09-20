package study.kafka.payment.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.listener.ContainerProperties

@Configuration
class KafkaConsumerConfig(
    @Value("\${spring.kafka.bootstrap-servers}") private val bootstrapServers: String
) {

    @Bean
    fun orderCreatedContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ByteArray>> {
        val properties = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ByteArrayDeserializer::class.java,
        )

        val consumerFactory = DefaultKafkaConsumerFactory<String, ByteArray>(properties)

        val factory = ConcurrentKafkaListenerContainerFactory<String, ByteArray>()
        factory.isBatchListener = false
        factory.containerProperties.ackMode = ContainerProperties.AckMode.RECORD
        factory.consumerFactory = consumerFactory

        return factory
    }
}