<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>User ${type == 'edit' ? 'Edit' : 'Registration' }</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center my-3">
    <div class="card col-md-8">
      <div class="card-header text-center">
        <h2 class="my-0">${type == 'edit' ? 'Edit ' : 'Add New '}User</h2>
      </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/user/${type == 'edit' ? 'update' : 'insert'}"
          method="post" enctype="multipart/form-data">

          <c:if test="${type == 'edit' }">
            <input type="hidden" name="id" value="${user.id}" />
            <input type="hidden" id="img" name="img"
              value="${pageContext.request.contextPath}/resources/img/${user.profile}" />
          </c:if>



          <div class="row mb-3 align-items-center">
            <div class="col-md-6 d-flex justify-content-center">
              <div id="image-container" class="text-center">
                <img id="image-preview" class="w-100 h-100"
                  alt="Image Preview"
                  src="${pageContext.request.contextPath}/resources/img/profile.png">
              </div>

            </div>
            <div class="col-md-6">
              <label class="fw-medium required" for="profile">Profile</label>
              <input type="file" class="form-control" id="profile"
                accept="image/*" onchange="previewImage()"
                value="${user.profile}" name="profile"
                ${user.profile ==null ? 'required': ''} />
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-md-6">
              <label class="fw-medium required" for="name">Name</label><input
                type="text" value="${user.name}" class="form-control"
                name="name" id="name" required>
            </div>
            <div class="col-md-6">
              <label class="fw-medium" for="phone">Phone</label> <input
                type="number" value="${user.phone}" class="form-control"
                name="phone" id="phone" oninput="validatePhoneNumber()">
            </div>
          </div>

          <div class="row mb-3">
            <div class="col-md-6">
              <label class="fw-medium required" for="email">Email</label>
              <input type="email" value="${user.email}"
                class="form-control" name="email" id="email" required><span
                  class="text-danger">${errEmail}</span>
            </div>
            <div class="col-md-6">
              <label class="fw-medium" for="address">Address</label> <input
                type="text" value="${user.address}" class="form-control"
                name="address" id="address">
            </div>
          </div>

          <c:if test="${type != 'edit'}">
            <div class="row mb-3">
              <div class="col-md-6">
                <label class="fw-medium required" for="password">Password</label>
                <input type="password" class="form-control"
                  value="${user.password}" name="password" id="password"
                  required>
              </div>
              <div class="col-md-6">
                <label class="fw-medium required" for="confirmPassword">Confirm
                  Password</label> <input type="password" class="form-control"
                  value="${user.confirmPassword}" name="confirmPassword"
                  id="confirmPassword" required> <span
                  class="text-danger">${err}</span>
              </div>
            </div>
          </c:if>

          <div class="row mb-3">
            <div class="col-md-6">
              <label class="fw-medium" for="dob"
                data-bs-toggle="tooltip" data-bs-placement="left"
                data-bs-title="Date of Birth">DOB</label> <input
                type="date" value="${user.dob}" class="form-control"
                name="dob" id="dob">
            </div>
            <div class="col-md-6">
              <label class="fw-medium required mb-2">Role</label><br>
              <c:if test="${sessionScope.userRole == 0}">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio"
                    name="role" ${user.role == 0 ? 'checked':'' }
                    id="admin" value="0" required> <label
                    class="form-check-label" for="admin">Admin</label>
                </div>
              </c:if>
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="role"
                  ${user.role == 1 || empty sessionScope.userRole || sessionScope.userRole == 1 ? 'checked':'' }
                  id="user" value="1"> <label
                  class="form-check-label" for="user">User</label>
              </div>
            </div>
          </div>

          <div class="text-center">
            <button type="submit" class="btn btn-primary mx-2">${type == 'edit' ? 'Update' : 'Add'}</button>
            <a
              href="${pageContext.request.contextPath}${empty sessionScope.userRole ? '/loginPage' : '/user/list'}"
              class="btn btn-dark mx-2">Back</a>
          </div>

        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
