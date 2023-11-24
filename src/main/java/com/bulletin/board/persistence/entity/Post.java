package com.bulletin.board.persistence.entity;

import java.sql.Date;

import com.bulletin.board.web.form.PostForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h2>Post Class</h2>
 * <p>
 * Process for Displaying Post
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    /**
     * <h2>id</h2>
     * <p>
     * id
     * </p>
     */
    private int id;
    /**
     * <h2>title</h2>
     * <p>
     * title
     * </p>
     */
    private String title;
    /**
     * <h2>description</h2>
     * <p>
     * description
     * </p>
     */
    private String description;
    /**
     * <h2>status</h2>
     * <p>
     * status
     * </p>
     */
    private int status;
    /**
     * <h2>createdUserId</h2>
     * <p>
     * createdUserId
     * </p>
     */
    private int createdUserId;
    /**
     * <h2>updatedUserId</h2>
     * <p>
     * updatedUserId
     * </p>
     */
    private int updatedUserId;
    /**
     * <h2>createdAt</h2>
     * <p>
     * createdAt
     * </p>
     */
    private Date createdAt;
    /**
     * <h2>updatedAt</h2>
     * <p>
     * updatedAt
     * </p>
     */
    private Date updatedAt;

    /**
     * <h2>Constructor for Post</h2>
     * <p>
     * Constructor for Post
     * </p>
     * 
     * @param postForm
     */
    public Post(PostForm postForm) {
        this.id = postForm.getId();
        this.title = postForm.getTitle();
        this.description = postForm.getDescription();
        this.status = postForm.getStatus();
        this.createdUserId = postForm.getCreatedUserId();
        this.updatedUserId = postForm.getUpdatedUserId();
    }
}
