package study.kafka.order.domain.events

data class OrderCreated(
    val id: Long?,
    val menu: String,
    val price: Int,
) : DomainEvent