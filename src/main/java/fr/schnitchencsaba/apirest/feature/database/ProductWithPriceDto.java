package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.Tuple;

import java.util.Objects;

public class ProductWithPriceDto extends ProductDto {

    private final Integer price;
    public ProductWithPriceDto(Integer id, String name, String description, Integer price) {
        super(id, name, description);
        this.price = price;
    }

    public ProductWithPriceDto(Tuple productData) {
        super(productData);
        this.price = (Integer) productData.get(3);
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductWithPriceDto that = (ProductWithPriceDto) o;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price);
    }
}
