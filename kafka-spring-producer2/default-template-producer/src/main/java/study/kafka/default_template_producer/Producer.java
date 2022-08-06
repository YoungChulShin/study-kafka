package study.kafka.default_template_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class Producer implements CommandLineRunner {

  private static final String TOPIC_NAME = "test";

  @Autowired
  private KafkaTemplate<String, String> template;

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 100; i++) {
      System.out.println("Send-Start: " + i);
      ListenableFuture<SendResult<String, String>> ack = template.send(TOPIC_NAME, "test" + i);
      ack.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
        @Override
        public void onFailure(Throwable ex) {
          System.out.println("Send-Fail: " + ex.getMessage());
        }

        @Override
        public void onSuccess(SendResult<String, String> result) {
          System.out.println("Send-Success: " + result.getProducerRecord().value());
        }
      });
      Thread.sleep(1);
    }
  }
}
