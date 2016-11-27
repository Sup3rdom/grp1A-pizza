package models
import java.util.Date
/**
  * Order entity.
  * @param id database id of the order.
  * @param custID database id of the customer.
  * @param orderItem item of order
  * @param costs costs of order.
  */
case class Order(var id: Long, var custID: Long, var date: Date, var orderItems: List[OrderItem], var costs: Int)