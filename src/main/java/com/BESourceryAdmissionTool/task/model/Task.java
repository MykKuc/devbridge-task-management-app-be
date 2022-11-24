package com.BESourceryAdmissionTool.task.model;


import com.BESourceryAdmissionTool.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private Long authorId;

    @ManyToOne
    private Category category;
}
