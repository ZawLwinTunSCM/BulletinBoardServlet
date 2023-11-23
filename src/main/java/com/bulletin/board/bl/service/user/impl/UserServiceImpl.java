package com.bulletin.board.bl.service.user.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.bl.service.user.UserService;
import com.bulletin.board.persistence.dao.user.UserDao;
import com.bulletin.board.persistence.dao.user.impl.UserDaoImpl;
import com.bulletin.board.persistence.entity.User;
import com.bulletin.board.web.form.UserForm;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void doInsertUser(UserForm userForm) {
        User user = new User(userForm);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setCreatedUserId(0);
        user.setUpdatedUserId(0);
        userDao.dbInsertUser(user);
    }

    @Override
    public List<UserDTO> doGetAllUsers(String searchData, int pageNumber) {
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        List<User> users = userDao.dbGetAllUsers(searchData, pageNumber);
        for (int i = 0; i < users.size(); i++) {
            UserDTO userDTO = new UserDTO(users.get(i));
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public UserDTO doGetUserById(int id) {
        return new UserDTO(userDao.dbGetUserById(id));
    }

    @Override
    public UserDTO doGetUserByEmail(String email) {
        User user = userDao.dbGetUserByEmail(email);
        return user == null ? null : new UserDTO(user);
    }

    @Override
    public void doUpdateUser(UserForm userForm) {
        UserDTO userDTO = new UserDTO(userDao.dbGetUserById(userForm.getId()));
        userDTO.setProfile(userForm.getProfile());
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

    @Override
    public void doChangePassword(int id, String password) {
        userDao.dbChangePassword(id, BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    @Override
    public void doDeleteUser(int id) {
        userDao.dbDeleteUser(id);
    }

    @Override
    public int doGetTotalCount(String searchData) {
        return userDao.dbGetTotalCount(searchData);
    }
}
