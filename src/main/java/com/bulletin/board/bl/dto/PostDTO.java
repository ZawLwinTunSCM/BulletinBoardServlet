package com.bulletin.board.bl.dto;

import java.sql.Date;

import com.bulletin.board.persistence.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h2>PostDTO Class</h2>
 * <p>
 * Process for Displaying PostDTO
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
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
     * <h2>author</h2>
     * <p>
     * author
     * </p>
     */
    private String author;
    /**
     * <h2>createdAt</h2>
     * <p>
     * createdAt
     * </p>
     */
    private Date createdAt;
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
     * <h2>Constructor for PostDTO</h2>
     * <p>
     * Constructor for PostDTO
     * </p>
     * 
     * @param post
     */
    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.status = post.getStatus();
        this.createdUserId = post.getCreatedUserId();
        this.updatedUserId = post.getUpdatedUserId();
        this.createdAt = post.getCreatedAt();
    }
}