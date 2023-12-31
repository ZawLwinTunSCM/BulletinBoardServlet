<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>User Detail</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card" style="width: 40%">
      <div class="card-header text-center">
        <h2 class="my-0">User Detail</h2>
      </div>
      <div class="card-body">
        <div class="row mb-3 justify-content-center">
          <img alt="Profile"
            src="${pageContext.request.contextPath}/resources/img/${user.profile}"
            class="w-50">
        </div>
        <div class="row">
          <div class="col-md-4">Name :</div>
          <div class="col-md-8">${user.name}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Email :</div>
          <div class="col-md-8">${user.email}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Phone :</div>
          <div class="col-md-8">${user.phone}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Address :</div>
          <div class="col-md-8">${user.address}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Role :</div>
          <div class="col-md-8">${user.role == 0 ? 'Admin' : 'User'}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Date of Birth :</div>
          <div class="col-md-8">${user.dob}</div>
        </div>
      </div>
      <div class="card-footer text-center">
        <c:if
          test="${sessionScope.userRole ==0 || sessionScope.userId == user.id}">
          <a href="edit?id=${user.id}"
            class="btn btn-primary mx-3">Edit</a>
        </c:if>
        <a href="${pageContext.request.contextPath}/user/list"
          class="btn btn-dark mx-3">Back</a>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
