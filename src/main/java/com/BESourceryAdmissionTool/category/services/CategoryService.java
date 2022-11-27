package com.BESourceryAdmissionTool.category.services;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
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


    // Method to update the Category.
    public void updateCategoryService(String name, String description, Long id){
        categoryRepository.updateCategoryDescription(description, id);
        categoryRepository.updateCategoryName(name, id);
    }

    //Method to check if the id of the category is present.
    public boolean checkIfCategoryIdNotExist(Long id){
       return categoryRepository.findById(id).isEmpty();
    }

    public long getCurrentUserId(){
        // TODO: should be taken from currently logged in user's id when authentication is created
        return 1;
    }
    public void createCategoryService(Category category){
        categoryRepository.save(category);
    }
}
