package com.BESourceryAdmissionTool.task.repositories;

import com.BESourceryAdmissionTool.task.model.Answer;
import com.BESourceryAdmissionTool.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAnswerByTask(Task task);
}
