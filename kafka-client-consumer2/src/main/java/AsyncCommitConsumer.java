
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncCommitConsumer {

  private final static Logger logger = LoggerFactory.getLogger(AsyncCommitConsumer.class);
  private final static String TOPIC_NAME = "test";
  private final static String BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092";
  private final static String GROUP_ID = "test-group2";

  public static void main(String[] args) {
    Properties config = new Properties();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
    consumer.subscribe(List.of(TOPIC_NAME));

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
      for (ConsumerRecord<String, String> record : records) {
        logger.info("message key: {}, value: {}", record.key(), record.value());
      }
      consumer.commitAsync(new OffsetCommitCallback() {
        @Override
        public void onComplete(
            Map<TopicPartition, OffsetAndMetadata> offsets,
            Exception exception) {
          if (exception != null) {
            logger.info("커밋 실패");
          } else {
            logger.info("커밋 성공");
          }
        }
      });
    }
  }


}
