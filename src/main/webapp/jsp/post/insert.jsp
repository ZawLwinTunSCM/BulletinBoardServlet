<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Post ${post != null ? 'Edit ' : 'Registration'}</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card col-md-4">
      <div class="card-header text-center">
        <h2 class="my-0">${post != null ? 'Edit ' : 'Add New '}Post</h2>
      </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/post/${post != null ? 'update' : 'insert'}"
          method="post">

          <c:if test="${post != null}">
            <input type="hidden" name="id" value="${post.id}" />
          </c:if>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3 required" for="title">Title</label>
            <div class="col-md-9">
              <input type="text" value="${post.title}"
                class="form-control" name="title" id="title" required>
            </div>
          </div>

          <div class="row mb-3 align-items-center">
            <label class="fw-medium col-md-3 required" for="description">Description</label>
            <div class="col-md-9">
              <textarea class="form-control" name="description"
                id="description" required>${post.description}</textarea>
            </div>
          </div>

          <div class="row mb-3">
            <label class="fw-medium col-md-3 required">Status</label>
            <div class="col-md-9">
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio"
                  name="status" ${post.status == 1 ? 'checked':'' }
                  id="public" value="1" required> <label
                  class="form-check-label" for="public">Public</label>
              </div>
              <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio"
                  name="status" ${post.status == 0? 'checked':'' }
                  id="private" value="0"> <label
                  class="form-check-label" for="private">Private</label>
              </div>
            </div>
          </div>

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
