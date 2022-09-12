package study.kafka.order.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import study.kafka.order.domain.Order
import study.kafka.order.domain.OrderRepository

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
}