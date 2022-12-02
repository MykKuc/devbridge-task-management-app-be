package com.BESourceryAdmissionTool.category.controllers;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.services.CategoryService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
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

    @PostMapping
    public void createCategory(String name, String description){
        long authorId = categoryService.getCurrentUserId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate=new Date();
        try{
            currentDate = formatter.parse(formatter.format(currentDate));
        }
        catch (ParseException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        Category category = new Category();

        category.setName(name);
        category.setDescription(description);
        category.setAuthorId(authorId);
        category.setCreationDate(currentDate);

        categoryService.createCategoryService(category);
    }
}
