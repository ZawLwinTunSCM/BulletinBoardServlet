<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<html>
<head>
<title>Forgot Password</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card col-md-4">
      <div class="card-header">
        <h2 class="text-center">Forgot Password?</h2>
      </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/auth/forgotPass"
          method="post">

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="title">Email</label> <input
              type="email" class="form-control" name="email"
              value="${email}" required> <span
              class="text-danger">${err}</span>
          </fieldset>

          <div class="text-center">
            <button type="submit" class="btn btn-primary mx-2 mb-2">Send
              Reset Password Mail</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
