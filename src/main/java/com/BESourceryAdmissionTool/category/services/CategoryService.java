package com.BESourceryAdmissionTool.category.services;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryOption> getCategoriesOptions() {
        return categoryRepository.findAllOptions();
    }
    public CategoryDto  getAllCategories() {
        return new CategoryDto(categoryRepository.findAll());
    }

    public void updateCategoryService(long id, CategoryRequest categoryRequest) {
        Optional<Category> categoryOption = categoryRepository.findById(id);
        if (categoryOption.isEmpty()) {
            throw new CategoryIdNotExistException(id);
        }
        Category category = categoryOption.get();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepository.save(category);
    }

}
