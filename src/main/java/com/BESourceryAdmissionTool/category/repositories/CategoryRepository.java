package com.BESourceryAdmissionTool.category.repositories;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c")
    List<CategoryOption> findAllOptions();
}
