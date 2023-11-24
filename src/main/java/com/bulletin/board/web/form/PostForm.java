package com.bulletin.board.web.form;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.persistence.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {
    private int id;
    private String title;
    private String description;
    private int status;
    private int createdUserId;
    private int updatedUserId;

    public PostForm(PostDTO postDTO) {
        this.id = postDTO.getId();
        this.title = postDTO.getTitle();
        this.description = postDTO.getDescription();
        this.status = postDTO.getStatus();
        this.createdUserId = postDTO.getCreatedUserId();
        this.updatedUserId = postDTO.getUpdatedUserId();
    }

    public PostForm(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.status = post.getStatus();
        this.createdUserId = post.getCreatedUserId();
        this.updatedUserId = post.getUpdatedUserId();
    }
}