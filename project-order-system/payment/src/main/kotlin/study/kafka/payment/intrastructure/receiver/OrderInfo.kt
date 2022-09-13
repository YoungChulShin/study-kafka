package study.kafka.payment.intrastructure.receiver

import java.time.LocalDateTime

data class OrderInfo(
    val id: Long,
    val menu: String,
    val price: Int,
    val status: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)