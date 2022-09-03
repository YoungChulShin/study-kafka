package study.kotlin

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import study.kotlin.consumer.ConsumerWorker
import java.util.Properties
import java.util.concurrent.Executors

class HdfsSinkApplication {

    companion object {
        val logger = LoggerFactory.getLogger(HdfsSinkApplication::class.java)

        val BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092"
        val TOPIC_NAME = "select-color"
        val GROUP_ID = "color-hdfs-save-consumer-group"
        val CONSUMER_COUNT = 3
        val workers = mutableListOf<ConsumerWorker>()
    }

    fun main(args: Array<String>) {
        // shutdown이 감지되면 버퍼에 있는 데이터를 다 보낸다
        Runtime.getRuntime().addShutdownHook(ShutdownThread())

        // 설정 추가
        val configs = Properties()
        configs[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_SERVERS
        configs[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
        configs[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
        configs[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name

        // 스레드 생성 및 실행
        val executorService = Executors.newFixedThreadPool(3)
        for (i in 0 .. CONSUMER_COUNT) {
            workers.add(ConsumerWorker(configs, TOPIC_NAME, i))
        }

        workers.forEach(executorService::execute)
    }

    private class ShutdownThread : Thread() {

        override fun run() {
            logger.info("Shutdown hook")
            workers.forEach { it.stopAndWakeup() }
        }
    }
}