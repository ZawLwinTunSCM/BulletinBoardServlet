package com.bulletin.board.persistence.dao.post;

import java.util.List;

import com.bulletin.board.persistence.entity.Post;

public interface PostDao {
    public void dbInsertPost(Post post);

    public List<Post> dbGetAllPosts(String searchData, int pageNumber);

    public Post doGetPostById(int id);

    public void dbUpdatePost(Post post);

    public void dbDeletePots(int id);

    public int dbGetTotalCount(String searchData);
}
