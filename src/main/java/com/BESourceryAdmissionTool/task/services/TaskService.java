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
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.services.mapper.TaskMapper;
import com.BESourceryAdmissionTool.task_vote.model.TaskVote;
import com.BESourceryAdmissionTool.task_vote.repositories.TaskVoteRepository;
import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import com.BESourceryAdmissionTool.user.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final AnswerRepository answerRepository;
    private final TaskVoteRepository taskVoteRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository,
                       TaskVoteRepository taskVoteRepository,
                       CategoryRepository categoryRepository,
                       AnswerRepository answerRepository,
                       TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
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

    public List<TaskDto> getAllTasks(User user, boolean onlyMine) {
        List<Task> tasks;
        if (user == null && onlyMine) {
            throw new UnauthorizedExeption("User not authorized");
        }

        if (user != null && onlyMine) {
            tasks = taskRepository.findTasksByAuthorId(user.getId());
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream()
                .map(task -> taskMapper.taskMap(task, checkVote(user, task)))
                .collect(Collectors.toList());
    }

    public void deleteTask(long id, User user) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }

        long currentUserId = user.getId();
        long taskAuthorId = task.get().getAuthor().getId();
        if (currentUserId != taskAuthorId && user.getRole() != Role.ADMIN) {
            throw new UserNotEqualTaskAuthorException("Can not delete task. You are not the author of the task.");
        }

        answerRepository.deleteAnswersByTask(task.get());
        taskRepository.deleteById(id);
    }

    public void createTask(TaskRequest taskRequest, User user) {
        Optional<Task> sameTitle = taskRepository.findTaskByTitle(taskRequest.getTitle());
        if (sameTitle.isPresent()) {
            throw new TaskNameAlreadyExistsException(taskRequest.getTitle());
        }

        Optional<Category> categoryOptional = categoryRepository.findById(taskRequest.getCategoryId());
        if (categoryOptional.isEmpty()) {
            throw new CategoryIdNotExistException(taskRequest.getCategoryId());
        }
        Category category = categoryOptional.get();

        Task task = taskMapper.taskMap(taskRequest, category, user);
        Task savedTask;
        try {
            savedTask = taskRepository.save(task);
        } catch (DataIntegrityViolationException ex) {
            throw new TaskNameAlreadyExistsException(taskRequest.getTitle());
        }

        addAnswersForTask(taskRequest.getAnswers(), savedTask);
    }

    public void updateTask(long id, UpdateTaskRequest request, User user) {
        Optional<Task> primaryTask = taskRepository.findTaskById(id);
        if (primaryTask.isEmpty()) {
            throw new TaskNotFoundException("Task was not found");
        }

        long currentUserId = user.getId();
        long taskAuthorId = primaryTask.get().getAuthor().getId();
        if (currentUserId != taskAuthorId && user.getRole() != Role.ADMIN) {
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
        if (user != null && user.getToken() != null) {
            Optional<TaskVote> taskVote = taskVoteRepository.findTaskVoteByTaskAndUser(task, user);
            return taskVote.isPresent();
        }
        return false;
    }
}
