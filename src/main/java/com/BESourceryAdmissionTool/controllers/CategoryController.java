package com.BESourceryAdmissionTool.controllers;


import com.BESourceryAdmissionTool.repositories.CategoryRepository;
import com.BESourceryAdmissionTool.model.Category;
import com.BESourceryAdmissionTool.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
public class CategoryController {
    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    @JsonView(Views.CategoriesViews.class)
    public List<Category> GetAll() {
        return categoryRepository.findAll();


    }
}
