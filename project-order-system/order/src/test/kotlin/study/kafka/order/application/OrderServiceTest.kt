package study.kafka.order.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import study.kafka.order.domain.Order
import study.kafka.order.domain.OrderRepository
import study.kafka.order.domain.OrderStatus
import java.util.*

class OrderServiceTest {

    @Test
    fun if_order_created_event_will_be_published() {
        // given
        val orderRepositoryMock = Mockito.mock(OrderRepository::class.java)
        val eventPublisher = TestEventPublisher()
        val sut = OrderService(orderRepositoryMock, eventPublisher)

        val order = Order("삼각김밥", 1000)
        Mockito.`when`(orderRepositoryMock.save(order)).thenReturn(order)

        // when
        sut.createOrder("삼각김밥", 1000)

        // then
        Assertions.assertThat(eventPublisher.events.size).isEqualTo(1)
    }

    @Test
    fun if_payment_success_update_order_status_to_completed() {
        // given
        val orderRepositoryMock = Mockito.mock(OrderRepository::class.java)
        val eventPublisher = TestEventPublisher()
        val sut = OrderService(orderRepositoryMock, eventPublisher)

        val orderId = 1L
        val order = Order("삼각김밥", 1000)
        Mockito.`when`(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order))

        // when
        sut.updatePaymentResult(orderId, true)

        // then
        Assertions.assertThat(order.status).isEqualTo(OrderStatus.PAYMENT_COMPLETED)
    }

    @Test
    fun if_payment_fail_update_order_status_to_cancelled() {
        // given
        val orderRepositoryMock = Mockito.mock(OrderRepository::class.java)
        val eventPublisher = TestEventPublisher()
        val sut = OrderService(orderRepositoryMock, eventPublisher)

        val orderId = 1L
        val order = Order("삼각김밥", 1000)
        Mockito.`when`(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order))

        // when
        sut.updatePaymentResult(orderId, false)

        // then
        Assertions.assertThat(order.status).isEqualTo(OrderStatus.CANCELLED)
    }
}