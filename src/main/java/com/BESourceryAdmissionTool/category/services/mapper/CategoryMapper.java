package com.BESourceryAdmissionTool.category.services.mapper;

import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.dto.CategoryEditDto;
import com.BESourceryAdmissionTool.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto categoryMap(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreationDate(),
                category.getAuthor().getName()
        );
    }

    public CategoryEditDto categoryEditMap(Category category) {
        return new CategoryEditDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
