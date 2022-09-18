package study.kafka.payment.infrastructure.payment

import study.kafka.payment.domain.PaymentRequester

class SuccessPaymentRequester : PaymentRequester {
    override fun request(price: Int): Boolean = true
}