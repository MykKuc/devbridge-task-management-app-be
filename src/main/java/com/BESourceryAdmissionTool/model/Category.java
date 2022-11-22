package com.BESourceryAdmissionTool.model;

import com.BESourceryAdmissionTool.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.CategoriesViews.class)
    private long Id;
    @JsonView(Views.CategoriesViews.class)
    private String Name;
    private Long Author_ID;
    private String Description;
    private Date  CreationDate;


    public Category(long id, String name, Long authorID, String description, Date creationDate) {
        Id = id;
        Name = name;
        Author_ID = authorID;
        Description = description;
        CreationDate = creationDate;
    }

    public Category() {

    }

    public long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public Long getAuthor() {
        return Author_ID;
    }

    public String getDescription() {
        return Description;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Author='" + Author_ID + '\'' +
                ", Description='" + Description + '\'' +
                ", CreationDate=" + CreationDate +
                '}';
    }
}
