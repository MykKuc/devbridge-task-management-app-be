package com.BESourceryAdmissionTool.task_vote.model;

import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Table(name = "task_votes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Task_VoteKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "task_id")
    private Long taskId;
    
}