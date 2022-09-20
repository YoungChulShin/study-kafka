package study.kafka.order.presentation.message

import java.time.LocalDateTime

data class OrderPaymentInfo(
    val orderId: Long,
    val success: Boolean,
    val createdAt: LocalDateTime,
)