package com.bulletin.board.bl.service.post;

import java.util.List;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.web.form.PostForm;

/**
 * <h2>PostService Class</h2>
 * <p>
 * Process for Displaying PostService
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public interface PostService {
    /**
     * <h2>doInsertPost</h2>
     * <p>
     * Insert Post
     * </p>
     *
     * @param postForm PostForm
     * @return void
     */
    public void doInsertPost(PostForm postForm);

    /**
     * <h2>doGetAllPosts</h2>
     * <p>
     * Get All Posts with LIMIT
     * </p>
     *
     * @param id         int
     * @param searchData String
     * @param pageNumber int
     * @return List<PostDTO>
     */
    public List<PostDTO> doGetAllPosts(int id, String searchData, int pageNumber);

    /**
     * <h2>doGetPosts</h2>
     * <p>
     * Get All Posts without LIMIT
     * </p>
     *
     * @param id int
     * @return List<PostDTO>
     */
    public List<PostDTO> doGetPosts(int id);

    /**
     * <h2>doGetPostById</h2>
     * <p>
     * Get Post By ID
     * </p>
     *
     * @param id int
     * @return PostDTO
     */
    public PostDTO doGetPostById(int id);

    /**
     * <h2>doUpdatePost</h2>
     * <p>
     * Update Post
     * </p>
     *
     * @param post PostForm
     * @return void
     */
    public void doUpdatePost(PostForm post);

    /**
     * <h2>doDeletePost</h2>
     * <p>
     * Delete Post
     * </p>
     *
     * @param id int
     * @return void
     */
    public void doDeletePost(int id);

    /**
     * <h2>doGetTotalCount</h2>
     * <p>
     * Get Total Count of Posts
     * </p>
     *
     * @param id         int
     * @param searchData String
     * @return int
     */
    public int doGetTotalCount(int id, String searchData);
}
