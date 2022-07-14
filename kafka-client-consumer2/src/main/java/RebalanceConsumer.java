
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RebalanceConsumer {

  private final static Logger logger = LoggerFactory.getLogger(RebalanceConsumer.class);
  private final static String TOPIC_NAME = "test";
  private final static String BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092";
  private final static String GROUP_ID = "test-group1";

  public static void main(String[] args) {
    Properties config = new Properties();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
    consumer.subscribe(List.of(TOPIC_NAME), new RebalanceListener());

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
      for (ConsumerRecord<String, String> record : records) {
        logger.info("message key: {}, value: {}", record.key(), record.value());

//        consumer.commitSync();
      }
    }
  }

  private static class RebalanceListener implements ConsumerRebalanceListener {

    // 리밸런스가 시작되기 전
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

    }

    // 리팰런스가 시작된 후
    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

    }
  }


}
