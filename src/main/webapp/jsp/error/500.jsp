<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>
<style type="text/css">
body {
  background-color: rgb(51, 51, 51);
  width: 100vw;
  height: 100vh;
  color: white;
  font-family: 'Arial Black';
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.error-num {
  font-size: 8em;
}

.eye {
  background: white;
  border-radius: 50%;
  display: inline-block;
  height: 100px;
  position: relative;
  width: 100px;
  display: inline-block;
  height: 100px;
  position: relative;
}

.eye::after {
  background: #000;
  border-radius: 50%;
  bottom: 56.1px;
  content: '';
  height: 33px;
  position: absolute;
  right: 33px;
  width: 33px;
}

p {
  margin-bottom: 4em
}

a {
  color: white;
  text-decoration: none;
  text-transform: uppercase;
}

a::hover {
  color: lightgray
}
</style>
</head>
<body>
  <div>
    <span class='error-num'>5</span>
    <div class='eye'></div>
    <div class='eye'></div>
    <p class='sub-text'>
      Something went wrong. We're <i>looking</i> to see what happened.
    </p>
    <a
      href="<%=request.getContextPath()%>${empty sessionScope.userId ? '/auth/loginPage' : '/post/list' }">
      Go Back </a>
  </div>
  <script type="text/javascript">
            $('body').mousemove(function(event) {
                var e = $('.eye');
                var x = (e.offset().left) + (e.width() / 2);
                var y = (e.offset().top) + (e.height() / 2);
                var rad = Math.atan2(event.pageX - x, event.pageY - y);
                var rot = (rad * (180 / Math.PI) * -1) + 180;
                e.css({
                    '-webkit-transform' : 'rotate(' + rot + 'deg)',
                    'transform' : 'rotate(' + rot + 'deg)'
                });
            });
        </script>
</body>
</html>