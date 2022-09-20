package study.kafka.order.application

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.kafka.order.domain.model.OrderInfo
import study.kafka.order.domain.Order
import study.kafka.order.domain.OrderRepository
import study.kafka.order.domain.events.OrderCreated

@Service
class OrderService (
    val orderRepository: OrderRepository,
    val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun createOrder(menu: String, price: Int): OrderInfo {
        val order = orderRepository.save(Order(menu = menu, price = price))
        eventPublisher.publishEvent(
            OrderCreated(id = order.id, menu = order.menu, price = order.price))

        return OrderInfo.from(order = order)
    }

    @Transactional
    fun updatePaymentResult(id: Long, success: Boolean) {
        val order = orderRepository.findById(id)
            .orElseThrow { IllegalArgumentException("오더 정보를 찾을 수 없습니다") }

        if (success) {
            order.paymentCompleted()
            println("결제 결과 업데이트 - 성공");
        } else {
            order.cancelled()
            println("결제 결과 업데이트 - 취소");
        }
    }
}