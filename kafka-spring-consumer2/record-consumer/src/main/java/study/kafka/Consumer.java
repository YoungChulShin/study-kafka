package study.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  public static final Logger logger = LoggerFactory.getLogger(Consumer.class);

  @KafkaListener(
      topics = "test",
      groupId = "test-group-00")
  public void recordListener(ConsumerRecord<String, String> record) {
    logger.info("[test-group-00] {}", record.value());
  }

  @KafkaListener(
      topics = "test",
      groupId = "test-group-01")
  public void singleTopicListener(String messageValue) {
    logger.info("[test-group-01] {}", messageValue);
  }

  @KafkaListener(
      topics = "test",
      groupId = "test-group-02",
      properties = {
          "max.poll.interval.ms:60000",
          "auto.offset.reset:earliest"
      })
  public void singleTopicWithPropertiesListener(String messageValue) {
    logger.info("[test-group-02] {}", messageValue);
  }

  @KafkaListener(
      topics = "test",
      groupId = "test-group-03",
      concurrency = "3")
  public void concurrencyTopicListener(String messageValue) {
    logger.info("[test-group-03] {}", messageValue);
  }
}
