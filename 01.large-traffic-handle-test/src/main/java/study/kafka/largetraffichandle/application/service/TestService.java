package study.kafka.largetraffichandle.application.service;

import org.springframework.stereotype.Component;
import study.kafka.largetraffichandle.application.port.in.TestUseCase;

@Component
class TestService implements TestUseCase {

  @Override
  public void calculate() {
    for (long i = 0; i <= 3000000000L; i++) { }
  }
}
