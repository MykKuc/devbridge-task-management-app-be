package com.BESourceryAdmissionTool.task.services;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.answer.repositories.AnswerRepository;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import com.BESourceryAdmissionTool.task.dto.*;
import com.BESourceryAdmissionTool.task.exceptions.TaskNotFoundException;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.services.mapper.TaskMapper;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        if (task.isEmpty()){
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

    public void createTask(TaskRequest taskRequest){
        Optional<Category> categoryOptional = categoryRepository.findById(taskRequest.getCategoryId());
        if(categoryOptional.isEmpty()){
            throw new CategoryIdNotExistException(taskRequest.getCategoryId());
        }
        Category category = categoryOptional.get();

        Optional<User> userOptional = userRepository.findById(taskRequest.getAuthorId());
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(taskRequest.getAuthorId());
        }
        User author = userOptional.get();

        Task task = Task.builder()
                        .id(null)
                        .title(taskRequest.getTitle())
                        .description(taskRequest.getDescription())
                        .summary(taskRequest.getSummary())
                        .creationDate(new Date())
                        .score(0)
                        .category(category)
                        .author(author)
                        .answers(null)
                        .build();

        List<Answer> answers = taskRequest.getAnswers().stream().map(tr -> taskMapper.answerMap(tr, task.getId())).collect(Collectors.toList());

        answerRepository.saveAll(answers);
        task.setAnswers(answers);
        taskRepository.save(task);
    }
}
