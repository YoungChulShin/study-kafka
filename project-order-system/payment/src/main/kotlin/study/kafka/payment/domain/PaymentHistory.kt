package study.kafka.payment.domain

import javax.persistence.*

@Entity
@Table(name = "payment_histories")
class PaymentHistory(
    val orderId: Long,
    val price: Int,
    val menu: String,
    val result: Boolean,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}