package study.spring.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaClientConsumer {

  private static final Logger logger = LoggerFactory.getLogger(KafkaClientConsumer.class);
  private static final String TOPIC_NAME = "test2-1";
  private static final String TOPIC_NAME2 = "test2-2";
  private static final String TOPIC_NAME3 = "test2-3";
  private static final String BOOTSTRAP_SERVERS = "localhost:9092";
  private static final String GROUP_ID = "test-group";

  public static void main(String[] args) {
    String hostName = args[0];

    Properties config = new Properties();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

    KafkaConsumer<String, String> consumer = new KafkaConsumer(config);
    consumer.subscribe(Arrays.asList(TOPIC_NAME, TOPIC_NAME2, TOPIC_NAME3));

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

      for (ConsumerRecord<String, String> record : records) {
        logger.info("{} : " + record.toString(), hostName);
      }
    }
  }
}
