package study.kafka.custom_template_producer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class Producer implements CommandLineRunner {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public Producer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    String topicName = "customTemplate";
    for(int i = 0; i < 100; i++) {
      ListenableFuture<SendResult<String, String>> resultListenableFuture =
          kafkaTemplate.send(topicName, "test-" + i);
      resultListenableFuture.addCallback(
          new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
              System.out.println("Fail");
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
              System.out.println("Success: " + result.getProducerRecord().value());
            }
          });
      Thread.sleep(1000);
    }
  }
}
