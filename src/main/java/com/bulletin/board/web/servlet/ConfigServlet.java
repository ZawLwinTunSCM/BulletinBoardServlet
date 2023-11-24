package com.bulletin.board.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h2>ConfigServlet Class</h2>
 * <p>
 * Process for Displaying ConfigServlet
 * </p>
 * 
 * @author ZawLwinTun
 *
 */
@WebServlet("/")
public class ConfigServlet extends HttpServlet {
    /**
     * <h2>serialVersionUID</h2>
     * <p>
     * serialVersionUID
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * <h2>Constructor for ConfigServlet</h2>
     * <p>
     * Constructor for ConfigServlet
     * </p>
     */
    public ConfigServlet() {
        super();
    }

    /**
     * <h2>doGet</h2>
     * <p>
     * 
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
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    /**
     * <h2>doPost</h2>
     * <p>
     * 
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
}
