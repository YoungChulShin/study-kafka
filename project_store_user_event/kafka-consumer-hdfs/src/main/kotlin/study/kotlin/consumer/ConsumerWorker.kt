package study.kotlin.consumer

import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.errors.WakeupException
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.Properties
import java.util.concurrent.ConcurrentHashMap

class ConsumerWorker(
    var prop: Properties,
    val topic: String,
    val number: Int,
) : Runnable {

    private val threadName = "consumer-thread-$number"
    private lateinit var consumer: KafkaConsumer<String, String>

    init {
        logger.info("Generate ConsumerWorker")
    }

    companion object {
        val FLUSH_RECORD_COUNT = 10

        val logger = LoggerFactory.getLogger(ConsumerWorker::class.java)
        val bufferString: MutableMap<Int, MutableList<String>> = ConcurrentHashMap()
        val currentFileOffset: MutableMap<Int, Long> = ConcurrentHashMap()
    }

    override fun run() {
        Thread.currentThread().name = threadName
        consumer = KafkaConsumer(prop)
        consumer.subscribe(listOf(topic))

        try {
            while (true) {
                // 1초동안 데이터를 가져와서
                val records = consumer.poll(Duration.ofSeconds(1))

                // 버퍼에 넣어준다
                records.forEach { addHdfsFileBuffer(it) }

                // 버퍼의 내용을 hdfs에 저장
                saveBufferToHdfsFile(consumer.assignment())
            }
        } catch (e: WakeupException) {
            logger.warn("Wakeup consumer")
        } catch (e: Exception) {
            logger.error(e.message, e)
        } finally {
            consumer.close()
        }
    }

    private fun addHdfsFileBuffer(record: ConsumerRecord<String, String>) {
        // bufferString은 파티션별 버퍼데이터를 저장
        val buffer = bufferString.getOrDefault(record.partition(), mutableListOf())
        // 파티션에 해당하는 버퍼 데이터에 value를 추가
        buffer.add(record.value())
        bufferString[record.partition()] = buffer
        // 첫번째 버퍼라면 오프셋을 저장한다
        if (buffer.size == 1) {
            currentFileOffset[record.partition()] = record.offset()
        }
    }

    private fun saveBufferToHdfsFile(partitions: Set<TopicPartition>) {
        partitions.forEach{ checkFlushCount(it.partition()) }
    }

    private fun checkFlushCount(partitionNo: Int) {
        bufferString[partitionNo]?.let {
            if (it.size >= FLUSH_RECORD_COUNT) {
                save(partitionNo)
            }
        }
    }

    private fun save(partitionNo: Int) {
        if (bufferString.getOrDefault(partitionNo, 0) == 0) {
            return
        }

        try {
            // 하둡에 데이터 전송
            val fileName = "/data/color-${partitionNo}-${currentFileOffset[partitionNo]}.log"
            val configuration = Configuration()
            configuration.set("fs.defaultFS", "hdfs://localhost:9000")
            val hdfsFileSystem = FileSystem.get(configuration)
            val fileOutputStream = hdfsFileSystem.create(Path(fileName))
            fileOutputStream.writeBytes(StringUtils.join(bufferString.get(partitionNo), "\n"))
            fileOutputStream.close()

            // 버퍼 초기화
            bufferString.put(partitionNo, mutableListOf())
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }

    private fun saveRemainBufferToHdfsFile() {
        bufferString.forEach { save(it.key) }
    }

    fun stopAndWakeup() {
        logger.info("stopAndWakeup")
        consumer.wakeup()
        saveRemainBufferToHdfsFile()
    }
}