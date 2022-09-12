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

    var status: OrderStatus = OrderStatus.CREATED

    fun paymentCompleted() {
        if (this.status == OrderStatus.CANCELLED) {
            throw IllegalArgumentException("이미 취소된 주문입니다")
        }

        this.status = OrderStatus.PAYMENT_COMPLETED
    }

    fun cancelled() {
        if (this.status == OrderStatus.PAYMENT_COMPLETED) {
            throw IllegalArgumentException("이미 생성된 주문입니다")
        }

        this.status = OrderStatus.CANCELLED
    }
}