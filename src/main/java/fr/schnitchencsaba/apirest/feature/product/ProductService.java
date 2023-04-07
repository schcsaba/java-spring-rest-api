package fr.schnitchencsaba.apirest.feature.product;

import fr.schnitchencsaba.apirest.feature.category.CategoryService;
import fr.schnitchencsaba.apirest.model.Category;
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

    @Autowired
    private CategoryService categoryService;

    public Product insertProduct(Product product) throws ConstraintViolationException {
        List<Product> byName = productRepository.findByNameContainingIgnoreCase(product.getName());
        if (byName.size() > 0) {
            throw new ConstraintViolationException("A product with the name " + product.getName() + " already exists", new SQLException(), "product::name INDEX UNIQUE");
        }
        return productRepository.save(product);
    }

    public Product insertProduct(ProductRequestWithCategoryName productRequestWithCategoryName) {
        List<Product> byName = productRepository.findByNameContainingIgnoreCase(productRequestWithCategoryName.getName());
        if (byName.size() > 0) {
            throw new ConstraintViolationException("A product with the name " + productRequestWithCategoryName.getName() + " already exists", new SQLException(), "product::name INDEX UNIQUE");
        }
        Category category = categoryService.findOrCreateCategoryByName(productRequestWithCategoryName.getCategoryName());
        Product product = new Product();
        product.setName(productRequestWithCategoryName.getName());
        product.setDescription(productRequestWithCategoryName.getDescription());
        product.setPrice(productRequestWithCategoryName.getPrice());
        product.setCategoryId(category.getId());
        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) throws EntityNotFoundException {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product with ID %d does not exist".formatted(productId));
        }
        productRepository.deleteById(productId);
    }

    public Product findFirstByNameIgnoreCase(String name) throws EntityNotFoundException {
        Optional<Product> byName = productRepository.findFirstByNameIgnoreCase(name);
        if (byName.isEmpty()) {
            throw new EntityNotFoundException("Product with name %s does not exist".formatted(name));
        }
        return byName.get();
    }

    public Product updateProduct(Integer productId, Product product) throws EntityNotFoundException, ConstraintViolationException {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product with ID %d does not exist".formatted(productId));
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

    public Product getOneProductById(Integer productId) throws EntityNotFoundException {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Product with ID %d does not exist".formatted(productId));
        }
        return byId.get();
    }
}
