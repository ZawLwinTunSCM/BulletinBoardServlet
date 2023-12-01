<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404</title>
<style type="text/css">
* {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}

#not-found {
  height: 100vh;
}

#not-found {
  position: relative;
  height: 60vh;
}

#not-found .not-found {
  position: absolute;
  left: 50%;
  top: 80%;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}

.not-found {
  max-width: 520px;
  width: 100%;
  text-align: center;
  line-height: 1.4;
}

.not-found .not-found-404 {
  height: 190px;
}

.not-found .not-found-404 h1 {
  font-family: 'Montserrat', sans-serif;
  font-size: 146px;
  font-weight: 700;
  margin: 0px;
  color: #232323;
}

.not-found .not-found-404 h1>span {
  display: inline-block;
  width: 120px;
  height: 120px;
  background-image: url('../resources/img/emoji.png');
  background-size: cover;
  -webkit-transform: scale(1.4);
  -ms-transform: scale(1.4);
  transform: scale(1.4);
  z-index: -1;
}

.not-found h2 {
  font-family: 'Montserrat', sans-serif;
  font-size: 22px;
  font-weight: 700;
  margin: 0;
  text-transform: uppercase;
  color: #232323;
}

.not-found h2:after, .not-found h2:before, .not-found .not-found-404 h1:after,
  .not-found .not-found-404 h1:before {
  display: none;
}

.not-found p {
  font-family: 'Montserrat', sans-serif;
  color: #787878;
  font-weight: 300;
}

.not-found a {
  font-family: 'Montserrat', sans-serif;
  display: inline-block;
  padding: 12px 30px;
  font-weight: 700;
  background-color: #f99827;
  color: #fff;
  border-radius: 40px;
  text-decoration: none;
  -webkit-transition: 0.2s all;
  transition: 0.2s all;
}

.not-found a:hover {
  opacity: 0.8;
}
</style>
</head>
<body>
  <div id="not-found">
    <div class="not-found">
      <div class="not-found-404">
        <h1>
          4<span></span>4
        </h1>
      </div>
      <h2 id="colorlib_404_customizer_page_heading">OOPS! PAGE NOT
        FOUND.</h2>
      <p id="colorlib_404_customizer_content">Sorry but the page you
        are looking does not exist, have been removed, name changed or
        is temporarily unavailable.</p>
      <a
        href="${pageContext.request.contextPath}${empty sessionScope.userId ? '/auth/loginPage' : '/post/list' }">
        Go Back </a>
    </div>
  </div>
</body>
</html>