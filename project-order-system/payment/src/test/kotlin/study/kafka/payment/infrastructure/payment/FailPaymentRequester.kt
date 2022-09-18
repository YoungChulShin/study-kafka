package study.kafka.payment.infrastructure.payment

import study.kafka.payment.domain.PaymentRequester

class FailPaymentRequester : PaymentRequester {
    override fun request(price: Int): Boolean = false
}