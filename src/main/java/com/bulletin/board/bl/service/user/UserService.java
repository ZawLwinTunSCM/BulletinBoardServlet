package com.bulletin.board.bl.service.user;

import java.util.List;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.web.form.UserForm;

public interface UserService {
    public void doInsertUser(UserForm userForm);

    public List<UserDTO> doGetAllUsers(String searchData, int pageNumber);

    public UserDTO doGetUserById(int id);

    public UserDTO doGetUserByEmail(String email);

    public void doUpdateUser(UserForm user);

    public void doChangePassword(int id, String password);

    public void doDeleteUser(int id);

    public int doGetTotalCount(String searchData);
}
