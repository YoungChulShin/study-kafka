package study.spring.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerCallback implements Callback {

  private static final Logger logger = LoggerFactory.getLogger(Logger.class);

  @Override
  public void onCompletion(RecordMetadata metadata, Exception exception) {
    if (exception == null) {
      logger.info(metadata.toString());
    } else {
      logger.error(exception.getMessage(), exception);
    }
  }
}
