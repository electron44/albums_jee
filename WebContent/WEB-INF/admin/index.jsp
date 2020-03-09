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
   List<Albums> listAlbum = (List<Albums>) request.getAttribute("listAlbum");
   List<Picture> listImage = (List<Picture>)request.getAttribute("galerie");
   List<Picture> listImageS = (List<Picture>)session.getAttribute("pictureShared");
%>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a href="#" class="navbar-brand">Gallerie Photo</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="accueil" class="nav-item nav-link active">Home</a>
             <a href="listAlbums?id=<%= administrateur.getId()%>" class="nav-item nav-link active">Albums</a>
             <a href="listUtilisateurs" class="nav-item nav-link active">Users</a>
        </div>
        <div class="navbar-nav ml-auto">
        	<a href="#" class="nav-item nav-link"><%=administrateur.getUsername() %></a>
            <a href="logout" class="nav-item nav-link">Déconnecter</a>
        </div>
    </div>
</nav>


<div class="container">
<div class="intro">
                <h2 class="text-center">Galerie de photos</h2>
                <p class="text-center">Site de partage de photos </p>
            </div>
         <div class="row" id="buttons">
	         <div class="col-md-12 col-sm-12 col-lg-12" align="center">
	            <button class="btn btn-default filter-button" data-filter="all">All</button>
	            <button class="btn btn-default filter-button" data-filter="vacance">Vacances</button>
	            <button class="btn btn-default filter-button" data-filter="sport">Sports</button>
	            <button class="btn btn-default filter-button" data-filter="tehcnologie">Technologie</button>
	            <button class="btn btn-default filter-button" data-filter="fantastique">Fantastique</button>
	            <button class="btn btn-default filter-button" data-filter="voyage">Voyage</button>
	            <button class="btn btn-default filter-button" data-filter="nature">Nature</button>
	            
	        </div>
         </div>
	 <div class="row photos">
            	<c:forEach var="picture" items="<%=listImage %>" varStatus="status"> 	
            	 <div class="col-sm-6 col-md-4 col-lg-3 item filter <c:forTokens var="key" items="${picture.keywords}" delims=";">
            	 		<c:out value="${key}"/>
            	 </c:forTokens>">
                <a href="data:image/jpg;base64,${picture.getFichierName()}" 
                data-lightbox="photos"><img class="img-fluid" 
                src="data:image/jpg;base64,${picture.getFichierName()}"></a>    
                <c:out value="${picture.description}"/>
             </div>
            	</c:forEach>
            </div>
            
            <div class="row">
            	<div class="col-md-12 text-center">
            		<h1>Photo(s) partagé(s) avec moi</h1>
            	</div>
            </div>
            
            <div class="row photos">
            
            	<c:forEach var="picture" items="<%= listImageS %>" varStatus="status"> 	
            	 <div class="col-sm-6 col-md-4 col-lg-3 item filter <c:forTokens var="key" items="${picture.keywords}" delims=";">
            	 		<c:out value="${key}"/>
            	 </c:forTokens>">
                <a href="data:image/jpg;base64,${picture.getFichierName()}" 
                data-lightbox="photos"><img class="img-fluid" 
                src="data:image/jpg;base64,${picture.getFichierName()}"></a>    
                <c:out value="${picture.description}"/>
             </div>
            
            	
            	</c:forEach>
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

.filter img{
	width : 300px;
	height : 200px;
}

</style>

<script>
$(document).ready(function(){

    $(".filter-button").click(function(){
        var value = $(this).attr('data-filter');
        
        if(value == "all")
        {
            //$('.filter').removeClass('hidden');
            $('.filter').show('1000');
        }
        else
        {
//            $('.filter[filter-item="'+value+'"]').removeClass('hidden');
//            $(".filter").not('.filter[filter-item="'+value+'"]').addClass('hidden');
            $(".filter").not('.'+value).hide('3000');
            $('.filter').filter('.'+value).show('3000');
            
        }
    });
    
    if ($(".filter-button").removeClass("active")) {
		$(this).removeClass("active");
		}
		$(this).addClass("active");
});
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/js/lightbox.min.js"></script>
</body>
</html>