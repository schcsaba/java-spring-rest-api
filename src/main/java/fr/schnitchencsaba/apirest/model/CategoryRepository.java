package fr.schnitchencsaba.apirest.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findFirstByNameIgnoreCase(String name);
}
