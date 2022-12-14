package com.BESourceryAdmissionTool.task_vote.repositories;


import com.BESourceryAdmissionTool.task_vote.model.TaskVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TaskVoteRepository extends JpaRepository<TaskVote, Long> {
}
