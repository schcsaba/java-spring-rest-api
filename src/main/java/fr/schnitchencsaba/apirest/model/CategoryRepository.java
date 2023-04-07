package fr.schnitchencsaba.apirest.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    List<Category> findByNameIgnoreCase(String name);
}
