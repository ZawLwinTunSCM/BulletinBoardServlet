<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Post Detail</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card" style="width: 30%">
      <div class="card-header bg-primary text-white">
        <h2 class="text-center mb-0">Post Detail</h2>
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-md-4">Title :</div>
          <div class="col-md-8">${post.title}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Description :</div>
          <div class="col-md-8">${post.description}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Author :</div>
          <div class="col-md-8">${post.author}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Posted Date :</div>
          <div class="col-md-8">${post.createdAt}</div>
        </div>
        <hr class="my-2">
        <div class="row">
          <div class="col-md-4">Status :</div>
          <div class="col-md-8">${post.status == 1 ? 'Public' : 'Private'}</div>
        </div>
      </div>
      <div class="card-footer text-center">
        <c:if
          test="${sessionScope.userRole ==0 || sessionScope.userId == post.createdUserId}">
          <a href="edit?id=${post.id}"
            class="btn btn-primary col-2 mx-3">Edit</a>
        </c:if>
        <a href="<%=request.getContextPath()%>/post/list"
          class="btn btn-dark col-2 mx-3">Back</a>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
