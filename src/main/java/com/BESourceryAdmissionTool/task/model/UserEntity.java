package com.BESourceryAdmissionTool.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user", schema =  "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private String password;
    private String token;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }
}