package study.kafka.payment.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import study.kafka.payment.application.model.CreatePaymentCommand
import study.kafka.payment.common.LogEventPublisher
import study.kafka.payment.domain.PaymentHistory
import study.kafka.payment.domain.PaymentHistoryRepository
import study.kafka.payment.domain.events.PaymentFinished
import study.kafka.payment.infrastructure.payment.FailPaymentRequester
import study.kafka.payment.infrastructure.payment.SuccessPaymentRequester

class PaymentServiceTest {

    @Test
    fun `결제가 성공하면 결과를 저장하고 이벤트를 발행한다`() {
        // given
        val paymentRequester = SuccessPaymentRequester()
        val repositoryMock = Mockito.mock(PaymentHistoryRepository::class.java)
        val eventPublisher = LogEventPublisher()
        val sut = PaymentService(paymentRequester, repositoryMock, eventPublisher)

        val command = CreatePaymentCommand(
            orderId = 1L,
            menu = "삼각김밥",
            price = 1000
        )

        // when
        sut.createPayment(command)

        // then
        Mockito.verify(repositoryMock).save(any(PaymentHistory::class.java))

        Assertions.assertThat(eventPublisher.eventCount()).isEqualTo(1)
        val event = eventPublisher.events[0] as PaymentFinished
        Assertions.assertThat(event.orderId).isEqualTo(1L)
        Assertions.assertThat(event.success).isEqualTo(true)
    }

    @Test
    fun `결제가 실패하면 결과를 저장하고 이벤트를 발행한다`() {
        // given
        val paymentRequester = FailPaymentRequester()
        val repositoryMock = Mockito.mock(PaymentHistoryRepository::class.java)
        val eventPublisher = LogEventPublisher()
        val sut = PaymentService(paymentRequester, repositoryMock, eventPublisher)

        val command = CreatePaymentCommand(
            orderId = 1L,
            menu = "삼각김밥",
            price = 1000
        )

        // when
        sut.createPayment(command)

        // then
        Mockito.verify(repositoryMock).save(any(PaymentHistory::class.java))

        Assertions.assertThat(eventPublisher.eventCount()).isEqualTo(1)
        val event = eventPublisher.events[0] as PaymentFinished
        Assertions.assertThat(event.orderId).isEqualTo(1L)
        Assertions.assertThat(event.success).isEqualTo(false)
    }
}