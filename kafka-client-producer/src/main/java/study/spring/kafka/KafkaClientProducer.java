package study.spring.kafka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KafkaClientProducer {

  public static void main(String[] args) throws IOException {

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    SimpleProducer simpleProducer = new SimpleProducer();
    while (true) {
      String message = bufferedReader.readLine();
      System.out.println(message);
      if (message.equals("exit")) {
        return;
      }
      if (message.length() == 0) {
        continue;
      }

      String[] splitMessages = message.split(" ");
      simpleProducer.produce(splitMessages[0], splitMessages[1]);
    }
  }
}
