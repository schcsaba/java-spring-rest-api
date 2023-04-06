package fr.schnitchencsaba.apirest.feature.product;

import fr.schnitchencsaba.apirest.model.Product;
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
        ResponseEntity.status(HttpStatus.CONFLICT).body("A product with this name already exists.");
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
}
