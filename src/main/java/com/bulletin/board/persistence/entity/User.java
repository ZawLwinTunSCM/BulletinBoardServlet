package com.bulletin.board.persistence.entity;

import java.sql.Date;

import com.bulletin.board.web.form.UserForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h2>User Class</h2>
 * <p>
 * Process for Displaying User
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * <h2>id</h2>
     * <p>
     * id
     * </p>
     */
    private int id;
    /**
     * <h2>profile</h2>
     * <p>
     * profile
     * </p>
     */
    private String profile;
    /**
     * <h2>name</h2>
     * <p>
     * name
     * </p>
     */
    private String name;
    /**
     * <h2>email</h2>
     * <p>
     * email
     * </p>
     */
    private String email;
    /**
     * <h2>password</h2>
     * <p>
     * password
     * </p>
     */
    private String password;
    /**
     * <h2>phone</h2>
     * <p>
     * phone
     * </p>
     */
    private String phone;
    /**
     * <h2>address</h2>
     * <p>
     * address
     * </p>
     */
    private String address;
    /**
     * <h2>role</h2>
     * <p>
     * role
     * </p>
     */
    private int role;
    /**
     * <h2>dob</h2>
     * <p>
     * dob
     * </p>
     */
    private Date dob;
    /**
     * <h2>createdUserId</h2>
     * <p>
     * createdUserId
     * </p>
     */
    private int createdUserId;
    /**
     * <h2>updatedUserId</h2>
     * <p>
     * updatedUserId
     * </p>
     */
    private int updatedUserId;
    /**
     * <h2>createdAt</h2>
     * <p>
     * createdAt
     * </p>
     */
    private Date createdAt;
    /**
     * <h2>updatedAt</h2>
     * <p>
     * updatedAt
     * </p>
     */
    private Date updatedAt;

    /**
     * <h2>Constructor for User</h2>
     * <p>
     * Constructor for User
     * </p>
     * 
     * @param userForm
     */
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
