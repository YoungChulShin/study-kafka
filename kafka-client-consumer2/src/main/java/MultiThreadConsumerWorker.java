import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiThreadConsumerWorker implements Runnable{

  private static final Logger logger = LoggerFactory.getLogger(MultiThreadConsumerWorker.class);
  private final String readValue;

  public MultiThreadConsumerWorker(String readValue) {
    this.readValue = readValue;
  }

  @Override
  public void run() {
    logger.info("thread: {}\trecord:{}", Thread.currentThread().getName(), readValue);
  }
}
