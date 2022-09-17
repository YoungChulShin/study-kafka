package study.kafka.payment.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.kafka.payment.domain.PaymentRequester

@Service
class PaymentService(
    private val paymentRequester: PaymentRequester
) {

    @Transactional
    fun createPayment(menu: String, price: Int) {
        // 결제 시도
        // 결제 결과 업데이트
        // 이벤트 발행
    }
}