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
    public Product insertProduct(@RequestBody Product product) {
        // GET produit.name == "table"
        // SI listProduits IS NOT EMPTY
        ResponseEntity.status(HttpStatus.CONFLICT).body("A product with this name already exists.");
        //
        return productService.insertProduct(product);
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
        } catch (ConstraintViolationException constraintViolationException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
