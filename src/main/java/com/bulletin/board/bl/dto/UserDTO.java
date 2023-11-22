package com.bulletin.board.bl.dto;

import java.sql.Date;

import com.bulletin.board.persistence.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String profile;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private int role;
    private Date dob;

    public UserDTO(User user) {
        this.id = user.getId();
        this.profile = user.getProfile();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.role = user.getRole();
        this.dob = user.getDob();
    }
}
