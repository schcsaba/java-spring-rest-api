package fr.schnitchencsaba.apirest.feature.database;

import fr.schnitchencsaba.apirest.model.Product;
import fr.schnitchencsaba.apirest.model.ProductRepository;
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

    public List<Product> getProductEntityList() {
        return productRepository.findAll();
    }

    public ProductWithPriceDto getOneProductById(Integer id) {
        String request = "SELECT id, name, description, price FROM products WHERE id = :id";
        Query query = entityManager.createNativeQuery(request, Tuple.class).setParameter("id", id);
        Tuple result = (Tuple) query.getSingleResult();
        return new ProductWithPriceDto(result);
    }

    public Product getOneProductEntityById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<ProductWithPriceDto> getProductListByName(String name) {
        String request = "SELECT id, name, description, price FROM products WHERE name LIKE :name";
        Query query = entityManager.createNativeQuery(request, Tuple.class).setParameter("name", "%" + name + "%");
        List<Tuple> resultList = (List<Tuple>) query.getResultList();
        return resultList.stream().map(ProductWithPriceDto::new).toList();
    }

    public List<Product> getProductEntityListByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<ProductWithPriceDto> getProductListByCategoryName(String categoryName) {
        String request = "SELECT p.id, p.name, p.description, p.price FROM products p INNER JOIN categories c ON p.category_id = c.id WHERE c.name LIKE :categoryName";
        Query query = entityManager.createNativeQuery(request, Tuple.class).setParameter("categoryName", "%" + categoryName + "%");
        List<Tuple> resultList = (List<Tuple>) query.getResultList();
        return resultList.stream().map(ProductWithPriceDto::new).toList();
    }

    public List<Product> getProductEntityListByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }
}
