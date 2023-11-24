package com.bulletin.board.web.form;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.persistence.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h2>PostForm Class</h2>
 * <p>
 * Process for Displaying PostForm
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {
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
     * <h2>Constructor for PostForm</h2>
     * <p>
     * Constructor for PostForm
     * </p>
     * 
     * @param postDTO
     */
    public PostForm(PostDTO postDTO) {
        this.id = postDTO.getId();
        this.title = postDTO.getTitle();
        this.description = postDTO.getDescription();
        this.status = postDTO.getStatus();
        this.createdUserId = postDTO.getCreatedUserId();
        this.updatedUserId = postDTO.getUpdatedUserId();
    }

    /**
     * <h2>Constructor for PostForm</h2>
     * <p>
     * Constructor for PostForm
     * </p>
     * 
     * @param post
     */
    public PostForm(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.status = post.getStatus();
        this.createdUserId = post.getCreatedUserId();
        this.updatedUserId = post.getUpdatedUserId();
    }
}