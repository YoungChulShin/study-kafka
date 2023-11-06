package study.kafka.largetraffichandle.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.kafka.largetraffichandle.application.port.in.TestUseCase;

@RestController
@RequiredArgsConstructor
class TestController {

  private final TestUseCase testUseCase;

  @PostMapping(value = "/api/calculate")
  String calculate() {
    testUseCase.calculate();
    return "done";
  }
}
