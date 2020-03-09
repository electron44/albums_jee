<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
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
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a href="#" class="navbar-brand">Gallerie Photo</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="" class="nav-item nav-link active">Home</a>
        </div>
        <div class="navbar-nav ml-auto">
            <a href="login" class="nav-item nav-link">Login</a>
            <a href="signup" class="nav-item nav-link">Sign up</a>
        </div>
    </div>
</nav>
<% Map<String , String> erreurs = (HashMap) request.getAttribute("erreurs"); %>
<h2 class="text-center"> Inscrivez vous</h2>
<form method="post" style="border:3px solid #ccc">
<div class="container">
	<label><b>Username</b></label>
	<input type="text" placeholder="username" name="username" value="<c:out value="${param.username}"></c:out>" >
	<label><b>Prenom</b></label>
	<input type="text" placeholder="Votre nom" name="names" value="<c:out value="${param.names}"></c:out>">
	<label><b>Nom</b></label>
	<input type="text" placeholder="Votre nom" name="lastname" value="<c:out value="${param.lastname}"></c:out>">
	<label><b>Mot de passe</b></label>
	<input type="password" placeholder="Mot de passe" name="password">
	<label><b>Confirmation mot de passe</b></label>
	<input type="password" placeholder="retaper le mot de passe" name="password_confirm">
	<p class="text-danger">${erreurs['username']}</p>
	<p class="text-danger">${erreurs['password']} </p>
	<p class="text-danger">${erreurs['exists']} </p>
	
</div>
<div class="clearfix">
<button type="submit" class="btn-round btn-block btn-success">register</button>

</div>
</div>

</form>
<style>
	.container{
	padding:16px;
}
input[type=text],input[type=password]{
	width:100%;
	padding:12px 20px;
	margin:8px 0;
	display:inline-block;
	box-sizing:border-box;
	
}
button{
	background-color:#4caF50;
	color:white;
	padding:14px 20px;
	margin:8px 0;
	border:none;
	cursor:pointer;
	width:100%;
	
	
}

</style>
</body>
</html>