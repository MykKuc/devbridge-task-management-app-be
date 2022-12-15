package com.BESourceryAdmissionTool.task.repositories;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskById(long id);
    Optional<Task> findTaskByTitle(String title);
    List<Task> findTasksByAuthorId(long authorId);


}
