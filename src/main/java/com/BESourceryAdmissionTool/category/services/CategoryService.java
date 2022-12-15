package com.BESourceryAdmissionTool.category.services;

import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.dto.CategoryEditDto;
import com.BESourceryAdmissionTool.category.exceptions.CategoryAlreadyExistsException;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
<<<<<<< HEAD
import com.BESourceryAdmissionTool.category.exceptions.CurrentUserIdNotEqualAuthorIdException;
=======
import com.BESourceryAdmissionTool.category.exceptions.CategoryNotFoundException;
>>>>>>> master
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
<<<<<<< HEAD
import com.BESourceryAdmissionTool.task.exceptions.UserNotEqualTaskAuthorException;
import com.BESourceryAdmissionTool.task.exceptions.UserNotLoggedInException;
=======
import com.BESourceryAdmissionTool.category.services.mapper.CategoryMapper;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
>>>>>>> master
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

    public void updateCategoryService(long id, CategoryRequest categoryRequest) {
        Optional<Category> categoryOption = categoryRepository.findById(id);
        if (categoryOption.isEmpty()) {
            throw new CategoryIdNotExistException(id);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        Optional<User> currentUser = userRepository.findByEmail(currentPrincipalEmail);
        if(currentUser.isEmpty()){
            throw new UserNotLoggedInException("No User is logged in at the moment.");
        }

        long idOfCurrentUser = currentUser.get().getId();

        long categoryAuthorId = categoryOption.get().getAuthorId();

        if(idOfCurrentUser != categoryAuthorId){
            throw new CurrentUserIdNotEqualAuthorIdException("Modification of category not allowed. You are not the author.");
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
