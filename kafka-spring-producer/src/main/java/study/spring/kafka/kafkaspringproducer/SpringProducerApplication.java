package study.spring.kafka.kafkaspringproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;

public class SpringProducerApplication implements CommandLineRunner {

  private static final String TOPIC_NAME = "test";

  @Autowired
  private KafkaTemplate<Integer, String> template;

  @Override
  public void run(String... args) {
    for (int i = 0; i < 10; i++) {
      template.send(TOPIC_NAME, "test" + i);
    }
    System.exit(0);
  }
}
