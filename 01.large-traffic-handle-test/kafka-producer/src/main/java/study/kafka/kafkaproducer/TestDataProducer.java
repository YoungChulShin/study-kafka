package study.kafka.kafkaproducer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestDataProducer implements CommandLineRunner {

  private final Integer TEST_COUNT = 1000;
  private final String TEST_TOPIC = "test-topic";
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < TEST_COUNT; i++) {
      String data = "test-" + i;
      kafkaTemplate.send(TEST_TOPIC, String.valueOf(i), data)
          .thenApply(sendResult -> {
            if (sendResult.getRecordMetadata() != null) {
              // 메시지 전송 성공
              log.info("Message sent successfully. data: " + data);
            } else {
              // 메시지 전송 실패
              log.info("Message sent failed. data: " + data);
            }

            return sendResult;
          })
          .exceptionally(exception -> {
            log.info("Error message sending. data: " + data, exception);
            return null;
          });
    }
  }
}
