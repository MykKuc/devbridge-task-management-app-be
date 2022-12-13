package com.BESourceryAdmissionTool.category.controllers;

import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.dto.CategoryEditDto;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
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

    @GetMapping("/options")
    public List<CategoryOption> GetCategoriesOptions() {
        return categoryService.getCategoriesOptions();
    }

    @GetMapping("{id}")
    public CategoryEditDto getCategory(@PathVariable("id") long id){
        return categoryService.getCategory(id);
    }

    @GetMapping
    public List<CategoryDto> GetAllCategories() {
        return categoryService.getAllCategories();
    }


    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void updateCategory(@PathVariable("id") long id, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategoryService(id, categoryRequest);
    }

    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED, reason = "CREATED")
    public void createCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.createCategoryService(categoryRequest);
    }
}
