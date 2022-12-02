package com.BESourceryAdmissionTool.task.model;

import com.BESourceryAdmissionTool.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String summary;
    private Date creationDate;
    private int score;

    @ManyToOne
    private Category category;

    @ManyToOne
    private UserEntity author;

    @OneToMany
    @JoinColumn(name="task_id")
    private List<Answer> answers;
}
