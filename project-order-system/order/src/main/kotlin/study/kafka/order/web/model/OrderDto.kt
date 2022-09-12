package study.kafka.order.web.model

import study.kafka.order.application.model.OrderInfo

data class CreateOrderRequest(
    val menu: String,
    val price: Int,
)

data class CreateOrderResponse(
    val data: OrderInfo
)