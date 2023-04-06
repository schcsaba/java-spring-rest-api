package fr.schnitchencsaba.apirest.database;

import fr.schnitchencsaba.apirest.feature.database.*;
import fr.schnitchencsaba.apirest.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DatabaseTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    DatabaseService databaseService;

    private final Logger logger = LoggerFactory.getLogger(DatabaseTests.class);

    @Test
    void testGetProductNameList() {
        Query query = entityManager.createNativeQuery("SELECT name from products;");
        List<String> productNameList1 = ((List<String>) query.getResultList());
        List<String> productNameList2 = databaseService.getProductNameList();
        assert productNameList1 != null;
        assert productNameList2 != null;
        assert productNameList1.size() == productNameList2.size();
        logger.info("The sizes of the product name lists are equal.");
        for (int i = 0; i < productNameList1.size(); i++) {
            String productName1 = productNameList1.get(i);
            String productName2 = productNameList2.get(i);
            assertEquals(productName1, productName2);
        }
        logger.info("The names of the products are equal.");
    }

    @Test
    void testGetProductList() {
        Query query = entityManager.createNativeQuery("SELECT id, name, description from products;", Tuple.class);
        List<Tuple> resultList = query.getResultList();
        List<ProductDto> productList1 = resultList.stream().map(ProductDto::new).toList();
        List<ProductDto> productList2 = databaseService.getProductList();
        assert productList1.size() == productList2.size();
        logger.info("The sizes of the product lists are equal.");
        for (int i = 0; i < productList1.size(); i++) {
            int productId1 = productList1.get(i).getId();
            int productId2 = productList2.get(i).getId();
            assertEquals(productId1, productId2);
            String productName1 = productList1.get(i).getName();
            String productName2 = productList2.get(i).getName();
            assertEquals(productName1, productName2);
            String productDescription1 = productList1.get(i).getDescription();
            String productDescription2 = productList2.get(i).getDescription();
            assertEquals(productDescription1, productDescription2);
        }
        logger.info("The ids, names and descriptions of the products are equal.");
    }

    @Test
    void testGetProductListExtended() {
        Query query = entityManager.createNativeQuery("SELECT p.id, p.name, p.description, p.price, c.name, count(r.review) review_count, AVG(r.rating) average_rating FROM products p INNER JOIN categories c ON p.category_id = c.id INNER JOIN reviews r ON p.id = r.product_id GROUP BY p.id;", Tuple.class);
        List<Tuple> resultList = query.getResultList();
        List<ProductDtoExtended> productList1 = resultList.stream().map(ProductDtoExtended::new).toList();
        List<ProductDtoExtended> productList2 = databaseService.getProductListExtended();
        assert productList1.size() == productList2.size();
        logger.info("The sizes of the product lists are equal.");
        for (int i = 0; i < productList1.size(); i++) {
            int productId1 = productList1.get(i).getId();
            int productId2 = productList2.get(i).getId();
            assertEquals(productId1, productId2);
            String productName1 = productList1.get(i).getName();
            String productName2 = productList2.get(i).getName();
            assertEquals(productName1, productName2);
            String productDescription1 = productList1.get(i).getDescription();
            String productDescription2 = productList2.get(i).getDescription();
            assertEquals(productDescription1, productDescription2);
            int productPrice1 = productList1.get(i).getPrice();
            int productPrice2 = productList2.get(i).getPrice();
            assertEquals(productPrice1, productPrice2);
            String categoryName1 = Optional.ofNullable(productList1.get(i).getCategoryName()).orElse("DEFAULT");
            String categoryName2 = Optional.ofNullable(productList2.get(i).getCategoryName()).orElse("DEFAULT");
            assertEquals(categoryName1, categoryName2);
            Long reviewCount1 = productList1.get(i).getReviewCount();
            Long reviewCount2 = productList2.get(i).getReviewCount();
            assertEquals(reviewCount1, reviewCount2);
            BigDecimal averageRating1 = productList1.get(i).getAverageRating();
            BigDecimal averageRating2 = productList2.get(i).getAverageRating();
            assertEquals(averageRating1, averageRating2);
        }
        logger.info("The ids, names, descriptions, prices, category names, review counts and average ratings of the products are equal.");
    }

    @Test
    void testGetProductListFromEntity() {
        Query query = entityManager.createNativeQuery("SELECT id, name, description, price from products;", Tuple.class);
        List<Tuple> resultList = query.getResultList();
        List<ProductWithPriceDto> productsWithPriceDto = resultList.stream().map(ProductWithPriceDto::new).toList();
        List<Product> products = databaseService.getProductListFromEntity();
        assert productsWithPriceDto.size() == products.size();
        logger.info("The sizes of the product lists are equal.");
        for (int i = 0; i < productsWithPriceDto.size(); i++) {
            Assertions.assertTrue(testEquality(products.get(i), productsWithPriceDto.get(i)));
        }
        logger.info("The ids, names, descriptions and prices of the products are equal.");
    }

    @Test
    void testGetOneProduct() {
        String request = "SELECT id, name, description, price FROM products WHERE id = :id";
        Query query = entityManager.createNativeQuery(request, Tuple.class).setParameter("id", 1);
        Tuple result = (Tuple) query.getSingleResult();
        ProductWithPriceDto p1 = new ProductWithPriceDto(result);
        ProductWithPriceDto p2 = databaseService.getOneProduct(1);
        assert p1.equals(p2);
        logger.info("The two products are equal.");
    }

    @Test
    void testGetOneProductEntity() {
        String request = "SELECT id, name, description, price FROM products WHERE id = :id";
        Query query = entityManager.createNativeQuery(request, Tuple.class).setParameter("id", 1);
        Tuple result = (Tuple) query.getSingleResult();
        ProductWithPriceDto p1 = new ProductWithPriceDto(result);
        Product p2 = databaseService.getOneProductEntity(1);
        Assertions.assertTrue(testEquality(p2, p1));
        logger.info("The two products are equal.");
    }

    boolean testEquality(Product product, ProductWithPriceDto productWithPriceDto) {
        return product.getId() == productWithPriceDto.getId() && Objects.equals(product.getName(), productWithPriceDto.getName()) && Objects.equals(product.getDescription(), productWithPriceDto.getDescription()) && Objects.equals(product.getPrice(), productWithPriceDto.getPrice());
    }
}
