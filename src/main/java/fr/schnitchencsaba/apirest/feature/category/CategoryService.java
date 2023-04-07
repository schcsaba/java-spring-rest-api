package fr.schnitchencsaba.apirest.feature.category;

import fr.schnitchencsaba.apirest.model.Category;
import fr.schnitchencsaba.apirest.model.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getOneCategoryById(Integer categoryId) {
        Optional<Category> byId = categoryRepository.findById(categoryId);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Category with ID %d not exist".formatted(categoryId));
        }
        return byId.get();
    }

    public Category findOrCreateCategoryByName(String name) {
        Optional<Category> byName = categoryRepository.findByName(name);

        if (byName.isPresent()) {
            return byName.get();
        } else {
            Category category = new Category();
            category.setName(name);
            return categoryRepository.save(category);
        }
    }
}
