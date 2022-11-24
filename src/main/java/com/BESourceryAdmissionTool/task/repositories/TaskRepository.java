package com.BESourceryAdmissionTool.task.repositories;

import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.projection.TaskData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    TaskData findTaskById (long id);

}
