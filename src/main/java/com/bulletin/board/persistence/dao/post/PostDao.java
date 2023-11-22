package com.bulletin.board.persistence.dao.post;

import java.util.List;

import com.bulletin.board.persistence.entity.Post;

public interface PostDao {
    public void dbInsertPost(Post post);

    public List<Post> dbGetAllPosts(String searchData, int pageNumber);

    public List<Post> dbGetPosts();

    public Post dbGetPostById(int id);

    public void dbUpdatePost(Post post);

    public void dbDeletePost(int id);

    public int dbGetTotalCount(String searchData);
}
