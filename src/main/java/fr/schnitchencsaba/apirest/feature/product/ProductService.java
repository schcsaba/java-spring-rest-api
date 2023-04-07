package fr.schnitchencsaba.apirest.feature.product;

import fr.schnitchencsaba.apirest.model.Product;
import fr.schnitchencsaba.apirest.model.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    public Product findFirstByNameIgnoreCase(String name) {
        return productRepository.findFirstByNameIgnoreCase(name);
    }

    public Product updateProduct(Integer productId, Product product) throws EntityNotFoundException, ConstraintViolationException {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product with ID %d not exist".formatted(productId));
        }
        List<Product> byName = productRepository.findByNameContainingIgnoreCase(product.getName()).stream().filter(p -> !Objects.equals(p.getId(), productId)).toList();
        if (byName.size() > 0) {
            throw new ConstraintViolationException("A product with the name " + product.getName() + " already exists", new SQLException(), "product::name INDEX UNIQUE");
        }
        Product foundProduct = byId.get();
        foundProduct.setName(product.getName());
        foundProduct.setCategoryId(product.getCategoryId());
        return productRepository.save(foundProduct);
    }

    public Product getOneProductById(Integer productId) {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product with ID %d not exist".formatted(productId));
        }
        return byId.get();
    }
}
