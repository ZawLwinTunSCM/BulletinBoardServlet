package com.bulletin.board.web.form;

import java.sql.Date;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.persistence.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
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

    public UserForm(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.profile = userDTO.getProfile();
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.phone = userDTO.getPhone();
        this.address = userDTO.getAddress();
        this.role = userDTO.getRole();
        this.dob = userDTO.getDob();
    }

    public UserForm(User user) {
        this.id = user.getId();
        this.profile = user.getProfile();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.dob = user.getDob();
        this.createdUserId = user.getCreatedUserId();
    }
}
