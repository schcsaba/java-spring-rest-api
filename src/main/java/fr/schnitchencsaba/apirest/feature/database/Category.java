package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "vat")
    private int vat;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVat() {
        return vat;
    }

    public List<Product> getProducts() {
        return products;
    }
}
