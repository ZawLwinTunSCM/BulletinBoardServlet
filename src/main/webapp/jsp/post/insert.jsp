<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Post ${type == 'edit' ? 'Edit ' : 'Registration'}</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card col-md-4">
      <div class="card-header text-center">
        <h2 class="my-0">${type == 'edit' ? 'Edit ' : 'Add New '}Post</h2>
      </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/post/${type == 'edit' ? 'update' : 'insert'}"
          method="post">

          <c:if test="${type == 'edit'}">
            <input type="hidden" name="id" value="${post.id}" />
          </c:if>

          <div class="mb-3 align-items-center">
            <label class="fw-medium required" for="title">Title</label>
            <input type="text" value="${post.title}"
              class="form-control" name="title" id="title" required><span
                  class="text-danger">${err}</span>
          </div>

          <div class="mb-3 align-items-center">
            <label class="fw-medium required" for="description">Description</label>
            <textarea class="form-control" name="description"
              id="description" required>${post.description}</textarea>
          </div>

          <div class="form-group mb-3">
            <label class="fw-medium required">Status</label><br>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="status"
                ${post.status == 1 ? 'checked':'' } id="public"
                value="1" required> <label
                class="form-check-label" for="public">Public</label>
            </div>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="status"
                ${post.status == 0? 'checked':'' } id="private"
                value="0"> <label class="form-check-label"
                for="private">Private</label>
            </div>
          </div>

          <div class="text-center">
            <button type="submit" class="btn btn-primary mx-2">${type == 'edit' ? 'Update' : 'Add'}</button>
            <a href="${pageContext.request.contextPath}/post/list"
              class="btn btn-dark mx-2">Back</a>
          </div>

        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
