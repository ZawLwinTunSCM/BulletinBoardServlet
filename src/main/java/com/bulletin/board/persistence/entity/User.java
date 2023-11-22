package com.bulletin.board.persistence.entity;

import java.sql.Date;

import com.bulletin.board.web.form.UserForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String profile;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private int role;
    private Date dob;
    private int createdUserId;
    private int updatedUserId;
    private Date createdAt;
    private Date updatedAt;

    public User(UserForm userForm) {
        this.id = userForm.getId();
        this.profile = userForm.getProfile();
        this.name = userForm.getName();
        this.email = userForm.getEmail();
        this.password = userForm.getPassword();
        this.phone = userForm.getPhone();
        this.address = userForm.getAddress();
        this.dob = userForm.getDob();
        this.role = userForm.getRole();
    }
}
