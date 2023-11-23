<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<html>
<head>
<title>Post Register</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card col-md-4">
    <div class="card-header text-center">
        <h2>${post != null ? 'Edit ' : 'Add New '}Post</h2>
    </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/post/${post != null ? 'update' : 'insert'}"
          method="post">

          <c:if test="${post != null}">
            <input type="hidden" name="id" value="${post.id}" />
          </c:if>

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="title">Title</label> <input
              type="text" value="${post.title}" class="form-control"
              name="title" id="title" required>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium">Description</label>
            <textarea class="form-control" name="description" required>${post.description}</textarea>
          </fieldset>

          <fieldset class="form-group mb-3">
            <label class="fw-medium">Status</label><br>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="status"
                ${post.status == 0 ? 'checked':'' } id="public"
                value="0" required> <label
                class="form-check-label" for="public">Public</label>
            </div>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="status"
                ${post.status == 1? 'checked':'' } id="private"
                value="1"> <label class="form-check-label"
                for="private">Private</label>
            </div>
          </fieldset>

          <div class="row justify-content-center">
            <button type="submit" class="btn btn-primary col-2 mx-2">${post != null ? 'Update' : 'Add'}</button>
            <a href="<%=request.getContextPath()%>/post/list"
              class="btn btn-dark col-2 mx-2">Back</a>
          </div>

        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
