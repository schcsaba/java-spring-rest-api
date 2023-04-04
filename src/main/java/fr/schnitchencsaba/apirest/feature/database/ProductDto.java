package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.Tuple;

import java.util.Objects;

public class ProductDto {

    private final int id;
    private final String name;
    private final String description;

    public ProductDto(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ProductDto(Tuple productData) {
        this.id = (int) productData.get(0);
        this.name = (String) productData.get(1);
        this.description = (String) productData.get(2);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
