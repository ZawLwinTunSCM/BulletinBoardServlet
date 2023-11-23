package com.bulletin.board.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.bl.service.post.PostService;
import com.bulletin.board.bl.service.post.impl.PostServiceImpl;
import com.bulletin.board.web.form.PostForm;

@WebServlet("/post/*")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PostService postService;

    public PostServlet() {
        super();
        this.postService = new PostServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        Object role = session.getAttribute("userRole");
        try {
            if (!isValidRole(role) || isValidRole(role) && Integer.parseInt(role.toString()) != 0) {
                error403(request, response);
                return;
            }
            switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                insertPost(request, response);
                break;
            case "/list":
                listPosts(request, response, false);
                break;
            case "/search":
                listPosts(request, response, true);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updatePost(request, response);
                break;
            case "/delete":
                deletePost(request, response);
                break;
            case "/detail":
                detailPost(request, response);
                break;
            case "/download":
                exportCSVPost(response);
                break;
            default:
                error404(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToPage("/jsp/post/insert.jsp", request, response);
    }

    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        PostForm newPost = getPostParameters(request);
        postService.doInsertPost(newPost);
        redirectToPage("list", response);
    }

    private void listPosts(HttpServletRequest request, HttpServletResponse response, boolean isSearch)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        String searchData = request.getParameter("searchData");
        String pageNum = request.getParameter("pageNumber");
        int pageNumber = pageNum == null || pageNum == "" ? 1 : Integer.parseInt(pageNum);
        if (pageNumber == 1 && isSearch && searchData != null) {
            session.setAttribute("searchData", searchData);
        }
        if (isSearch) {
            if (searchData == null) {
                searchData = session.getAttribute("searchData").toString();
            }
        }
        List<PostDTO> posts = postService.doGetAllPosts(searchData, pageNumber);
        request.setAttribute("listPost", posts);
        request.setAttribute("searchData", searchData);
        request.setAttribute("pageNum", pageNumber);
        request.setAttribute("type", isSearch ? "search" : "list");
        request.setAttribute("total", postService.doGetTotalCount(searchData));
        forwardToPage("/jsp/post/list.jsp", request, response);
    }

    private void detailPost(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        PostDTO post = postService.doGetPostById(id);
        request.setAttribute("post", post);
        forwardToPage("/jsp/post/detail.jsp", request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PostDTO post = postService.doGetPostById(id);
        request.setAttribute("post", post);
        forwardToPage("/jsp/post/insert.jsp", request, response);
    }

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        PostForm updatedPost = getPostParameters(request);
        postService.doUpdatePost(updatedPost);
        redirectToPage("list", response);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        postService.doDeletePost(id);
        redirectToPage("list", response);
    }

    private void error404(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        forwardToPage("/jsp/error/404.jsp", request, response);
    }

    private void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void redirectToPage(String page, HttpServletResponse response) throws IOException {
        response.sendRedirect(page);
    }

    private PostForm getPostParameters(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int status = Integer.parseInt(request.getParameter("status"));
        return new PostForm(id, title, description, status, id);
    }

    private void exportCSVPost(HttpServletResponse response) {
        List<PostDTO> posts = postService.doGetPosts();
        StringBuilder csvData = new StringBuilder();
        csvData.append("ID, Title, Description, Author, Status, Posted Date\n");
        for (int i = 0; i < posts.size(); i++) {
            PostDTO post = posts.get(i);
            String status = post.getStatus() == 0 ? "Admin" : "User";
            csvData.append(post.getId() + "," + post.getTitle() + "," + post.getDescription() + ",Leo," + status + ","
                    + post.getUpdatedAt() + "\n");
        }
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=data.csv");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(csvData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidRole(Object role) {
        return !(role == null || role == "");
    }

    private void error403(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        forwardToPage("/jsp/error/403.jsp", request, response);
    }
}
