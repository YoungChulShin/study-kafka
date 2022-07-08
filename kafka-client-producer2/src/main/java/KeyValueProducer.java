import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyValueProducer {
  private final static Logger logger = LoggerFactory.getLogger(KeyValueProducer.class);
  private final static String TOPIC_NAME = "test";
  private final static String BOOTSTRAP_SERVERS = "localhost:19092,localhost:29092,localhost:39092";

  public static void main(String[] args) {

    Properties config = new Properties();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class);

    try (KafkaProducer<String, String> producer = new KafkaProducer<>(config)) {
      String messageKey = "testKey4";
      String messageValue = "testMessage4";
      ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageKey, messageValue);
      logger.info("전송 데이터 - {}", record);
      RecordMetadata recordMetadata = producer.send(record).get();
      logger.info("전송 응답 - {}", recordMetadata);
      producer.flush();
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
