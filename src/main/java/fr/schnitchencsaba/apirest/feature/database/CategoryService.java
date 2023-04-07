package fr.schnitchencsaba.apirest.feature.database;

import fr.schnitchencsaba.apirest.model.Category;
import fr.schnitchencsaba.apirest.model.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category getOneCategoryById(Integer categoryId) {
        Optional<Category> byId = categoryRepository.findById(categoryId);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Category with ID %d not exist".formatted(categoryId));
        }
        return byId.get();
    }

    public Category findFirstByNameIgnoreCase(String name) {
        return categoryRepository.findFirstByNameIgnoreCase(name);
    }
}
