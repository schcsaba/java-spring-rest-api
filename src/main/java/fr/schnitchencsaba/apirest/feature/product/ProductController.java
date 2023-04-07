package fr.schnitchencsaba.apirest.feature.product;

import fr.schnitchencsaba.apirest.model.Product;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.insertProduct(product));
        } catch (ConstraintViolationException constraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/category-name")
    public ResponseEntity<Product> insertProduct(@RequestBody ProductRequestWithCategoryName productRequestWithCategoryName) {
        try {
            return ResponseEntity.ok(productService.insertProduct(productRequestWithCategoryName));
        } catch (ConstraintViolationException constraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") Integer productId) {
        productService.deleteProduct(productId);
    }

    @DeleteMapping("")
    public void deleteProduct(@RequestParam("productName") String productName) {
        Product product = productService.findFirstByNameIgnoreCase(productName);
        productService.deleteProduct(product.getId());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("productId") Integer productId) {
        try {
            return ResponseEntity.ok(productService.updateProduct(productId, product));
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ConstraintViolationException constraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
