package com.BESourceryAdmissionTool.task.repositories;
import com.BESourceryAdmissionTool.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findTaskById(long id);

    @Query("UPDATE task SET title=#{task.title}, description=#{task.description}, " +
            "summary=#{task.summary}, score=#{task.score}, category_id=#{task.category.id}, author_id=#{task.author.id} WHERE id=#{task.id}")
    int update(@Param("task") Task task);

}
