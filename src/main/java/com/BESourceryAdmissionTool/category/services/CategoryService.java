package com.BESourceryAdmissionTool.category.services;

import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.dto.CategoryEditDto;
import com.BESourceryAdmissionTool.category.exceptions.CategoryAlreadyExistsException;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.exceptions.CategoryNotFoundException;
import com.BESourceryAdmissionTool.category.exceptions.CurrentUserIdNotEqualAuthorIdException;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
import com.BESourceryAdmissionTool.category.services.mapper.CategoryMapper;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryOption> getCategoriesOptions() {
        return categoryRepository.findAllOptions();
    }

    public CategoryEditDto getCategory(long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new CategoryNotFoundException(id);
        }
        Category category = categoryOptional.get();
        return categoryMapper.categoryEditMap(category);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::categoryMap)
                .collect(Collectors.toList());
    }

    public void updateCategoryService(long id, CategoryRequest categoryRequest, User user) {
        Optional<Category> categoryOption = categoryRepository.findById(id);
        if (categoryOption.isEmpty()) {
            throw new CategoryIdNotExistException(id);
        }

        long currentUserId = user.getId();
        long authorOfCategoryId = categoryOption.get().getAuthor().getId();

        if(currentUserId != authorOfCategoryId){
            throw new CurrentUserIdNotEqualAuthorIdException("Current user " + user.getName() + " is not the author. " + categoryOption.get().getAuthor().getName() + " is the author.");
        }

        Category category = categoryOption.get();
        Optional<Category> sameName = categoryRepository.findCategoryByName(categoryRequest.getName());
        if (sameName.isPresent()) {
            Category sameNameCategory = sameName.get();

            if (sameNameCategory.getId() != category.getId()) {
                throw new CategoryAlreadyExistsException(categoryRequest.getName());
            }
        }

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepository.save(category);
    }

    @Transactional
    public void createCategoryService(CategoryRequest categoryRequest) {
        Optional<Category> sameName = categoryRepository.findCategoryByName(categoryRequest.getName());
        if (sameName.isPresent()) {
            throw new CategoryAlreadyExistsException(categoryRequest.getName());
        }

        long authorId = 1; // TODO: should be taken from currently logged in user's id when authentication is created
        Date currentDate = new Date();

        Optional<User> userOptional = userRepository.findById(authorId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(authorId);
        }
        User author = userOptional.get();

        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .author(author)
                .creationDate(currentDate)
                .build();

        categoryRepository.save(category);
    }
}
