<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<html>
<head>
<title>Login</title>
</head>
<body class="d-flex flex-column vh-100">
  <c:if test="${not empty sessionScope.userId}">
    <%
    response.sendRedirect(request.getContextPath() + "/post/list");
    %>
  </c:if>
  <c:if test="${empty sessionScope.userId}">
    <div
      class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
      <div class="card col-md-4">
        <div class="card-header">
          <h2 class="text-center">Login</h2>
        </div>
        <div class="card-body">
          <c:if test="${errorMsg != null}">
            <div class="alert alert-danger" id="errorMessage">${errorMsg}</div>
          </c:if>
          <c:if test="${successMsg != null}">
            <div class="alert alert-success" id="successMsg">${successMsg}</div>
          </c:if>
          <form action="${pageContext.request.contextPath}/auth/login"
            method="post">

            <fieldset class="form-group mb-3">
              <label class="fw-medium" for="title">Email</label> <input
                type="email" class="form-control" name="email" required>
            </fieldset>

            <fieldset class="form-group mb-3">
              <label class="fw-medium" for="title">Password</label> <input
                type="password" class="form-control" name="password"
                required>
            </fieldset>

            <div class="row justify-content-center text-center">
              <button type="submit" class="btn btn-primary col-2 mx-2 mb-2">Login</button>
              <a href="<%=request.getContextPath()%>/user/new" class="text-decoration-none">
    Create Account 
    <i class="fa-solid fa-user-plus"></i>
</a>

            </div>

          </form>
        </div>
      </div>
    </div>
  </c:if>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
