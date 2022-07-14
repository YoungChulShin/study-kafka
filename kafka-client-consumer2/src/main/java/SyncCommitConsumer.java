
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncCommitConsumer {

  private final static Logger logger = LoggerFactory.getLogger(SyncCommitConsumer.class);
  private final static String TOPIC_NAME = "test";
  private final static String BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092";
  private final static String GROUP_ID = "test-group3";

  private static KafkaConsumer<String, String> consumer;

  public static void main(String[] args) {
    Runtime.getRuntime().addShutdownHook(new ShutdownThread());

    Properties config = new Properties();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

    consumer = new KafkaConsumer<>(config);
    consumer.subscribe(List.of(TOPIC_NAME));

    int i = 0;
    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, String> record : records) {
          logger.info("message key: {}, value: {}", record.key(), record.value());
        }

        i++;
        System.out.println(i);
        if (i == 10) {

          consumer.wakeup();
        }
      }
    } catch (WakeupException e) {
      logger.info("wakeup 발생. 리소스를 해지합니다");
    } finally {
      logger.info("컨슈머 리소스를 해지합니다");
      consumer.close();
    }

    logger.info("프로그램 종료");
  }

  private static class ShutdownThread extends Thread {
    public void run() {
      logger.info("shutdown hook");
      consumer.wakeup();
    }
  }
}
