import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiWorkerConsumerWorker implements Runnable{

  private static final Logger logger = LoggerFactory.getLogger(MultiWorkerConsumerWorker.class);
  private final String readValue;

  public MultiWorkerConsumerWorker(String readValue) {
    this.readValue = readValue;
  }

  @Override
  public void run() {
    logger.info("thread: {}\trecord:{}", Thread.currentThread().getName(), readValue);
  }
}
