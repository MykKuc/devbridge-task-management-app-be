package com.BESourceryAdmissionTool.category.repositories;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c")
    List<CategoryOption> findAllOptions();

    // Update Category Name.
    @Modifying
    @Query(value = "UPDATE category SET name = :name WHERE id = :id ",nativeQuery = true)
    void updateCategoryName(@Param("name") String name, @Param("id") Long id);

    // Update Category Description.
    @Modifying
    @Query(value = "UPDATE category SET description = :description WHERE id = :id ",nativeQuery = true)
    void updateCategoryDescription(@Param("description") String description, @Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO category (name, description, creation_date, author_id) VALUES(:name,:description,:creationDate,:authorId)", nativeQuery = true)
    void insertCategory(@Param("name")String name, @Param("description")String description, @Param("creationDate")Date creationDate, @Param("authorId")long authorId);
}
