import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiThreadConsumerWorker implements Runnable {

  private final static Logger logger = LoggerFactory.getLogger(MultiThreadConsumerWorker.class);
  private Properties properties;
  private String topic;
  private String threadName;
  private KafkaConsumer<String, String> consumer;

  public MultiThreadConsumerWorker(Properties properties, String topic, int number) {
    this.properties = properties;
    this.topic = topic;
    this.threadName = "consumer-thread-" + number;
  }

  @Override
  public void run() {
    consumer = new KafkaConsumer<String, String>(properties);
    consumer.subscribe(Arrays.asList(topic));

    while (true) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
      for (ConsumerRecord<String, String> record : records) {
        logger.info("{} - {} - {}", threadName, record.key(), record.value());
      }
    }
  }
}
