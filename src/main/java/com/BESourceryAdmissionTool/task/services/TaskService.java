package com.BESourceryAdmissionTool.task.services;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.answer.repositories.AnswerRepository;
import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.category.repositories.CategoryRepository;
import com.BESourceryAdmissionTool.task.dto.FullTaskDto;
import com.BESourceryAdmissionTool.task.dto.TaskDto;
import com.BESourceryAdmissionTool.task.requests.AnswerRequest;
import com.BESourceryAdmissionTool.task.requests.UpdateTaskRequest;
import com.BESourceryAdmissionTool.task.exceptions.TaskNameAlreadyExistsException;
import com.BESourceryAdmissionTool.task.exceptions.TaskNotFoundException;
import com.BESourceryAdmissionTool.task.exceptions.UserNotEqualTaskAuthorException;
import com.BESourceryAdmissionTool.task.exceptions.UserNotLoggedInException;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.services.mapper.TaskMapper;
import com.BESourceryAdmissionTool.task_vote.model.TaskVote;
import com.BESourceryAdmissionTool.task_vote.repositories.TaskVoteRepository;
import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
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
    private final TaskVoteRepository taskVoteRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskVoteRepository taskVoteRepository, CategoryRepository categoryRepository, UserRepository userRepository, AnswerRepository answerRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.taskVoteRepository = taskVoteRepository;
        this.taskMapper = taskMapper;
    }

    public Optional<FullTaskDto> getTaskData(long id, User user) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }

        return task.map(tsk -> taskMapper.fullTaskMap(tsk, checkVote(user, tsk)));
    }

    public List<TaskDto> getAllTasks(User user) {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> list = tasks.stream()
                .map(task -> taskMapper.taskMap(task, checkVote(user, task)))
                .collect(Collectors.toList());
        return list;
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
        if (sameTitle.isPresent()) {
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
        try {
            savedTask = taskRepository.save(task);
        } catch (DataIntegrityViolationException ex) {
            throw new TaskNameAlreadyExistsException(taskRequest.getTitle());
        }

        addAnswersForTask(taskRequest.getAnswers(), savedTask);
    }

    public void updateTask(long id, UpdateTaskRequest request) {
        Optional<Task> primaryTask = taskRepository.findTaskById(id);
        if (primaryTask.isEmpty()) {
            throw new TaskNotFoundException("Task was not found");
        }

        //Gets current user name (email) and the email of the task author.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        String taskAuthorEmail = primaryTask.get().getAuthor().getEmail();

        // Checks if user is logged in and if task author is the same as logged in user.
        if(currentPrincipalEmail == null){
            throw new UserNotLoggedInException("No User is logged in at the moment.");
        }

        if(!currentPrincipalEmail.equals(taskAuthorEmail) ){
            throw new UserNotEqualTaskAuthorException("Can not update the task. You are not the author of the task.");
        }

        Task task = primaryTask.get();

        answerRepository.deleteAnswersByTask(task);

        task.setDescription(request.getDescription());
        task.setSummary(request.getSummary());
        task.setTitle(request.getTitle());

        if (request.getCategoryId() != task.getCategory().getId()) {
            Optional<Category> categoryOptional = categoryRepository.findById(request.getCategoryId());
            if (categoryOptional.isEmpty()) {
                throw new CategoryIdNotExistException(request.getCategoryId());
            }
            task.setCategory(categoryOptional.get());
        }

        Task savedTask = taskRepository.save(task);
        addAnswersForTask(request.getAnswers(), savedTask);
    }

    private void addAnswersForTask(List<AnswerRequest> answerRequest, Task savedTask) {
        List<Answer> answers = answerRequest.stream()
                .map(tr -> taskMapper.answerMap(tr.getText(), tr.isCorrect(), savedTask))
                .collect(Collectors.toList());
        answerRepository.saveAll(answers);
    }

    private boolean checkVote(User user, Task task) {
        if (user != null && user.getToken() != null)
        {
            Optional<TaskVote> taskVote = taskVoteRepository.findTaskVoteByTaskAndUser(task, user);
            if (taskVote.isPresent()) {
                return true;
            }
        }
        return false;
    }
}
