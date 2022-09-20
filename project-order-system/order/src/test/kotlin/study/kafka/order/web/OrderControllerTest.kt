package study.kafka.order.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import study.kafka.order.application.OrderService
import study.kafka.order.domain.model.OrderInfo
import study.kafka.order.domain.OrderStatus
import study.kafka.order.presentation.web.OrderController
import study.kafka.order.presentation.web.model.CreateOrderRequest

@WebMvcTest(OrderController::class)
class OrderControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockBean
    private lateinit var orderService: OrderService

    @Test
    fun create_order() {
        // given
        val menu = "삼각김밥"
        val price = 1000
        val request = CreateOrderRequest(menu, price)

        Mockito
            .`when`(orderService.createOrder(menu, price))
            .thenReturn(OrderInfo(null, menu, price, OrderStatus.CREATED, null, null))

        val objectMapper = ObjectMapper()

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
            .post("/api/v1/orders")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.menu").value(menu))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.price").value(price))
    }
}