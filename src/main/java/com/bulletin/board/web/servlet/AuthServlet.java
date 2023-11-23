package com.bulletin.board.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    public AuthServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        switch (action) {
        case "/loginPage":
            showLoginForm(request, response);
            break;
        case "/login":
            loginAction(request, response);
            break;
        case "/logout":
            logoutAction(request, response);
            break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToPage("/jsp/login.jsp", request, response);
    }

    private void loginAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        UserDTO user = userService.doGetUserByEmail(email);
        if (user == null) {
            request.setAttribute("errorMsg", "User does not exist!");
            forwardToPage("/jsp/login.jsp", request, response);
        } else {
            if (BCrypt.checkpw(pass, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                session.setAttribute("userRole", user.getRole());
                response.sendRedirect(request.getContextPath() + "/post/list");
            } else {
                request.setAttribute("errorMsg", "Wrong Email or Password!");
                forwardToPage("/jsp/login.jsp", request, response);
            }
        }
    }

    private void logoutAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("logoutMsg", "You have logout successful!");
        forwardToPage("/jsp/login.jsp", request, response);
    }

    private void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
