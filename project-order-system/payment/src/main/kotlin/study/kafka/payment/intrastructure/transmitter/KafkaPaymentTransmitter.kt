package study.kafka.payment.intrastructure.transmitter

import org.springframework.stereotype.Component
import study.kafka.payment.domain.OrderPaymentInfo
import study.kafka.payment.domain.transmitter.PaymentTransmitter

@Component
class KafkaPaymentTransmitter : PaymentTransmitter {
    override fun transmitPaymentFinished(orderPaymentInfo: OrderPaymentInfo) {

    }
}