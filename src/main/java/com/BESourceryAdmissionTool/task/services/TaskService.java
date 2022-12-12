package com.BESourceryAdmissionTool.task.services;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.answer.repositories.AnswerRepository;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import com.BESourceryAdmissionTool.task.dto.FullTaskDto;
import com.BESourceryAdmissionTool.task.dto.TaskDto;
import com.BESourceryAdmissionTool.task.exceptions.TaskNameAlreadyExistsException;
import com.BESourceryAdmissionTool.task.exceptions.TaskNotFoundException;
import com.BESourceryAdmissionTool.task.exceptions.UserNotEqualTaskAuthorException;
import com.BESourceryAdmissionTool.task.exceptions.UserNotLoggedInException;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.services.mapper.TaskMapper;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, UserRepository userRepository, AnswerRepository answerRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.taskMapper = taskMapper;
    }

    public Optional<FullTaskDto> getTaskData(long id) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }
        return task.map(taskMapper::fullTaskMap);
    }

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::taskMap)
                .collect(Collectors.toList());
    }

    public void deleteTask(long id) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        String taskAuthorEmail = task.get().getAuthor().getEmail();

        if(currentPrincipalEmail == null){
            throw new UserNotLoggedInException("No User is logged in at the moment.");
        }

        if(!currentPrincipalEmail.equals(taskAuthorEmail) ){
            throw new UserNotEqualTaskAuthorException("Can not delete task. You are not the author of the task.");
        }

        answerRepository.deleteAnswersByTask(task.get());
        taskRepository.deleteById(id);
    }

    public void createTask(TaskRequest taskRequest) {
        Optional<Task> sameTitle = taskRepository.findTaskByTitle(taskRequest.getTitle());
        if (sameTitle.isPresent()){
            throw new TaskNameAlreadyExistsException(taskRequest.getTitle());
        }

        Optional<Category> categoryOptional = categoryRepository.findById(taskRequest.getCategoryId());
        if (categoryOptional.isEmpty()) {
            throw new CategoryIdNotExistException(taskRequest.getCategoryId());
        }
        Category category = categoryOptional.get();

        Optional<User> userOptional = userRepository.findById(1L);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(1L);
        }
        User author = userOptional.get();

        Task task = taskMapper.taskMap(taskRequest, category, author);
        Task savedTask;
        try{
            savedTask = taskRepository.save(task);
        }
        catch (DataIntegrityViolationException ex){
            throw new TaskNameAlreadyExistsException(taskRequest.getTitle());
        }

        List<Answer> answers = taskRequest.getAnswers().stream().map(tr -> taskMapper.answerMap(tr, savedTask)).collect(Collectors.toList());
        answerRepository.saveAll(answers);
    }
}
