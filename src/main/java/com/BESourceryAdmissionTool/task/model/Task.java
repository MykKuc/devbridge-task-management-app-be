package com.BESourceryAdmissionTool.task.model;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.task_vote.model.Task_Vote;
import com.BESourceryAdmissionTool.user.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String summary;
    private Date creationDate;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    private List<Task_Vote> votes;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "task")
    @JsonManagedReference
    private List<Answer> answers;
}
