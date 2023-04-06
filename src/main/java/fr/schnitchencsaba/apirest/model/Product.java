package fr.schnitchencsaba.apirest.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", updatable = false, insertable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && name.equals(product.name) && description.equals(product.description) && price.equals(product.price) && Objects.equals(categoryId, product.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, categoryId);
    }
}
