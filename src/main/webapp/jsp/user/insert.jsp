<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>User Register</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card col-md-4">
      <div class="card-header text-center">
        <h2>${user != null ? 'Edit ' : 'Add New '}User</h2>
      </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/user/${user != null ? 'update' : 'insert'}"
          method="post" enctype="multipart/form-data">

          <c:if test="${user != null}">
            <input type="hidden" name="id" value="${user.id}" />
            <input type="hidden" id="img" name="img"
              value="<%=request.getContextPath()%>/resources/img/${user.profile}" />
          </c:if>
          <div id="image-container" class="text-center"
            style="display: none">
            <img id="image-preview" class="w-50" alt="Image Preview">
          </div>

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="name">Profile</label> <input
              type="file" class="form-control" id="profile"
              accept="image/*" onchange="previewImage()" name="profile"
              ${user.profile ==null ? 'required': ''} />
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="name">Name</label> <input
              type="text" value="${user.name}" class="form-control"
              name="name" id="name" required>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="email">Email</label> <input
              type="email" value="${user.email}" class="form-control"
              name="email" id="email" required>
          </fieldset>

          <c:if test="${user == null}">
            <fieldset class="form-group mb-3">
              <label class="fw-medium" for="password">Password</label> <input
                type="password" value="${user.password}"
                class="form-control" name="password" id="password"
                required>
            </fieldset>
          </c:if>

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="phone">Phone</label> <input
              type="text" value="${user.phone}" class="form-control"
              name="phone" id="phone" required>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="address">Address</label> <input
              type="text" value="${user.address}" class="form-control"
              name="address" id="address" required>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="dob">Date Of Birth</label> <input
              type="date" value="${user.dob}" class="form-control"
              name="dob" id="dob" required>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium">Role</label><br>
            <c:if
              test="${sessionScope.userRole == 0}">
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="role"
                  ${user.role == 0 ? 'checked':'' } id="admin" value="0"
                  required> <label class="form-check-label"
                  for="admin">Admin</label>
              </div>
            </c:if>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="role"
                ${user.role == 1 || empty sessionScope.userRole || sessionScope.userRole == 1 ? 'checked':'' }
                id="user" value="1"> <label
                class="form-check-label" for="user">User</label>
            </div>
          </fieldset>

          <div class="row justify-content-center">
            <button type="submit" class="btn btn-primary col-2 mx-2">${user != null ? 'Update' : 'Add'}</button>
            <a href="<%=request.getContextPath()%>${empty sessionScope.userRole ? '/loginPage' : '/user/list'}"
              class="btn btn-dark col-2 mx-2">Back</a>
          </div>

        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
