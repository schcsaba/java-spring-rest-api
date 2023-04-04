package fr.schnitchencsaba.apirest.database;

import fr.schnitchencsaba.apirest.feature.database.DatabaseService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
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
        Query query = entityManager.createNativeQuery("SELECT name from products;", Tuple.class);
        List<Tuple> productNameList1 = ((List<Tuple>) query.getResultList());
        List<Tuple> productNameList2 = databaseService.getProductNameList();
        assert productNameList1 != null;
        assert productNameList2 != null;
        assert productNameList1.size() == productNameList2.size();
        logger.info("The sizes of the product name lists are equal.");
        for (int i = 0; i < productNameList1.size(); i++) {
            String productName1 = (String) productNameList1.get(i).get(0);
            String productName2 = (String) productNameList2.get(i).get(0);
            assertEquals(productName1, productName2);
        }
        logger.info("The names of the products are equal.");
    }

    @Test
    void testGetProductList() {
        Query query = entityManager.createNativeQuery("SELECT id, name, description from products;", Tuple.class);
        List<Tuple> productList1 = ((List<Tuple>) query.getResultList());
        List<Tuple> productList2 = databaseService.getProductList();
        assert productList1 != null;
        assert productList2 != null;
        assert productList1.size() == productList2.size();
        logger.info("The sizes of the product lists are equal.");
        for (int i = 0; i < productList1.size(); i++) {
            int productId1 = (int) productList1.get(i).get(0);
            int productId2 = (int) productList2.get(i).get(0);
            assertEquals(productId1, productId2);
            String productName1 = (String) productList1.get(i).get(1);
            String productName2 = (String) productList2.get(i).get(1);
            assertEquals(productName1, productName2);
            String productDescription1 = (String) productList1.get(i).get(2);
            String productDescription2 = (String) productList2.get(i).get(2);
            assertEquals(productDescription1, productDescription2);
        }
        logger.info("The ids, names and descriptions of the products are equal.");
    }

    @Test
    void testGetProductListExtended() {
        Query query = entityManager.createNativeQuery("SELECT p.id, p.name, p.description, p.price, c.name, count(r.review) review_count, AVG(r.rating) average_rating FROM products p INNER JOIN categories c ON p.category_id = c.id INNER JOIN reviews r ON p.id = r.product_id GROUP BY p.id;", Tuple.class);
        List<Tuple> productList1 = ((List<Tuple>) query.getResultList());
        List<Tuple> productList2 = databaseService.getProductListExtended();
        assert productList1 != null;
        assert productList2 != null;
        assert productList1.size() == productList2.size();
        logger.info("The sizes of the product lists are equal.");
        for (int i = 0; i < productList1.size(); i++) {
            int productId1 = (int) productList1.get(i).get(0);
            int productId2 = (int) productList2.get(i).get(0);
            assertEquals(productId1, productId2);
            String productName1 = (String) productList1.get(i).get(1);
            String productName2 = (String) productList2.get(i).get(1);
            assertEquals(productName1, productName2);
            String productDescription1 = (String) productList1.get(i).get(2);
            String productDescription2 = (String) productList2.get(i).get(2);
            assertEquals(productDescription1, productDescription2);
            int productPrice1 = (int) productList1.get(i).get(3);
            int productPrice2 = (int) productList2.get(i).get(3);
            assertEquals(productPrice1, productPrice2);
            String categoryName1 = Optional.ofNullable((String) productList1.get(i).get(4)).orElse("DEFAULT");
            String categoryName2 = Optional.ofNullable((String) productList2.get(i).get(4)).orElse("DEFAULT");
            assertEquals(categoryName1, categoryName2);
            Long reviewCount1 = (Long) productList1.get(i).get(5);
            Long reviewCount2 = (Long) productList2.get(i).get(5);
            assertEquals(reviewCount1, reviewCount2);
            BigDecimal averageRating1 = (BigDecimal) productList1.get(i).toArray()[6];
            BigDecimal averageRating2 = (BigDecimal) productList2.get(i).toArray()[6];
            assertEquals(averageRating1, averageRating2);
        }
        logger.info("The ids, names, descriptions, prices, category names, review counts and average ratings of the products are equal.");
    }
}
