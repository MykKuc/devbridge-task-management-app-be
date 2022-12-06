package com.BESourceryAdmissionTool.answer.repositories;


import com.BESourceryAdmissionTool.answer.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
