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

import java.util.List;

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
        assertEquals(productNameList1.size(), productNameList2.size());
        logger.info("The sizes of the product name lists are equal.");
        if (productNameList1.size() == productNameList2.size()) {
            for (int i = 0; i < productNameList1.size(); i++) {
                String productName1 = (String) productNameList1.get(i).get(0);
                String productName2 = (String) productNameList2.get(i).get(0);
                assertEquals(productName1, productName2);
            }
            logger.info("The names of the products are equal.");
        }
    }

    @Test
    void testGetProductList() {
        Query query = entityManager.createNativeQuery("SELECT id, name, description from products;", Tuple.class);
        List<Tuple> productList1 = ((List<Tuple>) query.getResultList());
        List<Tuple> productList2 = databaseService.getProductList();
        assertEquals(productList1.size(), productList2.size());
        logger.info("The sizes of the product lists are equal.");
        if (productList1.size() == productList2.size()) {
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
    }
}
