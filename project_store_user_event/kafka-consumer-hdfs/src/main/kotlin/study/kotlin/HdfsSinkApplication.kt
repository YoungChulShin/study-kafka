package study.kotlin

import org.slf4j.LoggerFactory

class HdfsSinkApplication {

    companion object {
        val logger = LoggerFactory.getLogger(HdfsSinkApplication::class.java)

        val BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092"
        val TOPIC_NAME = "select-color"
        val GROUP_ID = "color-hdfs-save-consumer-group"
        val CONSUMER_COUNT = 3
    }




    fun main(args: Array<String>) {

    }
}