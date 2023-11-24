package com.bulletin.board.persistence.dao.post;

import java.util.List;

import com.bulletin.board.persistence.entity.Post;

public interface PostDao {
    public void dbInsertPost(Post post);

    public List<Post> dbGetAllPosts(int id, String searchData, int pageNumber);

    public List<Post> dbGetPosts(int id);

    public Post dbGetPostById(int id);

    public void dbUpdatePost(Post post);

    public void dbDeletePost(int id);

    public int dbGetTotalCount(int id, String searchData);

    public String dbGetAuthor(int id);
}
