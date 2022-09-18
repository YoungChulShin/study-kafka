package study.kafka.payment.application.handler

import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener
import study.kafka.payment.domain.OrderPaymentInfo
import study.kafka.payment.domain.events.PaymentFinished
import study.kafka.payment.domain.transmitter.PaymentTransmitter

@Component
class PaymentEventHandler(
    private val paymentTransmitter: PaymentTransmitter,
) {

    @TransactionalEventListener
    fun handlePaymentFinishedEvent(event: PaymentFinished) {
        paymentTransmitter.transmitPaymentFinished(
            OrderPaymentInfo(
                orderId = event.orderId,
                success = event.success,
                createdAt = event.createdAt
            )
        )
    }
}