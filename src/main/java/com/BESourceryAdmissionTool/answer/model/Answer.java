package com.BESourceryAdmissionTool.answer.model;

import com.BESourceryAdmissionTool.task.model.Task;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private boolean isCorrect;

    @JsonBackReference
    @JoinColumn(name="task_id")
    @ManyToOne
    private Task task;
}