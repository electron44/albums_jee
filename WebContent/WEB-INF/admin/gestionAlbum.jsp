<%@page import="beans.Picture"%>
<%@page import="beans.Albums"%>
<%@page import="dao.UserDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="beans.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Albums</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/css/lightbox.min.css">
     <!--  --> <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.css">     
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
<% User administrateur = (User) session.getAttribute("userConnected");   
   List<Picture> listImage = (List<Picture>) request.getAttribute("listImage");
%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a href="#" class="navbar-brand">Gallerie Photo</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="accueil " class="nav-item nav-link active">Home</a>
             <a href="listAlbums?id=<%= administrateur.getId()%>" class="nav-item nav-link active">Albums</a>
             <a href="#" class="nav-item nav-link active">Users</a>
        </div>
        <div class="navbar-nav ml-auto">
        	<a href="#" class="nav-item nav-link"></a>
            <a href="logout" class="nav-item nav-link">Déconnecter</a>
        </div>
    </div>
</nav>
 <div class="container-fluid">
<div class="row">
	<div class="col-md-12">
					<h1>Bienvenue <%= administrateur.getLastname() %> ! </h1>
	</div>
</div>

<div class="row">
	<div class="col-md-12">
		<div align="center">
<c:forEach var="picture" items="${listImage}">
    <h2><c:out value="${picture.titre}" /></h2>
    <h3><c:out value="${picture.description}" /></h3>
    <img src="data:image/jpg;base64,${picture.fichierName}" width="240" height="300"/>
</c:forEach>
</div>
	</div>
</div>
</div>

<style>
	.main{
 	padding: 40px 0;
}
.main input,
.main input::-webkit-input-placeholder {
    font-size: 11px;
    padding-top: 3px;
}
.main-center{
 	margin-top: 30px;
 	margin: 0 auto;
 	max-width: 400px;
    padding: 10px 40px;
	background:#009edf;
	    color: #FFF;
    text-shadow: none;
	-webkit-box-shadow: 0px 3px 5px 0px rgba(0,0,0,0.31);
-moz-box-shadow: 0px 3px 5px 0px rgba(0,0,0,0.31);
box-shadow: 0px 3px 5px 0px rgba(0,0,0,0.31);

}
span.input-group-addon i {
    color: #009edf;
    font-size: 17px;
}
</style>
<script>
$(".custom-file-input").on("change", function() {
	  var fileName = $(this).val().split("\\").pop();
	  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
	});
$(document).ready(function() {
    $('#example').DataTable();
} );
</script>
</body>
</html>