package com.bulletin.board.bl.service.post.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.bl.service.post.PostService;
import com.bulletin.board.persistence.dao.post.PostDao;
import com.bulletin.board.persistence.dao.post.impl.PostDaoImpl;
import com.bulletin.board.persistence.entity.Post;
import com.bulletin.board.web.form.PostForm;

public class PostServiceImpl implements PostService {
    private PostDao postDao = new PostDaoImpl();

    @Override
    public void doInsertPost(PostForm postForm) {
        Post post = new Post(postForm);
        postDao.dbInsertPost(post);
    }

    @Override
    public List<PostDTO> doGetAllPosts(int id, String searchData, int pageNumber) {
        List<PostDTO> postDTOs = new ArrayList<PostDTO>();
        List<Post> posts = postDao.dbGetAllPosts(id, searchData, pageNumber);
        for (int i = 0; i < posts.size(); i++) {
            PostDTO postDTO = new PostDTO(posts.get(i));
            postDTO.setAuthor(postDao.dbGetAuthor(postDTO.getCreatedUserId()));
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    @Override
    public List<PostDTO> doGetPosts() {
        List<PostDTO> postDTOs = new ArrayList<PostDTO>();
        List<Post> posts = postDao.dbGetPosts();
        for (int i = 0; i < posts.size(); i++) {
            PostDTO postDTO = new PostDTO(posts.get(i));
            postDTO.setAuthor(postDao.dbGetAuthor(postDTO.getCreatedUserId()));
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    @Override
    public PostDTO doGetPostById(int id) {
        PostDTO postDTO = new PostDTO(postDao.dbGetPostById(id));
        postDTO.setAuthor(postDao.dbGetAuthor(postDTO.getCreatedUserId()));
        return postDTO;
    }

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

    @Override
    public void doDeletePost(int id) {
        postDao.dbDeletePost(id);
    }

    @Override
    public int doGetTotalCount(int id, String searchData) {
        return postDao.dbGetTotalCount(id, searchData);
    }
}
