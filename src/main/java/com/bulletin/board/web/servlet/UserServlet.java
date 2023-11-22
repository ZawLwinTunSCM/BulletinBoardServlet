package com.bulletin.board.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.bl.service.user.UserService;
import com.bulletin.board.bl.service.user.impl.UserServiceImpl;
import com.bulletin.board.web.form.UserForm;

@WebServlet("/user/*")
@MultipartConfig
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService;

    public UserServlet() {
        super();
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                insertUser(request, response);
                break;
            case "/list":
                listUsers(request, response, false);
                break;
            case "/search":
                listUsers(request, response, true);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateUser(request, response);
                break;
            case "/delete":
                deleteUser(request, response);
                break;
            case "/detail":
                detailUser(request, response);
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
        forwardToPage("/jsp/user/insert.jsp", request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserForm newUser = getUserParameters(request);
        userService.doInsertUser(newUser);
        redirectToPage("list", response);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response, boolean isSearch)
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
        List<UserDTO> users = userService.doGetAllUsers(searchData, pageNumber);
        request.setAttribute("listUser", users);
        request.setAttribute("searchData", searchData);
        request.setAttribute("pageNum", pageNumber);
        request.setAttribute("type", isSearch ? "search" : "list");
        request.setAttribute("total", userService.doGetTotalCount(searchData));
        forwardToPage("/jsp/user/list.jsp", request, response);
    }

    private void detailUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDTO user = userService.doGetUserById(id);
        request.setAttribute("user", user);
        forwardToPage("/jsp/user/detail.jsp", request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDTO user = userService.doGetUserById(id);
        request.setAttribute("user", user);
        forwardToPage("/jsp/user/insert.jsp", request, response);
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserForm updatedUser = getUserParameters(request);
        userService.doUpdateUser(updatedUser);
        redirectToPage("list", response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.doDeleteUser(id);
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

    private UserForm getUserParameters(HttpServletRequest request) throws IOException, ServletException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;
        String img = request.getParameter("img");
        Part filePart = request.getPart("profile");
        String fileName = getFileName(filePart);
        String uploadDirectory = request.getServletContext().getRealPath("/") + "resources" + File.separator + "img"
                + File.separator;
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        int role = Integer.parseInt(request.getParameter("role"));
        String dobParam = request.getParameter("dob");
        Date dob = null;
        try {
            dob = new Date(dateFormat.parse(dobParam).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try (InputStream input = filePart.getInputStream()) {
            fileName = fileName == null || fileName == "" ? img.split("/")[4] : fileName;
            Path filePath = Paths.get(uploadDirectory, fileName);
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        return new UserForm(id, fileName, name, email, password, phone, address, role, dob);
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}