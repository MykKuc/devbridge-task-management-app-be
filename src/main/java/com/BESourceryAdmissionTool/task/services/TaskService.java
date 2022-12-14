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
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.services.mapper.TaskMapper;
import com.BESourceryAdmissionTool.task_vote.model.TaskVote;
import com.BESourceryAdmissionTool.task_vote.repositories.TaskVoteRepository;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
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

    public Optional<FullTaskDto> getTaskData(long id, String token) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
        }

        Optional<FullTaskDto> fullTaskDto = task.map(taskMapper::fullTaskMap);
        if (token != null) {
            if (fullTaskDto.isPresent()) {
                Optional<User> user = userRepository.findByToken(token);
                if (user.isPresent()) {
                    Optional<TaskVote> taskVote = taskVoteRepository.findTaskVoteByTaskAndUser(task.get(), user.get());
                    if (taskVote.isPresent()) {
                        fullTaskDto.get().setVoted(true);
                    }
                }
            }
        }
        return fullTaskDto;
    }

    public List<TaskDto> getAllTasks(String token) {
        List<Task> tasks = taskRepository.findAll();
        List<TaskDto> list = tasks.stream()
                .map(taskMapper::taskMap)
                .collect(Collectors.toList());
        if (token != null) {
            Optional<User> user = userRepository.findByToken(token);
            if (user.isPresent()) {
                for (TaskDto taskDto : list) {
                    Optional<Task> task = taskRepository.findTaskById(taskDto.getId());
                    if (task.isPresent()) {
                        Optional<TaskVote> taskVote = taskVoteRepository.findTaskVoteByTaskAndUser(task.get(), user.get());
                        if (taskVote.isPresent()) {
                            taskDto.setVoted(true);
                        }
                    }
                }
            }
        }
        return list;
    }

    public void deleteTask(long id) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException("Task not found");
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
}
