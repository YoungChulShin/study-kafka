package study.kafka.payment.intrastructure.payment

import org.springframework.stereotype.Component
import study.kafka.payment.domain.PaymentRequester

@Component
class DefaultPaymentRequester: PaymentRequester {

    override fun request(price: Int): Boolean {
        return true
    }
}