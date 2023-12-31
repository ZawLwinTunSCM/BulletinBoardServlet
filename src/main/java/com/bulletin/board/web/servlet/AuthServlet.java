package com.bulletin.board.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.bulletin.board.bl.dto.UserDTO;
import com.bulletin.board.bl.service.user.UserService;
import com.bulletin.board.bl.service.user.impl.UserServiceImpl;
import com.bulletin.board.common.Common;

/**
 * <h2>AuthServlet Class</h2>
 * <p>
 * Process for Displaying AuthServlet
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
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
     * <h2>Constructor for AuthServlet</h2>
     * <p>
     * Constructor for AuthServlet
     * </p>
     */
    public AuthServlet() {
        super();
        userService = new UserServiceImpl();
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
        switch (action) {
        case "/loginPage":
            Common.forwardToPage(Common.LOGIN_JSP, request, response);
            request.getSession().removeAttribute("successMsg");
            request.getSession().removeAttribute("removeMsg");
            break;
        case "/login":
            loginAction(request, response);
            break;
        case "/logout":
            logoutAction(request, response);
            break;
        case "/forgotPassword":
            Common.forwardToPage(Common.FORGOT_PASSWORD_JSP, request, response);
            break;
        case "/forgotPass":
            forgotPassword(request, response);
            break;
        case "/resetPassword":
            request.getSession().setAttribute("resetId", request.getQueryString().split("=")[1]);
            Common.forwardToPage(Common.RESET_PASSWORD_JSP, request, response);
            break;
        case "/resetPass":
            resetPassword(request, response);
            break;
        default:
            Common.error404(request, response);
            break;
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
     * <h2>loginAction</h2>
     * <p>
     * Login the User
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @return void
     */
    private void loginAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        UserDTO user = userService.doGetUserByEmail(email);
        if (user == null) {
            request.getSession().setAttribute("errorMsg", "User does not exist!");
            Common.forwardToPage(Common.LOGIN_JSP, request, response);
        } else {
            if (BCrypt.checkpw(pass, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute(Common.SESSION_USER_ID, user.getId());
                session.setAttribute(Common.SESSION_USER_NAME, user.getName());
                session.setAttribute(Common.SESSION_USER_ROLE, user.getRole());
                response.sendRedirect(request.getContextPath() + Common.LOGIN_POST_LIST_URL);
            } else {
                request.getSession().setAttribute("errorMsg", "Invalid email or password!");
                Common.forwardToPage(Common.LOGIN_JSP, request, response);
            }
        }
        request.getSession().removeAttribute("errorMsg");
    }

    /**
     * <h2>logoutAction</h2>
     * <p>
     * Logout the User
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @return void
     */
    private void logoutAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.getSession().setAttribute("successMsg", "You have successfully logged out!");
        Common.redirectToPage("loginPage", response);
    }

    /**
     * <h2>forgotPassword</h2>
     * <p>
     * 
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @return void
     */
    private void forgotPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDTO user = userService.doGetUserByEmail(email);
        if (user == null) {
            request.setAttribute("err", "Email is not registered!");
            request.setAttribute("email", email);
            Common.forwardToPage(Common.FORGOT_PASSWORD_JSP, request, response);
        } else {
            Common.sendMail(email, user.getId());
            request.getSession().setAttribute("successMsg", "Reset Password Link sent successfully!");
            Common.redirectToPage("loginPage", response);
        }
    }

    private void resetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getSession().getAttribute("resetId").toString());
        String newPass = request.getParameter("newPass");
        String conNewPass = request.getParameter("conNewPass");
        request.setAttribute("newPass", newPass);
        request.setAttribute("conNewPass", conNewPass);
        if (!newPass.equals(conNewPass)) {
            request.setAttribute("err", "New Password and Confirm New Password must be the same!");
            Common.forwardToPage(Common.RESET_PASSWORD_JSP, request, response);
            return;
        } else {
            userService.doChangePassword(id, newPass);
            request.getSession().setAttribute("successMsg", "Password reset successfully!");
            Common.redirectToPage("loginPage", response);
        }
    }
}
