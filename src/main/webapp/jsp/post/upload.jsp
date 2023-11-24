<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jsp/common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Post Upload</title>
</head>
<body class="d-flex flex-column vh-100">
  <div
    class="container-fluid flex-grow-1 d-flex align-items-center justify-content-center">
    <div class="card col-md-4">
      <div class="card-header text-center">
        <h2>Post Upload</h2>
      </div>
      <div class="card-body">
        <c:if test="${errorMsg != null}">
          <div class="alert alert-danger" id="errorMessage">${errorMsg}</div>
        </c:if>
        <c:if test="${successMsg != null}">
          <div class="alert alert-success" id="successMessage">${successMsg}</div>
        </c:if>
        <form
          action="${pageContext.request.contextPath}/post/uploadPost"
          method="post" enctype="multipart/form-data">

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="name">File</label> <input
              type="file" class="form-control" id="file" accept=".csv"
              name="file" required />
          </fieldset>

          <div class="row justify-content-center">
            <button type="submit" class="btn btn-primary col-2 mx-2">Upload</button>
            <a
              href="<%=request.getContextPath()%>/post/downloadTemplate"
              class="btn btn-primary col-5">Download Template</a> <a
              href="<%=request.getContextPath()%>/post/list"
              class="btn btn-dark col-2 mx-2">Back</a>
          </div>
        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
