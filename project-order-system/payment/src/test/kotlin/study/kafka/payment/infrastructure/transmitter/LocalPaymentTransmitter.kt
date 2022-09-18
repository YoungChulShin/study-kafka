package study.kafka.payment.infrastructure.transmitter

import study.kafka.payment.domain.OrderPaymentInfo
import study.kafka.payment.domain.transmitter.PaymentTransmitter

class LocalPaymentTransmitter : PaymentTransmitter {

    var orderPaymentInfo: OrderPaymentInfo? = null

    override fun transmitPaymentFinished(orderPaymentInfo: OrderPaymentInfo) {
        this.orderPaymentInfo = orderPaymentInfo
    }
}