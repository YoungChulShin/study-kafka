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
    try {
      Thread.sleep(500);
    } catch (Exception ignored) { }
    log.info("계산을 완료했습니다.");
  }
}
