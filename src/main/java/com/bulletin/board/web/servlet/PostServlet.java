package com.bulletin.board.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.bulletin.board.bl.dto.PostDTO;
import com.bulletin.board.bl.service.post.PostService;
import com.bulletin.board.bl.service.post.impl.PostServiceImpl;
import com.bulletin.board.common.Common;
import com.bulletin.board.web.form.PostForm;
import com.opencsv.CSVReader;

@MultipartConfig
@WebServlet("/post/*")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PostService postService;

    public PostServlet() {
        super();
        this.postService = new PostServiceImpl();
    }

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
                Common.forwardToPage(Common.POST_INSERT_JSP, request, response);
                break;
            case "/insert":
                insertPost(request, response);
                break;
            case "/list":
                listPosts(request, response, false);
                break;
            case "/search":
                listPosts(request, response, true);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updatePost(request, response);
                break;
            case "/delete":
                deletePost(request, response);
                break;
            case "/detail":
                detailPost(request, response);
                break;
            case "/download":
                exportCSVPost(request, response);
                break;
            case "/downloadTemplate":
                downloadTemplate(request, response);
                break;
            case "/upload":
                Common.forwardToPage(Common.POST_UPLOAD_URL, request, response);
                break;
            case "/uploadPost":
                uploadPost(request, response);
                break;
            default:
                Common.error404(request, response);
                break;
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

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Object role = Common.getLoginUserRole(request);
        int id = Integer.parseInt(request.getParameter("id"));
        PostDTO post = postService.doGetPostById(id);
        int loginId = Common.getLoginUserId(request);
        if (Common.isValidRole(role)
                && (Integer.parseInt(role.toString()) == 0 || (post != null && post.getCreatedUserId() == loginId))) {
            request.setAttribute("post", post);
            Common.forwardToPage(Common.POST_INSERT_JSP, request, response);
        } else {
            Common.error403(request, response);
        }
    }

    private void insertPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        PostForm newPost = getPostParameters(request);
        int id = Common.getLoginUserId(request);
        newPost.setCreatedUserId(id);
        newPost.setUpdatedUserId(id);
        postService.doInsertPost(newPost);
        Common.redirectToPage("list", response);
    }

    private void listPosts(HttpServletRequest request, HttpServletResponse response, boolean isSearch)
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
        int id = Common.getLoginUserId(request);
        List<PostDTO> posts = postService.doGetAllPosts(id, searchData, pageNumber);
        request.setAttribute("listPost", posts);
        request.setAttribute("searchData", searchData);
        request.setAttribute("pageNum", pageNumber);
        request.setAttribute("type", isSearch ? "search" : "list");
        request.setAttribute("total", postService.doGetTotalCount(id, searchData));
        Common.forwardToPage(Common.POST_LIST_URL, request, response);
    }

    private void detailPost(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        PostDTO post = postService.doGetPostById(id);
        request.setAttribute("post", post);
        Common.forwardToPage(Common.POST_DETAIL_URL, request, response);
    }

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        PostForm updatedPost = getPostParameters(request);
        updatedPost.setUpdatedUserId(Common.getLoginUserId(request));
        postService.doUpdatePost(updatedPost);
        Common.redirectToPage("list", response);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        postService.doDeletePost(id);
        Common.redirectToPage("list", response);
    }

    private PostForm getPostParameters(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int status = Integer.parseInt(request.getParameter("status"));
        return new PostForm(id, title, description, status, id, id);
    }

    private void exportCSVPost(HttpServletRequest request, HttpServletResponse response) {
        List<PostDTO> posts = postService.doGetPosts(Common.getLoginUserId(request));
        StringBuilder csvData = new StringBuilder();
        csvData.append("ID, Title, Description, Author, Status, Posted Date\n");
        for (int i = 0; i < posts.size(); i++) {
            PostDTO post = posts.get(i);
            String status = post.getStatus() == 0 ? "Private" : "Public";
            csvData.append(post.getId() + "," + post.getTitle() + "," + post.getDescription() + "," + post.getAuthor()
                    + "," + status + "," + post.getCreatedAt() + "\n");
        }
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=posts.csv");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(csvData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Path path = Paths.get(request.getServletContext().getRealPath("/") + "resources" + File.separator + "template"
                + File.separator + "template.csv");
        if (Files.exists(path) && Files.isRegularFile(path)) {
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + path.getFileName() + "\"");
            Files.copy(path, response.getOutputStream());
        } else {
            response.getWriter().println("File not found");
        }
    }

    private void uploadPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        List<PostForm> posts = new ArrayList<>();
        int id = Common.getLoginUserId(request);
        Part filePart = request.getPart("file");
        InputStream input = filePart.getInputStream();
        CSVReader csvReader = new CSVReader(new InputStreamReader(input));
        List<String[]> allData = csvReader.readAll();
        for (int i = 1; i < allData.size(); i++) {
            PostForm newPost = createPostFromCSVData(allData.get(i), request, response, i);
            if (newPost == null) {
                return;
            }
            newPost.setCreatedUserId(id);
            newPost.setUpdatedUserId(id);
            posts.add(newPost);
        }
        for (PostForm post : posts) {
            postService.doInsertPost(post);
        }
        request.setAttribute("successMsg", "Data are Successfully Uploaded!");
        Common.forwardToPage(Common.POST_UPLOAD_URL, request, response);
    }

    private PostForm createPostFromCSVData(String[] post, HttpServletRequest request, HttpServletResponse response,
            int row) throws IOException, ServletException {
        PostForm newPost = new PostForm();
        for (int j = 0; j < post.length; j++) {
            if (Common.isDataNullOrEmpty(post[j])) {
                request.setAttribute("errorMsg", "Data is missing at row : " + (row + 1) + " column : " + (j + 1));
                Common.forwardToPage(Common.POST_UPLOAD_URL, request, response);
                return null;
            }
            setPostField(newPost, j, post[j]);
        }
        return newPost;
    }

    private void setPostField(PostForm newPost, int columnIndex, String data) {
        switch (columnIndex) {
        case 0:
            newPost.setTitle(data);
            break;
        case 1:
            newPost.setDescription(data);
            break;
        default:
            newPost.setStatus(Integer.parseInt(data));
            break;
        }
    }
}
