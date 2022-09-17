package study.kafka.payment.presentation.message

import java.time.LocalDateTime

data class OrderInfo(
    val id: Long,
    val menu: String,
    val price: Int,
    val status: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)