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

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.mindrot.jbcrypt.BCrypt;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.bl.service.user.UserService;
import com.bulletin.board.bl.service.user.impl.UserServiceImpl;
import com.bulletin.board.common.Common;
import com.bulletin.board.web.form.UserForm;

/**
 * <h2>UserServlet Class</h2>
 * <p>
 * Process for Displaying UserServlet
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@MultipartConfig
@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    /**
     * <h2>serialVersionUID</h2>
     * <p>
     * serialVersionUID
     * </p>
     */
    private static final long serialVersionUID = 1L;
    /**
     * <h2>userService</h2>
     * <p>
     * userService
     * </p>
     */
    private final UserService userService;

    /**
     * <h2>Constructor for UserServlet</h2>
     * <p>
     * Constructor for UserServlet
     * </p>
     */
    public UserServlet() {
        super();
        this.userService = new UserServiceImpl();
    }

    /**
     * <h2>doGet</h2>
     * <p>
     * Get Method
     * </p>
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        Object role = session.getAttribute(Common.SESSION_USER_ROLE);
        try {
            if (!Common.isValidRole(role)) {
                Common.error403(request, response);
                return;
            }
            switch (action) {
            case "/new":
                Common.forwardToPage(Common.USER_INSERT_JSP, request, response);
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
            case "/passChange":
                Common.forwardToPage(Common.USER_PASS_CHANGE_URL, request, response);
                break;
            case "/changePassword":
                changePassword(request, response);
                break;
            default:
                Common.error404(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * <h2>doPost</h2>
     * <p>
     * Post Method
     * </p>
     * 
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * <h2>showEditForm</h2>
     * <p>
     * Forward to User Edit Page
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     * @return void
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Object role = Common.getLoginUserRole(request);
        int id = Integer.parseInt(request.getParameter("id"));
        UserDTO user = userService.doGetUserById(id);
        int loginId = Common.getLoginUserId(request);
        if (Common.isValidRole(role)
                && (Integer.parseInt(role.toString()) == 0 || (user != null && user.getId() == loginId))) {
            request.setAttribute("user", user);
            Common.forwardToPage(Common.USER_INSERT_JSP, request, response);
        } else {
            Common.error403(request, response);
        }
    }

    /**
     * <h2>insertUser</h2>
     * <p>
     * Insert User
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     * @return void
     */
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserForm newUser = getUserParameters(request);
        int id = Common.getLoginUserId(request);
        newUser.setCreatedUserId(id);
        newUser.setCreatedUserId(id);
        userService.doInsertUser(newUser);
        Common.redirectToPage("list", response);
    }

    /**
     * <h2>listUsers</h2>
     * <p>
     * Get All Users with LIMIT
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param isSearch boolean
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     * @return void
     */
    private void listUsers(HttpServletRequest request, HttpServletResponse response, boolean isSearch)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        String searchData = request.getParameter("searchData");
        String pageNum = request.getParameter("pageNumber");
        int pageNumber = Common.isDataNullOrEmpty(pageNum) ? 1 : Integer.parseInt(pageNum);
        if (pageNumber == 1 && isSearch && searchData != null) {
            session.setAttribute(Common.SESSION_SEARCH_DATA, searchData);
        }
        if (isSearch) {
            if (Common.isDataNullOrEmpty(searchData)) {
                searchData = session.getAttribute(Common.SESSION_SEARCH_DATA).toString();
            }
        }
        List<UserDTO> users = userService.doGetAllUsers(searchData, pageNumber);
        request.setAttribute("listUser", users);
        request.setAttribute("searchData", searchData);
        request.setAttribute("pageNum", pageNumber);
        request.setAttribute("type", isSearch ? "search" : "list");
        request.setAttribute("total", userService.doGetTotalCount(searchData));
        Common.forwardToPage(Common.USER_LIST_URL, request, response);
    }

    /**
     * <h2>detailUser</h2>
     * <p>
     * Forward to User Detail Page
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     * @return void
     */
    private void detailUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserDTO user = userService.doGetUserById(id);
        request.setAttribute("user", user);
        Common.forwardToPage(Common.USER_DETAIL_URL, request, response);
    }

    /**
     * <h2>updateUser</h2>
     * <p>
     * Update User
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     * @return void
     */
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        UserForm updatedUser = getUserParameters(request);
        userService.doUpdateUser(updatedUser);
        Common.redirectToPage("list", response);
    }

    /**
     * <h2>changePassword</h2>
     * <p>
     * Change User Password
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     * @return void
     */
    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String oldPass = request.getParameter("oldPass");
        String newPass = request.getParameter("newPass");
        UserDTO user = userService.doGetUserById(id);
        if (BCrypt.checkpw(oldPass, user.getPassword())) {
            userService.doChangePassword(id, newPass);
            request.setAttribute("successMsg", "Change Password Successfully!");
            Common.forwardToPage(Common.USER_PASS_CHANGE_URL, request, response);
        } else {
            request.setAttribute("errorMsg", "Please Enter Correct Old Password!");
            Common.forwardToPage(Common.USER_PASS_CHANGE_URL, request, response);
        }
    }

    /**
     * <h2>deleteUser</h2>
     * <p>
     * Delete User
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws SQLException
     * @throws IOException
     * @return void
     */
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.doDeleteUser(id);
        Common.redirectToPage("list", response);
    }

    /**
     * <h2>getUserParameters</h2>
     * <p>
     * Get the Parameters of User from JSP
     * </p>
     *
     * @param request HttpServletRequest
     * @throws IOException
     * @throws ServletException
     * @return UserForm
     */
    private UserForm getUserParameters(HttpServletRequest request) throws IOException, ServletException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;
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
        Part filePart = request.getPart("profile");
        String fileName = null;
        if (filePart.getSize() != 0) {
            fileName = Common.getFileName(filePart);
            String uploadDirectory = request.getServletContext().getRealPath("/") + "resources" + File.separator + "img"
                    + File.separator;
            try (InputStream input = filePart.getInputStream()) {
                Path filePath = Paths.get(uploadDirectory, fileName);
                Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

        }
        return new UserForm(id, fileName, name, email, password, phone, address, role, dob, id, id);
    }
}
