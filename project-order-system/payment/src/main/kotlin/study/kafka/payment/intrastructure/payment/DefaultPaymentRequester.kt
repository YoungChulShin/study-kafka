package study.kafka.payment.intrastructure.payment

import org.springframework.stereotype.Component
import study.kafka.payment.domain.PaymentRequester

@Component
class DefaultPaymentRequester: PaymentRequester {

    // 100원 미만은 소액이라서 실패하도록 설정한다
    override fun request(price: Int): Boolean {
        Thread.sleep(1000)
        return price >= 100
    }
}