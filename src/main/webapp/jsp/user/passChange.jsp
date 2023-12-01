<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Password Change</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card col-md-4">
      <div class="card-header text-center">
        <h2 class="my-0">Password Change</h2>
      </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/user/changePassword"
          method="post">
          <input type="hidden" name="id" value="${sessionScope.userId}" />

          <fieldset class="form-group mb-3">
            <label class="fw-medium required" for="oldPass">Old Password</label> <input
              type="password" class="form-control" name="oldPass" id="oldPass" value="${oldPass}"
              required>
              <span class="text-danger">${errOldPass}</span>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium required" for="newPass">New Password</label> <input
              type="password" class="form-control" name="newPass" id="newPass" value="${newPass}"
              required>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium required" for="conNewPass">Confirm New Password</label> <input
              type="password" class="form-control" name="conNewPass" id="conNewPass" value="${conNewPass}"
              required>
              <span class="text-danger">${errConNewPass}</span>
          </fieldset>

          <div class="text-center">
            <button type="submit" class="btn btn-primary mx-2">Change</button>
            <a href="<%=request.getContextPath()%>/user/list"
              class="btn btn-dark mx-2">Back</a>
          </div>

        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
