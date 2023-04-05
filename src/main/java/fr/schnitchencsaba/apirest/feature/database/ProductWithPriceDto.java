package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.Tuple;

import java.util.Objects;

public class ProductWithPriceDto extends ProductDto {

    private final int price;
    public ProductWithPriceDto(int id, String name, String description, int price) {
        super(id, name, description);
        this.price = price;
    }

    public ProductWithPriceDto(Tuple productData) {
        super(productData);
        this.price = (int) productData.get(3);
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductWithPriceDto that = (ProductWithPriceDto) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price);
    }
}
