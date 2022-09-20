package study.kafka.order.presentation.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import study.kafka.order.application.OrderService
import study.kafka.order.presentation.web.model.CreateOrderRequest
import study.kafka.order.presentation.web.model.CreateOrderResponse
import javax.validation.Valid

@RestController
class OrderController(
    val orderService: OrderService
) {

    @PostMapping("/api/v1/orders")
    fun crateOrder(@RequestBody @Valid request: CreateOrderRequest): CreateOrderResponse {
        val orderInfo = orderService.createOrder(menu = request.menu, price = request.price)
        return CreateOrderResponse(orderInfo)
    }
}