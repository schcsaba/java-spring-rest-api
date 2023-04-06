package fr.schnitchencsaba.apirest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "review")
    private String review;

    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "product_id", updatable = false, insertable = false)
    private Product product;

    @Column(name = "rating")
    private Integer rating;

    public Integer getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getRating() {
        return rating;
    }

    public Integer getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return id.equals(review1.id) && review.equals(review1.review) && Objects.equals(productId, review1.productId) && rating.equals(review1.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, review, productId, rating);
    }
}
