package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.Tuple;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class ProductDtoExtended extends ProductWithPriceDto {

    private final String categoryName;
    private final Long reviewCount;
    private final BigDecimal averageRating;

    public ProductDtoExtended(Integer id, String name, String description, Integer price, String categoryName, Long reviewCount, BigDecimal averageRating) {
        super(id, name, description, price);
        this.categoryName = categoryName;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
    }

    public ProductDtoExtended(Tuple productData) {
        super(productData);
        this.categoryName = Optional.ofNullable((String) productData.get(4)).orElse("DEFAULT");
        this.reviewCount = (Long) productData.get(5);
        this.averageRating = (BigDecimal) productData.toArray()[6];
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductDtoExtended that = (ProductDtoExtended) o;
        return Objects.equals(categoryName, that.categoryName) && Objects.equals(reviewCount, that.reviewCount) && Objects.equals(averageRating, that.averageRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoryName, reviewCount, averageRating);
    }
}
