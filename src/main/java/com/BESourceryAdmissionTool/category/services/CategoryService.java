package com.BESourceryAdmissionTool.category.services;

import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Transactional
    public void createCategoryService(String name, String description){
        long authorId = 1; // TODO: should be taken from currently logged in user's id when authentication is created
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

        //categoryRepository.save(category);
        //categoryRespoistory.insertCategory(category);
        categoryRepository.insertCategory(category.getName(), category.getDescription(), category.getCreationDate(), category.getAuthorId());
    }
}
