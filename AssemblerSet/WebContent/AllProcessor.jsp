<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Processor</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" href="style.css">
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
   <section class="bg-light">
    <div class="container">
      <div class="row">
      <center><h1 style="text-align: center">CPU</h1></center>
      </div>
    </div>
  </section>
  <br>
  <br>
  <br>
  <div class="container">
    <h5>Filters</h5>
    <form action="Home?page=selectProcessor" method="POST">
      		<input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" name="chk_box" value="Intel">
  		<label class="form-check-label" for="flexSwitchCheckDefault">Intel</label>
  		<br>
  		<input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" name="chk_box" value="AMD">
  		<label class="form-check-label" for="flexSwitchCheckDefault">AMD</label>
	 <br>
	 <input type="submit" class="btn btn-secondary" name="Apply" value="Apply" >
    </form>
  </div>
  <br>
  <span style="color:#F24638">  
  <c:choose>  
  <c:when test="${error!=null }"><c:out value="${error }"></c:out></c:when>
  </c:choose>
  </span>
  <br>
  
  <div class="container">
  <table class="table table-hover">
  <thead class="thead-dark">
  <tr>
  <th scope="col">#</th>
  <th scope="col">Name</th>
  <th scope="col">Details</th>
  <th scope="col">Rate</th>
  <th scope="col">Add</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${AllProcessor}" var="processor">
  <tr>
  <td><img alt="processor" src="${processor.value.getImage() }" width="80" height="80"></td>
  <td><c:out value="${processor.value.getBrand()}"></c:out>&nbsp;<c:out value="${processor.value.getModel()}"></c:out></td>
  <td><c:out value="${processor.value.getDetails()}"></c:out></td>
  <td><c:out value="${processor.value.getRate()}"></c:out></td>
  <td><a class="btn btn-primary" href="Home?page=addToCart&product=cpu&item=<c:out value="${processor.key}"/>">Add to Cart</a><td>
  </tr>
  </c:forEach>
  </tbody>
  </table>
  </div>
  
  
  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>