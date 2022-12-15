package com.BESourceryAdmissionTool.category.controllers;

import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.dto.CategoryEditDto;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
import com.BESourceryAdmissionTool.category.services.CategoryService;
import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
import com.BESourceryAdmissionTool.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public void updateCategory(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication , @AuthenticationPrincipal User user,
                               @PathVariable("id") long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategoryService(id, categoryRequest, user);
    }

    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED, reason = "CREATED")
    public void createCategory(@Valid @RequestBody CategoryRequest categoryRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
                               @AuthenticationPrincipal User user) throws UnauthorizedExeption {
        categoryService.createCategoryService(categoryRequest);
    }
}
