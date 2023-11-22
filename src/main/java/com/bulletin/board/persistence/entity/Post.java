package com.bulletin.board.persistence.entity;

import java.sql.Date;

import com.bulletin.board.web.form.PostForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private String title;
    private String description;
    private int status;
    private int createdUserId;
    private int updatedUserId;
    private Date createdAt;
    private Date updatedAt;

    public Post(PostForm postForm) {
        this.id = postForm.getId();
        this.title = postForm.getTitle();
        this.description = postForm.getDescription();
        this.status = postForm.getStatus();
        this.updatedUserId = postForm.getUpdatedUserId();
    }
}
