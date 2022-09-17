package study.kafka.payment.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.kafka.payment.application.model.CreatePaymentCommand
import study.kafka.payment.domain.PaymentHistory
import study.kafka.payment.domain.PaymentHistoryRepository
import study.kafka.payment.domain.PaymentRequester

@Service
class PaymentService(
    private val paymentRequester: PaymentRequester,
    private val paymentHistoryRepository: PaymentHistoryRepository,
) {

    @Transactional
    fun createPayment(command: CreatePaymentCommand) {
        val paymentResult = paymentRequester.request(command.price)
        val paymentHistory = PaymentHistory(
            orderId = command.orderId,
            price = command.price,
            menu = command.menu,
            result = paymentResult
        )
        paymentHistoryRepository.save(paymentHistory)

        // 이벤트 발행
    }

    private fun publishPaymentRequestResultEvent() {
        // 타입
        // 결과
        //
    }
}