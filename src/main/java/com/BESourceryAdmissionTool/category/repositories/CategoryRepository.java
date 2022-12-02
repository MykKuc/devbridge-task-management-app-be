package com.BESourceryAdmissionTool.category.repositories;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c")
    List<CategoryOption> findAllOptions();

    @Modifying
    @Query(value = "INSERT INTO category (name, description, creation_date, author_id) VALUES(:name,:description,:creationDate,:authorId)", nativeQuery = true)
    void insertCategory(@Param("name")String name, @Param("description")String description, @Param("creationDate") Date creationDate, @Param("authorId")long authorId);
}
