package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public Order getOrder() {
        return order;
    }
}
