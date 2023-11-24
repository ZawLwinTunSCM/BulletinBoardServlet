package com.bulletin.board.persistence.dao.post.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bulletin.board.common.Common;
import com.bulletin.board.persistence.dao.post.PostDao;
import com.bulletin.board.persistence.entity.Post;

public class PostDaoImpl implements PostDao {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bulletin?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";

    private static final String INSERT_POST_SQL = "INSERT INTO post (title, description, status, created_user_id, updated_user_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_POSTS = "SELECT * FROM post WHERE created_user_id = ? OR status = 1 LIMIT 10 OFFSET ?";
    private static final String SELECT_POSTS = "SELECT * FROM post WHERE created_user_id = ? OR status = 1";
    private static final String SELECT_POST_BY_ID = "SELECT * FROM post WHERE id = ?";
    private static final String SEARCH_POSTS = "SELECT * FROM post WHERE (created_user_id = ? OR status = 1) AND (title LIKE ? OR description LIKE ?) LIMIT 10 OFFSET ?";
    private static final String UPDATE_POST_SQL = "UPDATE post SET title = ?, description = ?, status = ?, updated_user_id=?, updated_at=? WHERE id = ?";
    private static final String DELETE_POST_SQL = "DELETE FROM post WHERE id = ?";
    private static final String COUNT_POSTS = "SELECT COUNT(*) as count FROM post WHERE created_user_id = ? OR status = 1";
    private static final String SEARCH_COUNT_POSTS = "SELECT COUNT(*) as count FROM post WHERE (created_user_id = ? OR status = 1) AND (title LIKE ? OR description LIKE ?)";
    private static final String GET_AUTHOR = "SELECT user.name FROM post JOIN user ON post.created_user_id=user.id WHERE post.created_user_id = ?";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    @Override
    public void dbInsertPost(Post post) {
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_POST_SQL)) {
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getDescription());
            preparedStatement.setInt(3, post.getStatus());
            preparedStatement.setInt(4, post.getCreatedUserId());
            preparedStatement.setInt(5, post.getUpdatedUserId());
            preparedStatement.setDate(6, new Date(System.currentTimeMillis()));
            preparedStatement.setDate(7, new Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> dbGetAllPosts(int id, String searchData, int pageNumber) {
        boolean isAllPost = Common.isDataNullOrEmpty(searchData);
        List<Post> posts = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(isAllPost ? SELECT_ALL_POSTS : SEARCH_POSTS)) {
            if (!isAllPost) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, "%" + searchData + "%");
                preparedStatement.setString(3, "%" + searchData + "%");
                preparedStatement.setInt(4, (pageNumber - 1) * 10);
            } else {
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, (pageNumber - 1) * 10);
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int postId = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int status = rs.getInt("status");
                int createdUserId = rs.getInt("created_user_id");
                int updatedUserId = rs.getInt("updated_user_id");
                Date createdAt = rs.getDate("created_at");
                posts.add(new Post(postId, title, description, status, createdUserId, updatedUserId, createdAt, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> dbGetPosts(int id) {
        List<Post> posts = new ArrayList<>();
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_POSTS)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int postId = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int status = rs.getInt("status");
                int createdUserId = rs.getInt("created_user_id");
                int updatedUserId = rs.getInt("updated_user_id");
                Date createdAt = rs.getDate("created_at");
                posts.add(new Post(postId, title, description, status, createdUserId, updatedUserId, createdAt, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post dbGetPostById(int id) {
        Post post = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_POST_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                int status = rs.getInt("status");
                int createdUserId = rs.getInt("created_user_id");
                int updatedUserId = rs.getInt("updated_user_id");
                Date createdAt = rs.getDate("created_at");
                post = new Post(id, title, description, status, createdUserId, updatedUserId, createdAt, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void dbUpdatePost(Post post) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_POST_SQL)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setInt(3, post.getStatus());
            statement.setInt(4, post.getUpdatedUserId());
            statement.setDate(5, post.getUpdatedAt());
            statement.setInt(6, post.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dbDeletePost(int id) {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_POST_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int dbGetTotalCount(int id, String searchData) {
        boolean isAllPost = searchData == null || searchData == "";
        int count = 0;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement(isAllPost ? COUNT_POSTS : SEARCH_COUNT_POSTS)) {
            if (!isAllPost) {
                preparedStatement.setString(1, "%" + searchData + "%");
                preparedStatement.setString(2, "%" + searchData + "%");
                preparedStatement.setInt(3, id);
            } else {
                preparedStatement.setInt(1, id);
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

    @Override
    public String dbGetAuthor(int id) {
        String author = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                author = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }
}