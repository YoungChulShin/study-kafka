package configuration

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties

@Configuration
class StreamConfiguration(
    @Value("\${stream.config.application-name}") private val applicationName: String,
    @Value("\${stream.config.bootstrap-servers}") private val bootstrapServers: String,
) {

    @Bean
    fun streamConfig(): Properties {
        val properties = Properties()
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationName)
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().javaClass)
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().javaClass)

        return properties
    }
}