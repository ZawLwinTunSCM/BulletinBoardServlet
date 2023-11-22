package com.bulletin.board.persistence.dao.user;

import java.util.List;

import com.bulletin.board.persistence.entity.User;

public interface UserDao {
    public void dbInsertUser(User user);

    public List<User> dbGetAllUsers(String searchData, int pageNumber);

    public User dbGetUserById(int id);

    public void dbUpdateUser(User user);

    public void dbDeleteUser(int id);

    public int dbGetTotalCount(String searchData);
}
