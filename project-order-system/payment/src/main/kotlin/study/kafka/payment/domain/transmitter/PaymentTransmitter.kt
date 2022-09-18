package study.kafka.payment.domain.transmitter

import study.kafka.payment.domain.OrderPaymentInfo

interface PaymentTransmitter {

    fun transmitPaymentFinished(orderPaymentInfo: OrderPaymentInfo)
}