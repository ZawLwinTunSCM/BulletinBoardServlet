package com.bulletin.board.bl.service.post;

import java.util.List;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.web.form.PostForm;

public interface PostService {
    public void doInsertPost(PostForm postForm);

    public List<PostDTO> doGetAllPosts(int id, String searchData, int pageNumber);

    public List<PostDTO> doGetPosts();

    public PostDTO doGetPostById(int id);

    public void doUpdatePost(PostForm post);

    public void doDeletePost(int id);

    public int doGetTotalCount(int id, String searchData);
}
