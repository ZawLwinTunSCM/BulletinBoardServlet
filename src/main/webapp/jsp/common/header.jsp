<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<link
  href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
  rel="stylesheet"
  integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
  crossorigin="anonymous">
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
  rel="stylesheet">
<script
  src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
  integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
  crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.js"
  type="text/javascript"></script>
<script
  src="${pageContext.request.contextPath}/resources/js/pagination.js"
  type="text/javascript"></script>
<body>
  <header>
    <nav class="navbar navbar-expand-md navbar-dark"
      style="background-color: tomato">
      <div class="container-fluid text-white">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/">
          MTM Bulletin Board </a>
        <c:if test="${not empty sessionScope.userName}">
          <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
            <li class="nav-item"><a
              class="nav-link me-5 text-white"
              href="<%=request.getContextPath()%>/post/list">Posts</a></li>
            <li class="nav-item"><a
              class="nav-link me-5 text-white"
              href="<%=request.getContextPath()%>/user/list">Users</a></li>
            <li class="nav-item dropdown"><a
              class="nav-link dropdown-toggle text-white" href="#"
              id="navbarDropdown" role="button"
              data-bs-toggle="dropdown" aria-expanded="false">
                ${sessionScope.userName} </a>
              <ul class="dropdown-menu dropdown-menu-end"
                aria-labelledby="navbarDropdown">
                <li><a
                  class="me-5 text-dark text-decoration-none dropdown-item"
                  href="<%=request.getContextPath()%>/user/passChange">Password
                    Change</a></li>
                <li><a
                  class="me-5 text-dark text-decoration-none dropdown-item"
                  href="<%=request.getContextPath()%>/auth/logout">Logout</a>
                </li>
              </ul></li>
          </ul>
        </c:if>
      </div>
    </nav>
  </header>

</body>
</html>