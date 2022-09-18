package study.kafka.payment.domain

import java.time.LocalDateTime

data class OrderPaymentInfo(
    val orderId: Long,
    val success: Boolean,
    val createdAt: LocalDateTime,
)