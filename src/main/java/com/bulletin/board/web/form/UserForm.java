package com.bulletin.board.web.form;

import java.sql.Date;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.persistence.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h2>UserForm Class</h2>
 * <p>
 * Process for Displaying UserForm
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
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
     * <h2>Constructor for UserForm</h2>
     * <p>
     * Constructor for UserForm
     * </p>
     * 
     * @param userDTO
     */
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

    /**
     * <h2>Constructor for UserForm</h2>
     * <p>
     * Constructor for UserForm
     * </p>
     * 
     * @param user
     */
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
