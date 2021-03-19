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
</head>
<body>
<% System.out.println(session.getAttribute("uid")); System.out.println(session.getAttribute("total"));%>
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
      <center><h1 style="text-align: center">Order History</h1></center>
      </div>
    </div>
  </section>
  <br>
  <br>
  <br>
 <div class="container">
  <table class="table table-hover">
  <thead class="thead-dark">
  <tr>
  <th scope="col">Product</th>
  <th scope="col">#</th>
  <th scope="col">Name</th>
  <th scope="col">Rate</th>
  </tr>
  </thead>
  <tbody>
  <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">CPU</a></td>
  <td><img alt="processor" src="${cpuImage}" width="80" height="80"></td>
  <td><c:out value="${cpuName}"></c:out></td>
  <td><c:out value="${cpuRate}"></c:out></td>
  </tr>
  
    <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">Motherboard</a></td>
  <td><img alt="motherBoard" src="${MotherBoardImage}" width="80" height="80"></td>
  <td><c:out value="${MotherBoardName}"></c:out></td>
  <td><c:out value="${MotherBoardRate}"></c:out></td>
  </tr>
  
    <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">Graphic Card</a></td>
  <td><img alt="graphiccard" src="${GraphicCardImage}" width="80" height="80"></td>
  <td><c:out value="${GraphicCardName}"></c:out></td>
  <td><c:out value="${GraphicCardRate}"></c:out></td>
  </tr>
  
  <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">Memory Module</a></td> 
  <td><img alt="memorymodule" src="${ramImage}" width="80" height="80"></td>
  <td><c:out value="${ramName}"></c:out></td>
  <td><c:out value="${ramRate}"></c:out></td>
  </tr>
  
      <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">Cabinets</a></td>  
  <td><img alt="cabinets" src="${CabinetsImage}" width="80" height="80"></td>
  <td><c:out value="${CabinetsName}"></c:out></td>
  <td><c:out value="${CabinetsRate}"></c:out></td>
  </tr>
  
  <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">Power Supply</a></td>
  <td><img alt="powersupply" src="${powerImage}" width="80" height="80"></td>
  <td><c:out value="${powerName}"></c:out></td>
  <td><c:out value="${powerRate}"></c:out></td>
  </tr>
  
  <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">Storage</a></td>
  <td><img alt="storage" src="${storageImage}" width="80" height="80"></td>
  <td><c:out value="${storageName}"></c:out></td>
  <td><c:out value="${storageRate}"></c:out></td>
  </tr>
  
    <tr>
  <td><a type="button" class="btn btn-primary btn-lg" href="#">Ups</a></td>
  <td><img alt="ups" src="${upsImage}" width="80" height="80"></td>
  <td><c:out value="${upsName}"></c:out></td>
  <td><c:out value="${upsRate}"></c:out></td>
  </tr>
  
  <tr>
  <td>Total</td>
  <td></td>
  <td></td>
  <td>Rs.&nbsp;<c:out value="${total}"></c:out>&nbsp;/-
  </td>


  </tr>
  </tbody>
  </table>
  </div>
  
  <br>
  <br>
  <div class="container">
  <h2>Shipping Details</h2>
    <div class="main-body">

            <div class="col-md-12">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Full Name</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:out value="${firstName}"></c:out>&nbsp;<c:out value="${lastName}"></c:out>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Email</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                    <c:out value="${Email}"></c:out>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Phone</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      <c:out value="${Phone}"></c:out>
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0">Shipping Address</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                    <c:out value="${Address}"></c:out>,<br>
                    <c:out value="${City}"></c:out>-<c:out value="${Zip}"></c:out>,<br>
                    <c:out value="${State}"></c:out>,<br>
                    <c:out value="${Country}"></c:out>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <a href="Home" class="btn btn-info float-right">OK</a>
    </div>

  <script type="text/javascript">
var order='<%=request.getAttribute("Order")%>';
    if (order==1) {
 function alertName(){
 alert("Order Placed Successfully");
 } 
 }
 </script>
 <script type="text/javascript"> window.onload = alertName; </script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>

 
</body>
</html>
