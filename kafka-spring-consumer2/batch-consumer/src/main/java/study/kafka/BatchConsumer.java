package study.kafka;

import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class BatchConsumer {

  private final static Logger logger = LoggerFactory.getLogger(BatchConsumer.class);

//  @KafkaListener(
//      topics = "test",
//      groupId = "test-group-0")
//  public void batchListener(ConsumerRecords<String, String> records) {
//    logger.info("[test-group-0] start");
//    records.forEach(record -> logger.info("[test-group-0] {}", record.value()));
//    logger.info("[test-group-0] end");
//  }

//  @KafkaListener(
//      topics = "test",
//      groupId = "test-group-1")
//  public void batchListener(List<String> records) {
//    logger.info("[test-group-1] start");
//    records.forEach(record -> logger.info("[test-group-1] {}", record));
//    logger.info("[test-group-1] end");
//  }

  // application.yml에서 아래 설정을 추가한ㄷ
  // ack-mode: manual_immediate
//  @KafkaListener(
//      topics = "test",
//      groupId = "test-group-2")
//  public void commitListener(List<String> records, Acknowledgment acknowledgment) {
//    logger.info("[test-group-1] start");
//    records.forEach(record -> logger.info("[test-group-1] {}", record));
//    logger.info("[test-group-1] end");
//    acknowledgment.acknowledge(); // 이 설정이 없으면 커밋이 되지 않기 때문에, 재시작하면 데이터를 다시 가져온다
//  }

  // 동기/비동기 커밋을 사용하고 싶을 때
  // 비동기 커밋을 이용하면 콜백도 사용할 수 있다
  // 리스너가 커밋을 사용하지 않도록 AckModel를 Manual 또는 Manual_immediate로 해야한다
  @KafkaListener(
      topics = "test",
      groupId = "test-group-3")
  public void consumerCommitListener(List<String> records, Consumer<String, String> consumer) {
    logger.info("[test-group-1] start");
    records.forEach(record -> logger.info("[test-group-1] {}", record));
    logger.info("[test-group-1] end");

    consumer.commitAsync((offsets, exception) -> logger.info("[test-group-1] complete"));
  }
}
