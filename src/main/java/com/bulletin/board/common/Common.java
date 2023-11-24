package com.bulletin.board.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class Common {
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_USER_NAME = "userName";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final String SESSION_SEARCH_DATA = "search";

    public static final String LOGIN_JSP = "/jsp/login.jsp";
    public static final String LOGIN_POST_LIST_URL = "/post/list";

    public static final String POST_INSERT_JSP = "/jsp/post/insert.jsp";
    public static final String POST_LIST_URL = "/jsp/post/list.jsp";
    public static final String POST_UPLOAD_URL = "/jsp/post/upload.jsp";
    public static final String POST_DETAIL_URL = "/jsp/post/detail.jsp";

    public static final String USER_INSERT_JSP = "/jsp/user/insert.jsp";
    public static final String USER_LIST_URL = "/jsp/user/list.jsp";
    public static final String USER_DETAIL_URL = "/jsp/user/detail.jsp";
    public static final String USER_PASS_CHANGE_URL = "/jsp/user/passChange.jsp";

    public static boolean isValidRole(Object role) {
        return role != null && !"".equals(role);
    }

    public static void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    public static void redirectToPage(String page, HttpServletResponse response) throws IOException {
        response.sendRedirect(page);
    }

    public static void error403(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToPage("/jsp/error/403.jsp", request, response);
    }

    public static void error404(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToPage("/jsp/error/404.jsp", request, response);
    }

    public static int getLoginUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.parseInt(session.getAttribute("userId").toString());
    }

    public static int getLoginUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Integer.parseInt(session.getAttribute("userRole").toString());
    }

    public static boolean isDataNullOrEmpty(String data) {
        return data == null || data.isEmpty();
    }

    public static String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
