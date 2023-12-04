package com.bulletin.board.bl.service.user;

import java.util.List;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.web.form.UserForm;

/**
 * <h2>UserService Class</h2>
 * <p>
 * Process for Displaying UserService
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public interface UserService {
    /**
     * <h2>doInsertUser</h2>
     * <p>
     * Insert User
     * </p>
     *
     * @param userForm UserForm
     * @return boolean
     */
    public boolean doInsertUser(UserForm userForm);

    /**
     * <h2>doGetAllUsers</h2>
     * <p>
     * Get All Users with LIMIT
     * </p>
     *
     * @param searchData String
     * @param pageNumber int
     * @param limit      int
     * @return List<UserDTO>
     */
    public List<UserDTO> doGetAllUsers(String searchData, int pageNumber, int limit);

    /**
     * <h2>doGetUserById</h2>
     * <p>
     * Get User By ID
     * </p>
     *
     * @param id int
     * @return UserDTO
     */
    public UserDTO doGetUserById(int id);

    /**
     * <h2>doGetUserByEmail</h2>
     * <p>
     * Get User By Email
     * </p>
     *
     * @param email String
     * @return UserDTO
     */
    public UserDTO doGetUserByEmail(String email);

    /**
     * <h2>doUpdateUser</h2>
     * <p>
     * Update User
     * </p>
     *
     * @param user UserForm
     * @return boolean
     */
    public boolean doUpdateUser(UserForm user);

    /**
     * <h2>doChangePassword</h2>
     * <p>
     * Change User Password
     * </p>
     *
     * @param id       int
     * @param password String
     * @return void
     */
    public void doChangePassword(int id, String password);

    /**
     * <h2>doDeleteUser</h2>
     * <p>
     * Delete User
     * </p>
     *
     * @param id int
     * @return void
     */
    public void doDeleteUser(int id);

    /**
     * <h2>doGetTotalCount</h2>
     * <p>
     * Get The Total Count of Users
     * </p>
     *
     * @param searchData String
     * @return int
     */
    public int doGetTotalCount(String searchData);
}
