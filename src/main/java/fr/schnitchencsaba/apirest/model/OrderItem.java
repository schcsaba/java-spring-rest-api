package fr.schnitchencsaba.apirest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false, insertable = false)
    private Product product;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "order_id", updatable = false, insertable = false)
    private Order order;

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getAmount() {
        return amount;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id.equals(orderItem.id) && Objects.equals(productId, orderItem.productId) && amount.equals(orderItem.amount) && Objects.equals(orderId, orderItem.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, amount, orderId);
    }
}
