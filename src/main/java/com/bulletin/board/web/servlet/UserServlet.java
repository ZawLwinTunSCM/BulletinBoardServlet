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
        int role = Common.getLoginUserRole(request);
        try {
            switch (action) {
            case "/new":
                Common.forwardToPage(Common.USER_INSERT_JSP, request, response);
                break;
            case "/insert":
                insertUser(request, response);
                break;
            default:
                if (role == 3) {
                    Common.error403(request, response);
                    return;
                }
                switch (action) {
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
        int role = Common.getLoginUserRole(request);
        int id = Integer.parseInt(request.getParameter("id"));
        UserDTO user = userService.doGetUserById(id);
        int loginId = Common.getLoginUserId(request);
        if (role != 3 && (role == 0 || (user != null && user.getId() == loginId))) {
            request.setAttribute("user", user);
            request.setAttribute("type", "edit");
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
        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            request.setAttribute("user", newUser);
            request.setAttribute("err", "Password and Confirm Password must be the same!");
            Common.forwardToPage(Common.USER_INSERT_JSP, request, response);
            return;
        }
        boolean isDuplicateUser = userService.doInsertUser(newUser);
        if (isDuplicateUser) {
            request.setAttribute("user", newUser);
            request.setAttribute("errEmail", "This email is already registered!");
            Common.forwardToPage(Common.USER_INSERT_JSP, request, response);
        } else {
            int role = Common.getLoginUserRole(request);
            request.getSession().setAttribute("successMsg", "User Account is created successfully!");
            if (role == 3) {
                Common.forwardToPage(Common.LOGIN_JSP, request, response);
                request.getSession().removeAttribute("successMsg");
            } else {
                Common.redirectToPage("list", response);
            }
        }
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
        String limitString = request.getParameter("limit");
        int limit = 10;
        if (limitString == null) {
            Object limitObj = session.getAttribute("limit");
            if (limitObj == null) {
                session.setAttribute("limit", limit);
            } else {
                limit = Integer.parseInt(limitObj.toString());
            }
        } else {
            limit = Integer.parseInt(limitString);
            session.setAttribute("limit", limit);
        }
        if (session.getAttribute("oldLimit") != null
                && Integer.parseInt(session.getAttribute("oldLimit").toString()) != limit) {
            pageNumber = 1;
        }
        session.setAttribute("oldLimit", limit);
        List<UserDTO> users = userService.doGetAllUsers(searchData, pageNumber, limit);
        request.setAttribute("listUser", users);
        request.setAttribute("searchData", searchData);
        request.setAttribute("pageNum", pageNumber);
        request.setAttribute("limit", limit);
        request.setAttribute("type", isSearch ? "search" : "list");
        request.setAttribute("total", userService.doGetTotalCount(searchData));
        Common.forwardToPage(Common.USER_LIST_URL, request, response);
        request.getSession().removeAttribute("successMsg");
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
        boolean isDuplicateUser = userService.doUpdateUser(updatedUser);
        if (isDuplicateUser) {
            if (updatedUser.getProfile() == null) {
                updatedUser.setProfile(userService.doGetUserById(updatedUser.getId()).getProfile());
            }
            request.setAttribute("user", updatedUser);
            request.setAttribute("type", "edit");
            request.setAttribute("errEmail", "This email is already registered!");
            Common.forwardToPage(Common.USER_INSERT_JSP, request, response);
        } else {
            if (updatedUser.getId() == Common.getLoginUserId(request)) {
                HttpSession session = request.getSession();
                session.setAttribute(Common.SESSION_USER_ID, updatedUser.getId());
                session.setAttribute(Common.SESSION_USER_NAME, updatedUser.getName());
                session.setAttribute(Common.SESSION_USER_ROLE, updatedUser.getRole());
            }
            request.getSession().setAttribute("successMsg", "User updated successfully!");
            Common.redirectToPage("list", response);
        }
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
        String conNewPass = request.getParameter("conNewPass");
        request.setAttribute("oldPass", oldPass);
        request.setAttribute("newPass", newPass);
        request.setAttribute("conNewPass", conNewPass);
        if (!newPass.equals(conNewPass)) {
            request.setAttribute("errConNewPass", "Password and Confirm Password must be the same!");
            Common.forwardToPage(Common.USER_PASS_CHANGE_URL, request, response);
            return;
        }
        UserDTO user = userService.doGetUserById(id);
        if (BCrypt.checkpw(oldPass, user.getPassword())) {
            userService.doChangePassword(id, newPass);
            request.getSession().setAttribute("successMsg", "Password change successfully!");
            request.setAttribute("oldPass", "");
            request.setAttribute("newPass", "");
            request.setAttribute("conNewPass", "");
            Common.forwardToPage(Common.USER_PASS_CHANGE_URL, request, response);
        } else {
            request.setAttribute("errOldPass", "Please enter correct old password!");
            Common.forwardToPage(Common.USER_PASS_CHANGE_URL, request, response);
        }
        request.getSession().removeAttribute("successMsg");
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
        request.getSession().setAttribute("successMsg", "User deleted successfully!");
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
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        int role = Integer.parseInt(request.getParameter("role"));
        String dobParam = request.getParameter("dob");
        Date dob = null;
        if (!Common.isDataNullOrEmpty(dobParam)) {
            try {
                dob = new Date(dateFormat.parse(dobParam).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
        return new UserForm(id, fileName, name, email, password, confirmPassword, phone, address, role, dob, id, id);
    }
}
