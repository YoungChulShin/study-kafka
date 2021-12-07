package study.spring.kafka.kafkaspringconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConsumerListener {

  @KafkaListener(
      topics = "test",
      groupId = "test-group-00")
  public void recordListener(ConsumerRecords<String, String> record) {
    System.out.println(record.toString());
  }

  @KafkaListener(
      topics = "test",
      groupId = "test-group-01")
  public void singleTopicListener(String messageValue) {
    System.out.println(messageValue);
  }

  @KafkaListener(
      topics = "test",
      groupId = "test-group-02",
      properties = {
          "max.poll.interval.ms:60000",
          "auto.offset.reset:earliest" })
  public void singleTopicWithPropertiesListener(String messageValue) {
    System.out.println(messageValue);
  }

  @KafkaListener(
      topics = "test",
      groupId = "test-group-03",
      concurrency = "3")
  public void concurrentTopicListener(String messageValue) {
    System.out.println(messageValue);
  }
}
