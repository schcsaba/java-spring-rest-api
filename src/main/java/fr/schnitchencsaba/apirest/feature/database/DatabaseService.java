package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    public List<String> getProductNameList() {
        Query query = entityManager.createNativeQuery("SELECT name from products;");
        return (List<String>) query.getResultList();
    }

    public List<ProductDto> getProductList() {
        Query query = entityManager.createNativeQuery("SELECT id, name, description from products;", Tuple.class);
        List<Tuple> resultList = query.getResultList();
        return resultList.stream().map(ProductDto::new).toList();
    }

    public List<ProductDtoExtended> getProductListExtended() {
        String request = "SELECT p.id, p.name, p.description, p.price, c.name, count(r.review) review_count, AVG(r.rating) average_rating FROM products p INNER JOIN categories c ON p.category_id = c.id INNER JOIN reviews r ON p.id = r.product_id GROUP BY p.id;";
        Query query = entityManager.createNativeQuery(request, Tuple.class);
        List<Tuple> resultList = query.getResultList();
        return resultList.stream().map(ProductDtoExtended::new).toList();
    }

    public List<Product> getProductListFromEntity() {
        return productRepository.findAll();
    }

    public ProductWithPriceDto getOneProduct(Integer id) {
        String request = "SELECT id, name, description, price FROM products WHERE id = :id";
        Query query = entityManager.createNativeQuery(request, Tuple.class).setParameter("id", id);
        Tuple result = (Tuple) query.getSingleResult();
        return new ProductWithPriceDto(result);
    }

    public Product getOneProductEntity(Integer id) {
        return productRepository.findById(id).orElse(null);
    }
}
