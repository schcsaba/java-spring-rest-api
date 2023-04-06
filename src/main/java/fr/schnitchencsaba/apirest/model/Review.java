package fr.schnitchencsaba.apirest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "review")
    private String review;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "rating")
    private int rating;

    public int getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }
}
