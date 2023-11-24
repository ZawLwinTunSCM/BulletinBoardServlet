package com.bulletin.board.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * <h2>Common Class</h2>
 * <p>
 * Process for Displaying Common
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
public class Common {
    /**
     * <h2>SESSION_USER_ID</h2>
     * <p>
     * SESSION_USER_ID
     * </p>
     */
    public static final String SESSION_USER_ID = "userId";
    /**
     * <h2>SESSION_USER_NAME</h2>
     * <p>
     * SESSION_USER_NAME
     * </p>
     */
    public static final String SESSION_USER_NAME = "userName";
    /**
     * <h2>SESSION_USER_ROLE</h2>
     * <p>
     * SESSION_USER_ROLE
     * </p>
     */
    public static final String SESSION_USER_ROLE = "userRole";
    /**
     * <h2>SESSION_SEARCH_DATA</h2>
     * <p>
     * SESSION_SEARCH_DATA
     * </p>
     */
    public static final String SESSION_SEARCH_DATA = "search";

    /**
     * <h2>LOGIN_JSP</h2>
     * <p>
     * LOGIN_JSP
     * </p>
     */
    public static final String LOGIN_JSP = "/jsp/login.jsp";
    /**
     * <h2>LOGIN_POST_LIST_URL</h2>
     * <p>
     * LOGIN_POST_LIST_URL
     * </p>
     */
    public static final String LOGIN_POST_LIST_URL = "/post/list";

    /**
     * <h2>POST_INSERT_JSP</h2>
     * <p>
     * POST_INSERT_JSP
     * </p>
     */
    public static final String POST_INSERT_JSP = "/jsp/post/insert.jsp";
    /**
     * <h2>POST_LIST_URL</h2>
     * <p>
     * POST_LIST_URL
     * </p>
     */
    public static final String POST_LIST_URL = "/jsp/post/list.jsp";
    /**
     * <h2>POST_UPLOAD_URL</h2>
     * <p>
     * POST_UPLOAD_URL
     * </p>
     */
    public static final String POST_UPLOAD_URL = "/jsp/post/upload.jsp";
    /**
     * <h2>POST_DETAIL_URL</h2>
     * <p>
     * POST_DETAIL_URL
     * </p>
     */
    public static final String POST_DETAIL_URL = "/jsp/post/detail.jsp";

    /**
     * <h2>USER_INSERT_JSP</h2>
     * <p>
     * USER_INSERT_JSP
     * </p>
     */
    public static final String USER_INSERT_JSP = "/jsp/user/insert.jsp";
    /**
     * <h2>USER_LIST_URL</h2>
     * <p>
     * USER_LIST_URL
     * </p>
     */
    public static final String USER_LIST_URL = "/jsp/user/list.jsp";
    /**
     * <h2>USER_DETAIL_URL</h2>
     * <p>
     * USER_DETAIL_URL
     * </p>
     */
    public static final String USER_DETAIL_URL = "/jsp/user/detail.jsp";
    /**
     * <h2>USER_PASS_CHANGE_URL</h2>
     * <p>
     * USER_PASS_CHANGE_URL
     * </p>
     */
    public static final String USER_PASS_CHANGE_URL = "/jsp/user/passChange.jsp";

    /**
     * <h2>isValidRole</h2>
     * <p>
     * Check whether if the role is valid or not
     * </p>
     *
     * @param role Object
     * @return boolean
     */
    public static boolean isValidRole(Object role) {
        return role != null && !"".equals(role);
    }

    /**
     * <h2>forwardToPage</h2>
     * <p>
     * Forward to another page
     * </p>
     *
     * @param page     String
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @return void
     */
    public static void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    /**
     * <h2>redirectToPage</h2>
     * <p>
     * Redirect to another page
     * </p>
     *
     * @param page     String
     * @param response HttpServletResponse
     * @throws IOException
     * @return void
     */
    public static void redirectToPage(String page, HttpServletResponse response) throws IOException {
        response.sendRedirect(page);
    }

    /**
     * <h2>error403</h2>
     * <p>
     * Forward to Error 403 Page
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @return void
     */
    public static void error403(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToPage("/jsp/error/403.jsp", request, response);
    }

    /**
     * <h2>error404</h2>
     * <p>
     * Forward to Error 404 Page
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     * @return void
     */
    public static void error404(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToPage("/jsp/error/404.jsp", request, response);
    }

    /**
     * <h2>getLoginUserId</h2>
     * <p>
     * Get Logged In User
     * </p>
     *
     * @param request HttpServletRequest
     * @return int
     */
    public static int getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.parseInt(session.getAttribute("userId").toString());
    }

    /**
     * <h2>getLoginUserRole</h2>
     * <p>
     * Get Logged In User Role
     * </p>
     *
     * @param request HttpServletRequest
     * @return int
     */
    public static int getLoginUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.parseInt(session.getAttribute("userRole").toString());
    }

    /**
     * <h2>isDataNullOrEmpty</h2>
     * <p>
     * Check whether if Data is null or not
     * </p>
     *
     * @param data String
     * @return boolean
     */
    public static boolean isDataNullOrEmpty(String data) {
        return data == null || data.isEmpty();
    }

    /**
     * <h2>getFileName</h2>
     * <p>
     * Get File Name
     * </p>
     *
     * @param part Part
     * @return String
     */
    public static String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
