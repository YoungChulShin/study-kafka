package study.kafka.order.infrastructure.transmitter

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback
import study.kafka.order.domain.model.OrderInfo
import study.kafka.order.domain.transmitter.OrderTransmitter

@Component
class KafkaOrderTransmitter(
    private val orderCreatedKafkaTemplate: KafkaTemplate<String, OrderInfo>,
    @Value("\${transmitter.order.created.topic}") private val topic: String,
): OrderTransmitter {

    override fun transmitOrderCreated(orderInfo: OrderInfo) {
        val result = orderCreatedKafkaTemplate.send(topic, orderInfo.id.toString(), orderInfo)
        result.addCallback(object : ListenableFutureCallback<SendResult<String, OrderInfo>> {
            override fun onSuccess(result: SendResult<String, OrderInfo>?) {
                println("카프카 전송 성공")
            }

            override fun onFailure(ex: Throwable) {
                println("카프카 전송 실패")
            }
        })
    }
}