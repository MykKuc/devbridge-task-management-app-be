package com.BESourceryAdmissionTool.task.repositories;

import com.BESourceryAdmissionTool.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findTaskById (long id);
}
