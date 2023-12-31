<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Post List</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex justify-content-center ${empty listPost && empty searchData ? 'align-items-center ' : ''}py-3">
    <c:choose>
      <c:when test="${empty listPost && empty searchData}">
        <div class="col-md-4 text-center">
          <h3>No New Post.</h3>
          <a href="${pageContext.request.contextPath}/post/new"
            class="btn btn-primary mx-3">Add New Post</a> <a
            href="${pageContext.request.contextPath}/post/upload"
            class="btn btn-primary mx-3">Upload</a>
        </div>
      </c:when>
      <c:when test="${empty listPost && not empty searchData}">
        <div class="col-md-8">
          <h3 class="text-center">List of Posts</h3>
          <hr>
          <div class="row mt-3">
            <div class="col-md-4 d-flex justify-content-between">
              <a href="${pageContext.request.contextPath}/post/new"
                class="btn btn-primary">Add New Post</a> <a
                href="${pageContext.request.contextPath}/post/upload"
                class="btn btn-primary">Upload</a> <a
                href="${pageContext.request.contextPath}/post/download"
                class="btn btn-primary">Download</a>
            </div>
            <div class="col-md-4"></div>
            <form class="col-md-4 d-flex"
              action="${pageContext.request.contextPath}/post/search"
              method="post">
              <input type="text" class="form-control" id="search"
                name="searchData" placeholder="Search"
                value="${searchData}">
              <button type="submit" class="btn btn-primary ms-1">Search</button>
            </form>
          </div>
          <br>
          <h3 class="text-center mt-5">There is no such Post.</h3>
        </div>
      </c:when>
      <c:when test="${not empty listPost}">
        <input type="hidden" id="total" value="${total}">
        <input type="hidden" id="pageNum" value="${pageNum}">
        <input type="hidden" id="type" value="${type}">
        <input type="hidden" id="limit" value="${limit}">
        <div class="col-md-8">
          <h3 class="text-center">List of Posts</h3>
          <hr>
          <div class="row mt-3">
            <div class="col-md-4 d-flex justify-content-between">
              <a href="${pageContext.request.contextPath}/post/new"
                class="btn btn-primary">Add New Post</a> <a
                href="${pageContext.request.contextPath}/post/upload"
                class="btn btn-primary">Upload</a><a
                href="${pageContext.request.contextPath}/post/download"
                class="btn btn-primary">Download</a>
            </div>
            <div class="col-md-4"></div>
            <form class="col-md-4 d-flex"
              action="${pageContext.request.contextPath}/post/search"
              method="post">
              <input type="text" class="form-control" id="search"
                name="searchData" placeholder="Search"
                value="${searchData}">
              <button type="submit" class="btn btn-primary ms-1">Search</button>
            </form>
          </div>
          <br>
          <table class="table table-bordered table-hover"
            style="table-layout: fixed;">
            <thead>
              <tr>
                <th class="col-1">No.</th>
                <th class="col-2">Title</th>
                <th class="col-3">Description</th>
                <th class="col-1">Author</th>
                <th class="col-2">Posted Date</th>
                <th class="text-center col-2">Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="post" items="${listPost}" varStatus="loop">
                <tr
                  style="vertical-align: middle; max-width: 50px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                  <td>${loop.count}</td>
                  <td class="text-truncate"><a
                    href="${pageContext.request.contextPath}/post/detail?id=${post.id}">${post.title}</a></td>
                  <td class="text-truncate">${post.description}</td>
                  <td class="text-truncate">${post.author}</td>
                  <td>${post.createdAt}</td>
                  <td class="d-flex justify-content-center"><a
                    href="edit?id=${post.id}"
                    class="btn mx-3 ${sessionScope.userRole ==0 || sessionScope.userId == post.createdUserId ? 'btn-primary':'btn-secondary disabled'}"><i
                      class="fa-solid fa-pen-to-square"></i></a>
                    
                    <button type="button" data-bs-toggle="modal"
                      data-bs-target="#deleteModal"
                      onclick="addLink(${post.id})"
                      class="btn mx-3 ${sessionScope.userRole ==0 || sessionScope.userId == post.createdUserId ? 'btn-danger':'btn-secondary disabled'}">
                      <i class="fa-solid fa-trash"></i>
                    </button></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>

          <div class="row">
            <div class="col-md-7 d-flex align-items-center">
              <label class="me-1">Page Size:</label>
              <div>
                <select class="form-select" name="limit"
                  onchange="redirectToPage(this.value)">
                  <option
                    value="${pageContext.request.contextPath}/post/${type}?pageNumber=${pageNum}&limit=5"
                    ${limit == 5 ? 'selected' : '' }>5</option>
                  <option
                    value="${pageContext.request.contextPath}/post/${type}?pageNumber=${pageNum}&limit=10"
                    ${limit == 10 ? 'selected' : '' }>10</option>
                  <option
                    value="${pageContext.request.contextPath}/post/${type}?pageNumber=${pageNum}&limit=15"
                    ${limit == 15 ? 'selected' : '' }>15</option>
                </select>
              </div>
              <div class="ms-1">Showing ${((pageNum - 1) * limit) + 1}
                to ${pageNum * limit > total ? total : pageNum * limit}
                of ${total} entries.</div>
            </div>
            <div id="pagination"
              class="col-md-5 d-flex justify-content-end"></div>
          </div>
        </div>
      </c:when>
    </c:choose>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
  <div class="modal fade" id="deleteModal" tabindex="-1"
    aria-labelledby="deleteModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-sm">
      <div class="modal-content text-center text-danger">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="deleteModal">
            <i class="fa-solid fa-triangle-exclamation"></i>&nbsp;&nbsp;Delete
            Post?
          </h1>
          <button type="button" class="btn-close"
            data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <span>Are You Sure To Delete?</span>
        </div>
        <div class="modal-footer justify-content-center">
          <button type="button" class="btn btn-dark"
            data-bs-dismiss="modal">Close</button>
          <a id="deleteLink" class="btn btn-danger">Delete</a>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
