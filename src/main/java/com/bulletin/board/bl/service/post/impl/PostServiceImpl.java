package com.bulletin.board.bl.service.post.impl;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.bl.service.post.PostService;
import com.bulletin.board.persistence.dao.post.PostDao;
import com.bulletin.board.persistence.dao.post.impl.PostDaoImpl;
import com.bulletin.board.persistence.entity.Post;
import com.bulletin.board.web.form.PostForm;

/**
 * <h2>PostServiceImpl Class</h2>
 * <p>
 * Process for Displaying PostServiceImpl
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public class PostServiceImpl implements PostService {
    /**
     * <h2>postDao</h2>
     * <p>
     * postDao
     * </p>
     */
    private PostDao postDao = new PostDaoImpl();

    /**
     * <h2>doInsertPost</h2>
     * <p>
     * Insert Post
     * </p>
     * 
     * @param postForm PostForm
     */
    @Override
    public void doInsertPost(PostForm postForm) {
        Post post = new Post(postForm);
        postDao.dbInsertPost(post);
    }

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
    @Override
    public List<PostDTO> doGetAllPosts(int id, String searchData, int pageNumber) {
        return postDao.dbGetAllPosts(id, searchData, pageNumber).stream().map(post -> {
            PostDTO postDTO = new PostDTO(post);
            postDTO.setAuthor(postDao.dbGetAuthor(postDTO.getCreatedUserId()));
            return postDTO;
        }).collect(Collectors.toList());
    }

    /**
     * <h2>doGetPosts</h2>
     * <p>
     * Get All Posts without LIMIT
     * </p>
     * 
     * @param id int
     * @return List<PostDTO>
     */
    @Override
    public List<PostDTO> doGetPosts(int id) {
        return postDao.dbGetPosts(id).stream().map(post -> {
            PostDTO postDTO = new PostDTO(post);
            postDTO.setAuthor(postDao.dbGetAuthor(postDTO.getCreatedUserId()));
            return postDTO;
        }).collect(Collectors.toList());
    }

    /**
     * <h2>doGetPostById</h2>
     * <p>
     * Get Post By ID
     * </p>
     * 
     * @param id int
     * @return PostDTO
     */
    @Override
    public PostDTO doGetPostById(int id) {
        PostDTO postDTO = new PostDTO(postDao.dbGetPostById(id));
        postDTO.setAuthor(postDao.dbGetAuthor(postDTO.getCreatedUserId()));
        return postDTO;
    }

    /**
     * <h2>doUpdatePost</h2>
     * <p>
     * Update Post
     * </p>
     * 
     * @param postForm PostForm
     */
    @Override
    public void doUpdatePost(PostForm postForm) {
        PostDTO postDTO = new PostDTO(postDao.dbGetPostById(postForm.getId()));
        postDTO.setTitle(postForm.getTitle());
        postDTO.setDescription(postForm.getDescription());
        postDTO.setStatus(postForm.getStatus());
        postDTO.setUpdatedUserId(postForm.getUpdatedUserId());
        Post post = new Post(new PostForm(postDTO));
        post.setUpdatedAt(new Date(System.currentTimeMillis()));
        postDao.dbUpdatePost(post);
    }

    /**
     * <h2>doDeletePost</h2>
     * <p>
     * Delete Post
     * </p>
     * 
     * @param id int
     */
    @Override
    public void doDeletePost(int id) {
        postDao.dbDeletePost(id);
    }

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
    @Override
    public int doGetTotalCount(int id, String searchData) {
        return postDao.dbGetTotalCount(id, searchData);
    }
}
