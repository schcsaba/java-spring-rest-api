package fr.schnitchencsaba.apirest.feature.category;

import fr.schnitchencsaba.apirest.model.Category;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<Category> insertCategory(@RequestBody Category category) {
        try {
            return ResponseEntity.ok(categoryService.insertCategory(category));
        } catch (ConstraintViolationException constraintViolationException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
