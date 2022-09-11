package study.kafka.order.domain

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    val menu: String,
    val price: Int,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var status: OrderStatus = OrderStatus.CREATING
}