package com.BESourceryAdmissionTool.category.services;

import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.exceptions.CurrentUserIdNotEqualAuthorIdException;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.projection.CategoryOption;
import com.BESourceryAdmissionTool.category.dto.CategoryDto;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import com.BESourceryAdmissionTool.category.requests.CategoryRequest;
import com.BESourceryAdmissionTool.task.exceptions.UserNotEqualTaskAuthorException;
import com.BESourceryAdmissionTool.task.exceptions.UserNotLoggedInException;
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

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<CategoryOption> getCategoriesOptions() {
        return categoryRepository.findAllOptions();
    }
    public CategoryDto  getAllCategories() {
        return new CategoryDto(categoryRepository.findAll());
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
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        categoryRepository.save(category);
    }
    @Transactional
    public void createCategoryService(CategoryRequest categoryRequest){
        long authorId = 1; // TODO: should be taken from currently logged in user's id when authentication is created
        Date currentDate=new Date();


        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .authorId(authorId)
                .creationDate(currentDate)
                .build();

        categoryRepository.save(category);
    }
}
