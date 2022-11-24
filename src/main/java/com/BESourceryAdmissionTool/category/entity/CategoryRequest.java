package com.BESourceryAdmissionTool.category.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    private long id;
    private String name;
    private String description;
}
