package study.kafka.payment.domain

interface PaymentRequester {

    fun request(price: Int): Boolean
}