package study.kafka.order.presentation.web.model

import study.kafka.order.domain.model.OrderInfo

data class CreateOrderRequest(
    val menu: String,
    val price: Int,
)

data class CreateOrderResponse(
    val data: OrderInfo
)