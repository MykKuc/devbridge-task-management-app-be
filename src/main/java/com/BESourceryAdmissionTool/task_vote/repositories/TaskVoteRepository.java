package com.BESourceryAdmissionTool.task_vote.repositories;


import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task_vote.model.TaskVote;
import com.BESourceryAdmissionTool.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TaskVoteRepository extends JpaRepository<TaskVote, Long> {
    Optional<TaskVote> findTaskVoteByTaskAndUser(Task task, User user);
}
