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
	<link href="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.css" rel="stylesheet" type=text/css>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"
			  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
			  crossorigin="anonymous"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/ekko-lightbox/5.3.0/ekko-lightbox.min.js"></script> 
     <!--  --> <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.css">     
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>

<body>
<% User utilisateur = (User) session.getAttribute("userConnected");
 List<Picture> listImages = (List) request.getAttribute("galerie");
%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a href="#" class="navbar-brand">Gallerie Photo</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="#" class="nav-item nav-link active">Home</a>
             <a href="mesAlbums?id=<%= utilisateur.getId()%>" class="nav-item nav-link">Albums</a>
        </div>
        <div class="navbar-nav ml-auto">
        	<a href="#" class="nav-item nav-link"></a>
            <a href="logout" class="nav-item nav-link">Déconnecter</a>
        </div>
    </div>
</nav>
<div class="container">
	<div class="row">
		<div class="col-md-12">
						<h1>Bienvenue <%= utilisateur.getLastname() %> ! </h1>
		</div>
	</div>
	<div class="row photos">
        <c:forEach var="picture" items="<%=listImages %>" varStatus="status">     	
           <div class="col-sm-6 col-md-4 col-lg-3 item filter <c:forTokens var="key" items="${picture.keywords}" delims=";">
            	 		<c:out value="${key}"/>
            	 </c:forTokens>">    
                 <a href="data:image/jpg;base64,${picture.getFichierName()}" data-toggle="lightbox" data-width="900">
                <img src="data:image/jpg;base64,${picture.getFichierName()}" class="img-fluid" width:>
            </a>
                <c:out value="${picture.description}"/>
             </div>
         </c:forEach>
     </div>    
    
            
</div>
<style>
.photo-gallery {
  color:#313437;
  background-color:#fff;
}

.photo-gallery p {
  color:#7d8285;
}

.photo-gallery h2 {
  font-weight:bold;
  margin-bottom:40px;
  padding-top:40px;
  color:inherit;
}

@media (max-width:767px) {
  .photo-gallery h2 {
    margin-bottom:25px;
    padding-top:25px;
    font-size:24px;
  }
}

.photo-gallery .intro {
  font-size:16px;
  max-width:500px;
  margin:0 auto 40px;
}

.photo-gallery .intro p {
  margin-bottom:0;
}

.photo-gallery .photos {
  padding-bottom:20px;
}

.photo-gallery .item {
  padding-bottom:30px;
}


#buttons{
	margin-bottom: 40px;
}


.filter img{
	width : 300px;
	height : 200px;
}
</style>
<script>
$(document).on('click', '[data-toggle="lightbox"]', function(event) {
    event.preventDefault();
    $(this).ekkoLightbox({
    	loadingMessage : "accord accord"	
    });
});
</script>
</body>
</html>