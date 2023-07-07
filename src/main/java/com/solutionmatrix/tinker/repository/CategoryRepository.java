package com.solutionmatrix.tinker.repository;

import com.solutionmatrix.tinker.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@PreAuthorize("hasAuthority('Admin')")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long id);
}
