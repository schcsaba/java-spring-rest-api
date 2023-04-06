package fr.schnitchencsaba.apirest.feature.product;

import fr.schnitchencsaba.apirest.model.Product;
import fr.schnitchencsaba.apirest.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
