package com.bulletin.board.bl.service.user.impl;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.bl.service.user.UserService;
import com.bulletin.board.persistence.dao.user.UserDao;
import com.bulletin.board.persistence.dao.user.impl.UserDaoImpl;
import com.bulletin.board.persistence.entity.User;
import com.bulletin.board.web.form.UserForm;

/**
 * <h2>UserServiceImpl Class</h2>
 * <p>
 * Process for Displaying UserServiceImpl
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public class UserServiceImpl implements UserService {
    /**
     * <h2>userDao</h2>
     * <p>
     * userDao
     * </p>
     */
    private UserDao userDao = new UserDaoImpl();

    /**
     * <h2>doInsertUser</h2>
     * <p>
     * Insert User
     * </p>
     * 
     * @param userForm UserForm
     */
    @Override
    public void doInsertUser(UserForm userForm) {
        User user = new User(userForm);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userDao.dbInsertUser(user);
    }

    /**
     * <h2>doGetAllUsers</h2>
     * <p>
     * Get All Users with LIMIT
     * </p>
     * 
     * @param searchData String
     * @param pageNumber int
     * @return
     */
    @Override
    public List<UserDTO> doGetAllUsers(String searchData, int pageNumber) {
        return userDao.dbGetAllUsers(searchData, pageNumber).stream().map(UserDTO::new).collect(Collectors.toList());
    }

    /**
     * <h2>doGetUserById</h2>
     * <p>
     * Get User By ID
     * </p>
     * 
     * @param id int
     * @return UserDTO
     */
    @Override
    public UserDTO doGetUserById(int id) {
        return new UserDTO(userDao.dbGetUserById(id));
    }

    /**
     * <h2>doGetUserByEmail</h2>
     * <p>
     * Get User By Email
     * </p>
     * 
     * @param email String
     * @return UserDTO
     */
    @Override
    public UserDTO doGetUserByEmail(String email) {
        User user = userDao.dbGetUserByEmail(email);
        return user == null ? null : new UserDTO(user);
    }

    /**
     * <h2>doUpdateUser</h2>
     * <p>
     * Update User
     * </p>
     * 
     * @param userForm UserForm
     */
    @Override
    public void doUpdateUser(UserForm userForm) {
        UserDTO userDTO = new UserDTO(userDao.dbGetUserById(userForm.getId()));
        if (userForm.getProfile() != null) {
            userDTO.setProfile(userForm.getProfile());
        }
        userDTO.setName(userForm.getName());
        userDTO.setEmail(userForm.getEmail());
        userDTO.setPhone(userForm.getPhone());
        userDTO.setAddress(userForm.getAddress());
        userDTO.setDob(userForm.getDob());
        userDTO.setRole(userForm.getRole());
        User user = new User(new UserForm(userDTO));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        userDao.dbUpdateUser(user);
    }

    /**
     * <h2>doChangePassword</h2>
     * <p>
     * Change User Password
     * </p>
     * 
     * @param id       int
     * @param password String
     */
    @Override
    public void doChangePassword(int id, String password) {
        userDao.dbChangePassword(id, BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    /**
     * <h2>doDeleteUser</h2>
     * <p>
     * Delete User
     * </p>
     * 
     * @param id int
     */
    @Override
    public void doDeleteUser(int id) {
        userDao.dbDeleteUser(id);
    }

    /**
     * <h2>doGetTotalCount</h2>
     * <p>
     * Get The Total Count of Users
     * </p>
     * 
     * @param searchData String
     * @return int
     */
    @Override
    public int doGetTotalCount(String searchData) {
        return userDao.dbGetTotalCount(searchData);
    }
}
