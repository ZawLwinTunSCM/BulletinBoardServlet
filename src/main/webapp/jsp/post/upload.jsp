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
        <h2 class="my-0">Post Upload</h2>
      </div>
      <div class="card-body">
        <form
          action="${pageContext.request.contextPath}/post/uploadPost"
          method="post" enctype="multipart/form-data">

          <fieldset class="form-group mb-3">
            <label class="fw-medium" for="name">File</label> <input
              type="file" class="form-control" id="file" accept=".csv"
              name="file" required />
          </fieldset>

          <div class="text-center">
            <button type="submit" class="btn btn-primary mx-2">Upload</button>
            <a
              href="${pageContext.request.contextPath}/post/downloadTemplate"
              class="btn btn-primary">Download Template</a> <a
              href="${pageContext.request.contextPath}/post/list"
              class="btn btn-dark mx-2">Back</a>
          </div>
        </form>
      </div>
    </div>
  </div>
  <%@ include file="/jsp/common/footer.jsp"%>
</body>
</html>
