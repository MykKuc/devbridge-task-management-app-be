package com.BESourceryAdmissionTool.task.repositories;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Modifying
    @Query("update Task t set t.title = ?1, t.summary = ?2, t.description = ?3, t.category = ?4, t.answers = ?5")
    void updateTitleAndSummaryAndDescriptionAndCategoryAndAnswers(String title, String summary, String description, Category category, Answer answers);

    Optional<Task> findTaskById(long id);
    Optional<Task> findTaskByTitle(String title);


}
