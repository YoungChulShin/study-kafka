package study.kafka.order.domain.model

import study.kafka.order.domain.Order
import study.kafka.order.domain.OrderStatus
import java.time.LocalDateTime

data class OrderInfo(
    val id: Long?,
    val menu: String,
    val price: Int,
    val status: OrderStatus,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {

    companion object {
        fun from(order: Order) : OrderInfo {
            return OrderInfo(
                id = order.id,
                menu = order.menu,
                price = order.price,
                status = order.status,
                createdAt = order.createdAt,
                updatedAt = order.updatedAt,
            )
        }
    }
}