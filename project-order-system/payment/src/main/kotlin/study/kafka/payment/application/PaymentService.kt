package study.kafka.payment.application

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.kafka.payment.application.model.CreatePaymentCommand
import study.kafka.payment.domain.PaymentHistory
import study.kafka.payment.domain.PaymentHistoryRepository
import study.kafka.payment.domain.PaymentRequester
import study.kafka.payment.domain.events.PaymentFinished
import java.time.LocalDateTime

@Service
class PaymentService(
    private val paymentRequester: PaymentRequester,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun createPayment(command: CreatePaymentCommand) {
        val paymentResult = paymentRequester.request(command.price)
        val paymentHistory = PaymentHistory(
            orderId = command.orderId,
            price = command.price,
            menu = command.menu,
            success = paymentResult
        )
        paymentHistoryRepository.save(paymentHistory)
        publishPaymentResultEvent(
            id = paymentHistory.id!!,
            orderId = paymentHistory.orderId,
            success = paymentHistory.success,
            createdAt = paymentHistory.createdAt!!
        )
    }

    private fun publishPaymentResultEvent(
        id: Long,
        orderId: Long,
        success: Boolean,
        createdAt: LocalDateTime,
    ) {
        eventPublisher.publishEvent(PaymentFinished(id, orderId, success, createdAt))
    }
}