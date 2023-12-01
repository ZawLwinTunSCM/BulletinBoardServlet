package com.bulletin.board.persistence.dao.user.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bulletin.board.common.Common;
import com.bulletin.board.persistence.dao.user.UserDao;
import com.bulletin.board.persistence.entity.User;

/**
 * <h2>UserDaoImpl Class</h2>
 * <p>
 * Process for Displaying UserDaoImpl
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public class UserDaoImpl implements UserDao {
    /**
     * <h2>JDBC_URL</h2>
     * <p>
     * JDBC_URL
     * </p>
     */
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bulletin?useSSL=false";
    /**
     * <h2>JDBC_USERNAME</h2>
     * <p>
     * JDBC_USERNAME
     * </p>
     */
    private static final String JDBC_USERNAME = "root";
    /**
     * <h2>JDBC_PASSWORD</h2>
     * <p>
     * JDBC_PASSWORD
     * </p>
     */
    private static final String JDBC_PASSWORD = "root";

    /**
     * <h2>INSERT_USER_SQL</h2>
     * <p>
     * INSERT_USER_SQL
     * </p>
     */
    private static final String INSERT_USER_SQL = "INSERT INTO user (profile, name, email, password, phone, address, role, dob, created_user_id, updated_user_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    /**
     * <h2>SELECT_ALL_USERS</h2>
     * <p>
     * SELECT_ALL_USERS
     * </p>
     */
    private static final String SELECT_ALL_USERS = "SELECT * FROM user LIMIT ? OFFSET ?";
    /**
     * <h2>SELECT_USER_BY_ID</h2>
     * <p>
     * SELECT_USER_BY_ID
     * </p>
     */
    private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    /**
     * <h2>SELECT_USER_BY_EMAIL</h2>
     * <p>
     * SELECT_USER_BY_EMAIL
     * </p>
     */
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    /**
     * <h2>SEARCH_USERS</h2>
     * <p>
     * SEARCH_USERS
     * </p>
     */
    private static final String SEARCH_USERS = "SELECT * FROM user WHERE name LIKE ? OR email LIKE ? LIMIT ? OFFSET ?";
    /**
     * <h2>UPDATE_USER_SQL</h2>
     * <p>
     * UPDATE_USER_SQL
     * </p>
     */
    private static final String UPDATE_USER_SQL = "UPDATE user SET name = ?, email = ?, phone = ?, address = ?, role = ?, dob = ?,  updated_at = ?, profile = ? WHERE id = ?";
    /**
     * <h2>CHANGE_PASSWORD_SQL</h2>
     * <p>
     * CHANGE_PASSWORD_SQL
     * </p>
     */
    private static final String CHANGE_PASSWORD_SQL = "UPDATE user SET password = ?, updated_at = ? WHERE id = ?";
    /**
     * <h2>DELETE_USER_SQL</h2>
     * <p>
     * DELETE_USER_SQL
     * </p>
     */
    private static final String DELETE_USER_SQL = "DELETE FROM user WHERE id = ?";
    /**
     * <h2>COUNT_USERS</h2>
     * <p>
     * COUNT_USERS
     * </p>
     */
    private static final String COUNT_USERS = "SELECT COUNT(*) as count FROM user";
    /**
     * <h2>SEARCH_COUNT_USERS</h2>
     * <p>
     * SEARCH_COUNT_USERS
     * </p>
     */
    private static final String SEARCH_COUNT_USERS = "SELECT COUNT(*) as count FROM user WHERE name LIKE ? OR email LIKE ?";

    /**
     * <h2>getConnection</h2>
     * <p>
     * Get the connection from MySQL
     * </p>
     *
     * @throws SQLException
     * @return Connection
     */
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * <h2>dbInsertUser</h2>
     * <p>
     * Insert User
     * </p>
     * 
     * @param user User
     */
    @Override
    public void dbInsertUser(User user) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getProfile());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setInt(7, user.getRole());
            preparedStatement.setDate(8, user.getDob());
            preparedStatement.setObject(9, user.getCreatedUserId() == 0 ? null : user.getCreatedUserId());
            preparedStatement.setObject(10, user.getUpdatedUserId() == 0 ? null : user.getUpdatedUserId());
            preparedStatement.setDate(11, new Date(System.currentTimeMillis()));
            preparedStatement.setDate(12, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
    @Override
    public List<User> dbGetAllUsers(String searchData, int pageNumber, int limit) {
        boolean isAllUser = Common.isDataNullOrEmpty(searchData);
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(isAllUser ? SELECT_ALL_USERS : SEARCH_USERS)) {
            if (!isAllUser) {
                preparedStatement.setString(1, "%" + searchData + "%");
                preparedStatement.setString(2, "%" + searchData + "%");
                preparedStatement.setInt(3, limit);
                preparedStatement.setInt(4, (pageNumber - 1) * limit);
            } else {
                preparedStatement.setInt(1, limit);
                preparedStatement.setInt(2, (pageNumber - 1) * limit);
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String profile = rs.getString("profile");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int role = rs.getInt("role");
                Date dob = rs.getDate("dob");
                int createdUserId = rs.getInt("created_user_id");
                int updatedUserId = rs.getInt("updated_user_id");
                users.add(new User(id, profile, name, email, password, phone, address, role, dob, createdUserId,
                        updatedUserId, null, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * <h2>dbGetUserById</h2>
     * <p>
     * Get User By ID
     * </p>
     * 
     * @param id int
     * @return User
     */
    @Override
    public User dbGetUserById(int id) {
        User user = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String profile = rs.getString("profile");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int role = rs.getInt("role");
                Date dob = rs.getDate("dob");
                int createdUserId = rs.getInt("created_user_id");
                int updatedUserId = rs.getInt("updated_user_id");
                user = new User(id, profile, name, email, password, phone, address, role, dob, createdUserId,
                        updatedUserId, null, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * <h2>dbGetUserByEmail</h2>
     * <p>
     * Get User By Email
     * </p>
     * 
     * @param email String
     * @return User
     */
    @Override
    public User dbGetUserByEmail(String email) {
        User user = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));
                String profile = rs.getString("profile");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int role = rs.getInt("role");
                Date dob = rs.getDate("dob");
                int createdUserId = rs.getInt("created_user_id");
                int updatedUserId = rs.getInt("updated_user_id");
                user = new User(id, profile, name, email, password, phone, address, role, dob, createdUserId,
                        updatedUserId, null, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * <h2>dbUpdateUser</h2>
     * <p>
     * Update User
     * </p>
     * 
     * @param user User
     */
    @Override
    public void dbUpdateUser(User user) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getAddress());
            statement.setInt(5, user.getRole());
            statement.setDate(6, user.getDob());
            statement.setDate(7, user.getUpdatedAt());
            statement.setString(8, user.getProfile());
            statement.setInt(9, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>dbChangePassword</h2>
     * <p>
     * Change User Password
     * </p>
     * 
     * @param id       int
     * @param password String
     */
    @Override
    public void dbChangePassword(int id, String password) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(CHANGE_PASSWORD_SQL)) {
            statement.setString(1, password);
            statement.setDate(2, new Date(System.currentTimeMillis()));
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>dbDeleteUser</h2>
     * <p>
     * Delete User
     * </p>
     * 
     * @param id int
     */
    @Override
    public void dbDeleteUser(int id) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <h2>dbGetTotalCount</h2>
     * <p>
     * Get The Total Count of Users
     * </p>
     * 
     * @param searchData
     * @return int
     */
    @Override
    public int dbGetTotalCount(String searchData) {
        boolean isAllUser = Common.isDataNullOrEmpty(searchData);
        int count = 0;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(isAllUser ? COUNT_USERS : SEARCH_COUNT_USERS)) {
            if (!isAllUser) {
                preparedStatement.setString(1, "%" + searchData + "%");
                preparedStatement.setString(2, "%" + searchData + "%");
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}