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
    <div class="card col-md-4">
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
              value="<%=request.getContextPath()%>/resources/img/${user.profile}" />
          </c:if>

          <div id="image-container" class="text-center mb-3"
            style="display: none">
            <img id="image-preview" class="w-25" alt="Image Preview">
          </div>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3 required" for="profile">Profile</label>
            <div class="col-md-9">
              <input type="file" class="form-control" id="profile"
                accept="image/*" onchange="previewImage()"
                value="${user.profile}" name="profile"
                ${user.profile ==null ? 'required': ''} />
            </div>
          </div>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3 required" for="name">Name</label>
            <div class="col-md-9">
              <input type="text" value="${user.name}"
                class="form-control" name="name" id="name" required>
            </div>
          </div>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3 required" for="email">Email</label>
            <div class="col-md-9">
              <input type="email" value="${user.email}"
                class="form-control" name="email" id="email" required>
            </div>
          </div>

          <c:if test="${type != 'edit'}">
            <div class="row mb-3 align-items-center">
              <label class="fw-medium col-md-3 required" for="password">Password</label>
              <div class="col-md-9">
                <input type="password" class="form-control"
                  value="${user.password}" name="password" id="password"
                  required>
              </div>
            </div>

            <div class="row align-items-center">
              <label class="fw-medium col-md-3 required"
                for="confirmPassword">Confirm Password</label>
              <div class="col-md-9">
                <input type="password" class="form-control"
                  value="${user.confirmPassword}" name="confirmPassword"
                  id="confirmPassword" required>
              </div>
            </div>
            <div class="row mb-3 align-items-center">
              <div class="col-md-3"></div>
              <div class="col-md-9">
                <span class="text-danger">${err}</span>
              </div>
            </div>
          </c:if>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3" for="phone">Phone</label>
            <div class="col-md-9">
              <input type="number" value="${user.phone}"
                class="form-control" name="phone" id="phone"
                oninput="validatePhoneNumber()">
            </div>
          </div>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3" for="address">Address</label>
            <div class="col-md-9">
              <input type="text" value="${user.address}"
                class="form-control" name="address" id="address">
            </div>
          </div>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3" for="dob"
              data-bs-toggle="tooltip" data-bs-placement="left"
              data-bs-title="Date of Birth">DOB</label>
            <div class="col-md-9">
              <input type="date" value="${user.dob}"
                class="form-control" name="dob" id="dob">
            </div>
          </div>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3 required">Role</label>
            <div class="col-md-9">
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
              href="<%=request.getContextPath()%>${empty sessionScope.userRole ? '/loginPage' : '/user/list'}"
              class="btn btn-dark mx-2">Back</a>
          </div>

        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
