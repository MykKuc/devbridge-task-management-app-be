package com.BESourceryAdmissionTool.category.controllers;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryOption> GetAllCategories() {
        return categoryService.getAllCategories();
    }


    // Update the category.
    @PutMapping("/updatecategory/{id}")
    public String updateCategory(@PathVariable("id") Long id, @RequestBody Category category){
        if(categoryService.checkIfCategoryIdNotExist(id)){
            return "Such an id of the category does not exist. Please enter valid id.";
        }
        String categoryName = category.getName();
        String categoryDescription = category.getDescription();

        categoryService.updateCategoryService(categoryName,categoryDescription,id);

        return "Category has been updated.";
    }
}
