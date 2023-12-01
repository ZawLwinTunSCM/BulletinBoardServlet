package com.bulletin.board.persistence.dao.user;

import java.util.List;

import com.bulletin.board.persistence.entity.User;

/**
 * <h2>UserDao Class</h2>
 * <p>
 * Process for Displaying UserDao
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public interface UserDao {
    /**
     * <h2>dbInsertUser</h2>
     * <p>
     * Insert User
     * </p>
     *
     * @param user User
     * @return void
     */
    public void dbInsertUser(User user);

    /**
     * <h2>dbGetAllUsers</h2>
     * <p>
     * Get All Users with LIMIT
     * </p>
     *
     * @param searchData String
     * @param pageNumber int
     * @param limit      int
     * @return List<User>
     */
    public List<User> dbGetAllUsers(String searchData, int pageNumber, int limit);

    /**
     * <h2>dbGetUserById</h2>
     * <p>
     * Get User By ID
     * </p>
     *
     * @param id int
     * @return User
     */
    public User dbGetUserById(int id);

    /**
     * <h2>dbGetUserByEmail</h2>
     * <p>
     * Get User By Email
     * </p>
     *
     * @param email String
     * @return User
     */
    public User dbGetUserByEmail(String email);

    /**
     * <h2>dbUpdateUser</h2>
     * <p>
     * Update User
     * </p>
     *
     * @param user User
     * @return void
     */
    public void dbUpdateUser(User user);

    /**
     * <h2>dbChangePassword</h2>
     * <p>
     * Change User Password
     * </p>
     *
     * @param id       int
     * @param password String
     * @return void
     */
    public void dbChangePassword(int id, String password);

    /**
     * <h2>dbDeleteUser</h2>
     * <p>
     * Delete User
     * </p>
     *
     * @param id int
     * @return void
     */
    public void dbDeleteUser(int id);

    /**
     * <h2>dbGetTotalCount</h2>
     * <p>
     * Get The Total Count of Users
     * </p>
     *
     * @param searchData String
     * @return int
     */
    public int dbGetTotalCount(String searchData);
}
