package ru.abtank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abtank.entities.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category ,Integer>, JpaSpecificationExecutor<Category> {

    Optional<Category> findByCategory(String category);
    Optional<Category> findByCategoryLike(String categoryPattern);
}
