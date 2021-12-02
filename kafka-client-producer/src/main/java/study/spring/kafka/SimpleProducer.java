package study.spring.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class SimpleProducer {
  static final Logger logger = LoggerFactory.getLogger(SimpleProducer.class);
  static final String TOPIC_NAME = "test";
  static final String BOOTSTRAP_SERVERS = "localhost:9092";
}
