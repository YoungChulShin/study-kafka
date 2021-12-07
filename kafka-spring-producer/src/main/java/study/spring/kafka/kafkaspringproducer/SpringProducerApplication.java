package study.spring.kafka.kafkaspringproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class SpringProducerApplication implements CommandLineRunner {

  private static final String TOPIC_NAME = "test";

  private final KafkaTemplate<String, String> template;

  public SpringProducerApplication(
      @Autowired @Qualifier(value = "customKafkaTemplate") KafkaTemplate<String, String> template) {
    this.template = template;
  }

  @Override
  public void run(String... args) {
    ListenableFuture<SendResult<String, String>> future = template.send(TOPIC_NAME, "tes2");
    future.addCallback(new KafkaSendCallback<>() {

      @Override
      public void onFailure(KafkaProducerException ex) {
        System.out.println("failed. " + ex.getMessage());
      }

      @Override
      public void onSuccess(SendResult<String, String> result) {
        System.out.println("success");
      }
    });

    System.exit(0);
  }
}
