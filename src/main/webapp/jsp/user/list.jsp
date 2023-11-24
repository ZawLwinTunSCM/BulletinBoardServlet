<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User List</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex justify-content-center ${empty listUser && empty searchData ? 'align-items-center' : ''} pt-5">
    <c:choose>
      <c:when test="${empty listUser && empty searchData}">
        <div class="col-md-4 text-center">
          <h3>No New User.</h3>
          <a href="<%=request.getContextPath()%>/user/new"
            class="btn btn-primary">Add New User</a>
        </div>
      </c:when>
      <c:when test="${empty listUser && not empty searchData}">
        <div class="col-md-8">
          <h3 class="text-center">List of Users</h3>
          <hr>
          <div class="row mt-3">
            <div class="col-md-4 d-flex justify-content-between">
              <a href="<%=request.getContextPath()%>/user/new"
                class="btn btn-primary">Add New User</a> 
            </div>
            <div class="col-md-4"></div>
            <form class="col-md-4 d-flex"
              action="${pageContext.request.contextPath}/user/search"
              method="post">
              <input type="text" class="form-control" id="search"
                name="searchData" placeholder="Search"
                value="${searchData}">
              <button type="submit" class="btn btn-primary ms-1">Search</button>
            </form>
          </div>
          <br>
          <h3 class="text-center mt-5">There is no such user.</h3>
        </div>
      </c:when>
      <c:when test="${not empty listUser}">
      <input type="hidden" id="total" value="${total}">
      <input type="hidden" id="pageNum" value="${pageNum}">
      <input type="hidden" id="type" value="${type}">
        <div class="col-md-8">
          <h3 class="text-center">List of Users</h3>
          <hr>
          <div class="row mt-3">
            <div class="col-md-4 d-flex justify-content-between">
              <a href="<%=request.getContextPath()%>/user/new"
                class="btn btn-primary">Add New User</a>
            </div>
            <div class="col-md-4"></div>
            <form class="col-md-4 d-flex"
              action="${pageContext.request.contextPath}/user/search"
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
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Date of Birth</th>
                <th>Role</th>
                <th class="text-center col-3">Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="user" items="${listUser}" varStatus="loop">
                <tr
                  style="vertical-align: middle; max-width: 50px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                  <td><c:out value="${loop.count}" /></td>
                  <td class="text-truncate"><a
                    href="<%=request.getContextPath()%>/user/detail?id=${user.id}"><c:out
                        value="${user.name}" /></a></td>
                  <td class="text-truncate"><c:out
                      value="${user.email}" /></td>
                  <td class="text-truncate"><c:out
                      value="${user.phone}" /></td>
                  <td><c:out
                      value="${user.dob}" /></td>
                  <td><c:out value="${user.role == 0 ? 'Admin' : 'User'}" /></td>
                  <td class="d-flex justify-content-between"><a
                    href="edit?id=<c:out value='${user.id}'/>"
                    class="btn col-5 ms-2 ${sessionScope.userRole ==0 || sessionScope.userId == user.id ? 'btn-primary':'btn-secondary disabled'}">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <button type="button" data-bs-toggle="modal"
                      data-bs-target="#deleteModal"
                      onclick="addLink(${user.id})"
                      class="btn col-5 me-2 ${sessionScope.userRole ==0 || sessionScope.userId == user.id ? 'btn-danger' :'btn-secondary disabled'}">Delete</button></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          <div id="pagination"></div>
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
            User?
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
