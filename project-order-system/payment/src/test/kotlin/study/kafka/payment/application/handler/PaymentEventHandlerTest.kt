package study.kafka.payment.application.handler

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import study.kafka.payment.domain.events.PaymentFinished
import study.kafka.payment.infrastructure.transmitter.LocalPaymentTransmitter
import java.time.LocalDateTime

class PaymentEventHandlerTest {

    @Test
    fun `결제가 완료되면 결제 정보를 전달한다`() {
        // given
        val paymentTransmitter = LocalPaymentTransmitter()
        val sut = PaymentEventHandler(paymentTransmitter)

        val event = PaymentFinished(
            id = 1L,
            orderId = 2L,
            success = true,
            createdAt = LocalDateTime.now()
        )

        // when
        sut.handlePaymentFinishedEvent(event)

        // then
        Assertions.assertThat(paymentTransmitter.orderPaymentInfo).isNotNull
        Assertions.assertThat(paymentTransmitter.orderPaymentInfo?.orderId).isEqualTo(2L)
        Assertions.assertThat(paymentTransmitter.orderPaymentInfo?.success).isEqualTo(true)
    }
}