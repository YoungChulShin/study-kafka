package study.kafka.order.application.handler

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import study.kafka.order.domain.Order
import study.kafka.order.domain.OrderRepository
import study.kafka.order.domain.events.OrderCreated
import java.util.*

class OrderEventHandlerTest {

    @Test
    fun if_order_created_send_order_info_to_order_transmitter() {
        // given
        val testOrderTransmitter = TestOrderTransmitter()
        val orderRepositoryMock = Mockito.mock(OrderRepository::class.java)
        val sut = OrderEventHandler(testOrderTransmitter, orderRepositoryMock)

        val orderCreatedEvent = OrderCreated(1L, "삼각김밥", 1000)
        val order = Order("삼각김밥", 1000)
        Mockito.`when`(orderRepositoryMock.findById(1L)).thenReturn(Optional.of(order))

        // when
        sut.handleOrderCreatedEvent(orderCreatedEvent)

        // then
        Assertions.assertThat(testOrderTransmitter.orderInfos.size).isEqualTo(1)
        Assertions.assertThat(testOrderTransmitter.orderInfos[0].menu).isEqualTo("삼각김밥")
        Assertions.assertThat(testOrderTransmitter.orderInfos[0].price).isEqualTo(1000)
    }
}