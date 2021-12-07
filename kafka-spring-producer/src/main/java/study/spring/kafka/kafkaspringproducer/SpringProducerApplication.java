package study.spring.kafka.kafkaspringproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class SpringProducerApplication implements CommandLineRunner {

  private static final String TOPIC_NAME = "test";

  @Autowired
  @Qualifier(value = "customKafkaTemplate")
  private KafkaTemplate<String, String> template;

  @Override
  public void run(String... args) {
    ListenableFuture<SendResult<String, String>> future = template.send(TOPIC_NAME, "test");
    future.addCallback(new KafkaSendCallback<String, String>() {

      @Override
      public void onFailure(KafkaProducerException ex) {

      }

      @Override
      public void onSuccess(SendResult<String, String> result) {

      }
    });

    System.exit(0);
  }
}
