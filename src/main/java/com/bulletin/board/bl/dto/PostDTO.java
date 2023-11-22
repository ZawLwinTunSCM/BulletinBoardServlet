package com.bulletin.board.bl.dto;

import java.sql.Date;

import com.bulletin.board.persistence.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int id;
    private String title;
    private String description;
    private int status;
    private String author;
    private Date updatedAt;
    private int updatedUserId;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.status = post.getStatus();
        this.updatedAt = post.getUpdatedAt();
        this.updatedUserId = post.getUpdatedUserId();
        this.updatedAt = post.getUpdatedAt();
    }
}