<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="login.css">
</head>
<body>
<% System.out.println(session.getAttribute("uid"));%>
<nav class="navbar navbar-light navbar-expand-lg fixed-top shadow-sm bg-white"><a href="Home" class="navbar-brand">PC Part Picker</a>
    <button type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" class="navbar-toggler"><span class="navbar-toggler-icon"></span></button>
    <div id="navbarSupportedContent" class="collapse navbar-collapse">
            <ul class="navbar-nav ml-auto">
      	<c:choose>
		 <c:when test="${uid== null}">
		  <li class="nav-item"><a href="Home?page=login" class="nav-link">Login</a></li>
		  <li class="nav-item"><a href="Home?page=sign-up" class="nav-link">Sign-up</a></li>
		 </c:when>
		 <c:when test="${uid!= null}">
		  <li class="nav-item"><a href="Home?page=logout" style="color: #F24638;" class="nav-link">Logout</a></li>
		  <li class="nav-item"><a href="#" class="nav-item">My Account(<c:out value="${firstName}"></c:out>)</a></li>
		 </c:when>
		</c:choose>      
      </ul>
    </div>
  </nav>
   <div class="container">
  <div class="form">
  <form >
  <input type="hidden" name="page" value="login-form">
      <div class="input-field">
      <label for="email">Email</label>
      <input type="email" placeholder="example@example.com" id="email" name="email">
    </div>
    <div class="input-field">
      <label for="password">Password</label>
      <input type="password" placeholder="********" id="password" name="password">
    </div>
    <div class="input-field">
      <label for="remember">
          <input type="checkbox" name="" id="remember">Remember me
      </label>
    </div>
    
    <div class="action">
      <button id="btn" type="submit" name="login" class="btn">Sign in</button>
    </div>
  </form>

  </div>
</div>
 <script type="text/javascript">
var login='<%=request.getAttribute("Login")%>';
    if (login==1) {
 function alertName(){
 alert("Your account is Created Successfully");
 } 
 }
 </script>
 <script type="text/javascript"> window.onload = alertName; </script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>