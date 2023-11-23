package com.bulletin.board.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Common {

    public static boolean isValidRole(Object role) {
        return !(role == null || role == "");
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
}
