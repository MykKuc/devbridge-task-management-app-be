package com.BESourceryAdmissionTool.category.controllers;

import com.BESourceryAdmissionTool.category.entity.CategoryRequest;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @PutMapping("/updatecategory/{id}")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public String updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest categoryRequest) throws CategoryIdNotExistException {

        categoryService.updateCategoryService(id,categoryRequest);
        return "Category has been updated.";
    }
}
