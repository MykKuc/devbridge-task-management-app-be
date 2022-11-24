package com.BESourceryAdmissionTool.category.services;

import com.BESourceryAdmissionTool.category.entity.CategoryRequest;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryOption> getAllCategories() {
        return categoryRepository.findAllOptions();
    }


    public void updateCategoryService(Long id, CategoryRequest categoryRequest) throws CategoryIdNotExistException {
        if(categoryRepository.findById(id).isEmpty()){
            throw new CategoryIdNotExistException(id);
        }
        categoryRepository.save(categoryRequest);
    }

}
