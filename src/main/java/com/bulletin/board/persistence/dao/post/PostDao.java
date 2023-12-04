package com.bulletin.board.persistence.dao.post;

import java.util.List;

import com.bulletin.board.persistence.entity.Post;

/**
 * <h2>PostDao Class</h2>
 * <p>
 * Process for Displaying PostDao
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public interface PostDao {
    /**
     * <h2>dbInsertPost</h2>
     * <p>
     * Insert Post
     * </p>
     *
     * @param post Post
     * @return void
     */
    public void dbInsertPost(Post post);

    /**
     * <h2>dbGetAllPosts</h2>
     * <p>
     * Get All Posts with LIMIT
     * </p>
     *
     * @param id         int
     * @param searchData String
     * @param pageNumber int
     * @param limit      int
     * @return List<Post>
     */
    public List<Post> dbGetAllPosts(int id, String searchData, int pageNumber, int limit);

    /**
     * <h2>dbGetPosts</h2>
     * <p>
     * Get All Posts without LIMIT
     * </p>
     *
     * @param id         int
     * @param searchData String
     * @return List<Post>
     */
    public List<Post> dbGetPosts(int id, String searchData);

    /**
     * <h2>dbGetPostById</h2>
     * <p>
     * Get Post By ID
     * </p>
     *
     * @param id int
     * @return Post
     */
    public Post dbGetPostById(int id);

    /**
     * <h2>dbUpdatePost</h2>
     * <p>
     * Update Post
     * </p>
     *
     * @param post Post
     * @return void
     */
    public void dbUpdatePost(Post post);

    /**
     * <h2>dbDeletePost</h2>
     * <p>
     * Delete Post
     * </p>
     *
     * @param id int
     * @return void
     */
    public void dbDeletePost(int id);

    /**
     * <h2>dbGetTotalCount</h2>
     * <p>
     * Get Total Count of Posts
     * </p>
     *
     * @param id         int
     * @param searchData String
     * @return int
     */
    public int dbGetTotalCount(int id, String searchData);

    /**
     * <h2>dbGetAuthor</h2>
     * <p>
     * Get Author of Post
     * </p>
     *
     * @param id int
     * @return String
     */
    public String dbGetAuthor(int id);

    /**
     * <h2>dbisDuplicatePost</h2>
     * <p>
     * Check if the post is already existed or not
     * </p>
     *
     * @param id    int
     * @param title String
     * @return boolean
     */
    public boolean dbisDuplicatePost(int id, String title);
}
