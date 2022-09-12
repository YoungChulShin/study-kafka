package study.kafka.order.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test

class OrderTest {

    @Test
    fun order_creating() {
        // given
        val menu = "김치찌개"
        val price = 8000

        // when
        val order = Order(menu = menu, price = price)

        // then
        Assertions.assertThat(order.menu).isEqualTo(menu)
        Assertions.assertThat(order.price).isEqualTo(price)
        Assertions.assertThat(order.status).isEqualTo(OrderStatus.CREATED)
    }

    @Test
    fun order_created() {
        // given
        val order = Order(menu = "김치찌개", price = 8000)

        // when
        order.paymentCompleted()

        // then
        Assertions.assertThat(order.status).isEqualTo(OrderStatus.PAYMENT_COMPLETED)
    }

    @Test
    fun order_can_not_created_if_status_is_cancelled() {
        // given
        val order = Order(menu = "김치찌개", price = 8000)
        order.cancelled()

        // when
        val catchThrowable = catchThrowable { order.paymentCompleted() }


        // then
        Assertions.assertThat(catchThrowable).isNotNull
        Assertions.assertThat(catchThrowable.message).isEqualTo("이미 취소된 주문입니다")
    }

    @Test
    fun order_cancelled() {
        // given
        val order = Order(menu = "김치찌개", price = 8000)

        // when
        order.cancelled()

        // then
        Assertions.assertThat(order.status).isEqualTo(OrderStatus.CANCELLED)
    }
}