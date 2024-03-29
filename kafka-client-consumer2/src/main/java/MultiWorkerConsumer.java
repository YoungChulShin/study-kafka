import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiWorkerConsumer {

  private final static Logger logger = LoggerFactory.getLogger(MultiWorkerConsumer.class);
  private final static String TOPIC_NAME = "multi-thread-test";
  private final static String BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092";
  private final static String GROUP_ID = "test-group-multi-thread-consumer";

  public static void main(String[] args) {
    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

    try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
      consumer.subscribe(List.of(TOPIC_NAME));
      ExecutorService executorService = Executors.newCachedThreadPool();
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        for (ConsumerRecord<String, String> record : records) {
          MultiWorkerConsumerWorker worker = new MultiWorkerConsumerWorker(record.value());
          executorService.execute(worker);
        }
      }
    }
  }
}
