package com.BESourceryAdmissionTool.category.controllers;

import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
import com.BESourceryAdmissionTool.category.services.CategoryService;
import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/options")
    public List<CategoryOption> GetCategoriesOptions() {
        return categoryService.getCategoriesOptions();
    }

    @GetMapping
    public CategoryDto GetAllCategories() {
        return categoryService.getAllCategories();
    }


    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    public void updateCategory(@PathVariable("id") long id, @RequestBody CategoryRequest categoryRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
                               @AuthenticationPrincipal User user) throws UnauthorizedExeption {
        userService.checkUser(user,authentication);
        categoryService.updateCategoryService(id, categoryRequest);
    }

    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED, reason = "CREATED")
    public void createCategory(@RequestBody CategoryRequest categoryRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
                               @AuthenticationPrincipal User user) throws UnauthorizedExeption {
        userService.checkUser(user,authentication);
        categoryService.createCategoryService(categoryRequest);
    }
}
