package com.BESourceryAdmissionTool.task_vote.model;


import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "task_votes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskVote {

    @EmbeddedId
    TaskVoteKey id;

    @JsonBackReference
    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    Task task;

    @JsonBackReference
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;


}
