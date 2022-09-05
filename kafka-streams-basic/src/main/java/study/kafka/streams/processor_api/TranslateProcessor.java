package study.kafka.streams.processor_api;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

public class TranslateProcessor implements Processor<String, String> {

  private ProcessorContext context;

  @Override
  public void init(ProcessorContext context) {
    this.context = context;
  }

  @Override
  public void process(String key, String value) {
    context.forward(key, value + " 5자이상입니다");
    context.commit();
  }

  @Override
  public void close() {
  }
}
