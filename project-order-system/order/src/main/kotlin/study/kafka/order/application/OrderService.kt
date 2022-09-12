package study.kafka.order.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.kafka.order.application.model.OrderInfo
import study.kafka.order.domain.Order
import study.kafka.order.domain.OrderRepository

@Service
class OrderService (
    val orderRepository: OrderRepository,
) {

    @Transactional
    fun createOrder(menu: String, price: Int): OrderInfo {
        return orderRepository.save(Order(menu = menu, price = price))
            .run(OrderInfo::from)
    }

    @Transactional
    fun paymentCompleted(id: Long) {
        val order = orderRepository.findById(id)
            .orElseThrow { IllegalArgumentException("오더 정보를 찾을 수 없습니다") }
        order.paymentCompleted()
    }

    @Transactional
    fun payment(id: Long) {
        val order = orderRepository.findById(id)
            .orElseThrow { IllegalArgumentException("오더 정보를 찾을 수 없습니다") }
        order.cancelled()
    }
}