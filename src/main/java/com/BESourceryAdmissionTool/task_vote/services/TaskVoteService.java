package com.BESourceryAdmissionTool.task_vote.services;

import com.BESourceryAdmissionTool.category.exceptions.CategoryIdNotExistException;
import com.BESourceryAdmissionTool.task.exceptions.TaskNotFoundException;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task_vote.model.TaskVote;
import com.BESourceryAdmissionTool.task_vote.model.TaskVoteKey;
import com.BESourceryAdmissionTool.task_vote.repositories.TaskVoteRepository;
import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskVoteService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskVoteRepository taskVoteRepository;

    @Autowired
    public TaskVoteService (UserRepository userRepository, TaskRepository taskRepository, TaskVoteRepository taskVoteRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskVoteRepository = taskVoteRepository;
    }

    public void addVote (String token, Long taskId) {
        Optional<User> optionalUser = userRepository.findByToken(token);
        if (optionalUser.isEmpty()) {
            throw new UnauthorizedExeption("User must be logged in to vote");
        }
        User user = optionalUser.get();
        
        Optional<Task> optionalTask = taskRepository.findTaskById(taskId);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException("Task with the following id is not found: " + taskId);
        }
        Task task = optionalTask.get();

        TaskVoteKey taskVoteKey = new TaskVoteKey(user.getId(), task.getId());

        TaskVote taskVote = new TaskVote(taskVoteKey, task, user);

        taskVoteRepository.save(taskVote);
    }
}
