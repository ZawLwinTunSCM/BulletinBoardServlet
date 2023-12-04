package com.bulletin.board.common;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

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
     * <h2>FORGOT_PASSWORD_JSP</h2>
     * <p>
     * FORGOT_PASSWORD_JSP
     * </p>
     */
    public static final String FORGOT_PASSWORD_JSP = "/jsp/forgotPassword.jsp";

    /**
     * <h2>RESET_PASSWORD_JSP</h2>
     * <p>
     * RESET_PASSWORD_JSP
     * </p>
     */
    public static final String RESET_PASSWORD_JSP = "/jsp/resetPassword.jsp";

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
        Object id = session.getAttribute("userId");
        return id == null ? 0 : Integer.parseInt(id.toString());
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
        Object role = session.getAttribute("userRole");
        return role == null ? 3 : Integer.parseInt(role.toString());
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

    public static void sendMail(String email, int id) {
        String receiverEmail = "";
        String senderEmail = ""; // gmail
        String senderPassword = ""; // app password
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject("Password Reset");
            String resetLink = "http://localhost:8080/ServletBulletinBoard/auth/resetPassword?id=" + id;
            String emailContent = "Dear User,\n\n" + "To reset your password, please click on the following link:\n"
                    + resetLink + "\n\n" + "If you didn't request a password reset, please ignore this email.\n\n"
                    + "Best regards,\nYour Bulletin Board Team";

            message.setText(emailContent);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
