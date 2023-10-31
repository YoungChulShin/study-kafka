package study.kafka.largetraffichandle.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.kafka.largetraffichandle.application.port.in.TestUseCase;

@Slf4j
@Component
class TestService implements TestUseCase {

  @Override
  public void calculate() {
    log.info("계산을 시작합니다.");
    for (long i = 0; i <= 3000000000L; i++) { }
    log.info("계산을 완료했습니다.");
  }
}
