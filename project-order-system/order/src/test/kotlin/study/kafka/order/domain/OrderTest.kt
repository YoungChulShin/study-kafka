package study.kafka.order.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class OrderTest {

    @Test
    fun order_created_successfully() {
        // given
        val menu = "김치찌개"
        val price = 8000

        // when
        val order = Order(menu = menu, price = price)

        // then
        Assertions.assertThat(order.menu).isEqualTo(menu)
        Assertions.assertThat(order.price).isEqualTo(price)
        Assertions.assertThat(order.status).isEqualTo(OrderStatus.CREATING)
    }
}