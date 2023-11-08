package study.kafka.largetraffichandle.adapter.in.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import study.kafka.largetraffichandle.application.port.in.TestUseCase;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestConsumer {

  private static final String TEST_TOPIC = "test-topic";
  private static final String CONSUMER_GROUP = "test-group-01";

  private final TestUseCase testUseCase;

  @KafkaListener(
      topics = TEST_TOPIC,
      groupId = CONSUMER_GROUP
  )
  public void handleMessage(ConsumerRecord<String, String> record) {
    testUseCase.calculate();
    log.info("[" + record.partition() + "] Message receive successfully. data: " + record.value());
  }
}
