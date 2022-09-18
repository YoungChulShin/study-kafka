package study.kafka.payment.domain.events

import java.time.LocalDateTime

data class PaymentFinished(
    val id: Long,
    val orderId: Long,
    val success: Boolean,
    val createdAt:LocalDateTime,
)