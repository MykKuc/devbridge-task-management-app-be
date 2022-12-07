package com.BESourceryAdmissionTool.answer.repositories;


import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    void deleteAnswersByTask(Task task);
}
