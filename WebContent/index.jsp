<%@page import="beans.Picture"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<%   List<Picture> listImage = (List) request.getAttribute("galerie");

%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a href="#" class="navbar-brand">Gallerie Photo</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="#" class="nav-item nav-link active">Home</a>
        </div>
        <div class="navbar-nav ml-auto">
            <a href="login" class="nav-item nav-link">Login</a>
            <a href="signup" class="nav-item nav-link">Sign up</a>
        </div>
    </div>
</nav>
  
        
    <div class="photo-gallery">
        <div class="container-fluid">
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
                <div class="col-sm-6 col-md-4 col-lg-3 item filter vacance">
                <a href="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg" 
                data-lightbox="photos"><img class="img-fluid" 
                src="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg"></a>    
             </div>
                <div class="col-sm-6 col-md-4 col-lg-3 item filter lifestyle" ><a href="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg" data-lightbox="photos"><img class="img-fluid" src="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg"></a></div>
                <div class="col-sm-6 col-md-4 col-lg-3 item filter lifestyle" ><a href="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg" data-lightbox="photos"><img class="img-fluid" src="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg"></a></div>
                <div class="col-sm-6 col-md-4 col-lg-3 item filter meteo"><a href="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg" data-lightbox="photos"><img class="img-fluid" src="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg"></a></div>
                <div class="col-sm-6 col-md-4 col-lg-3 item filter lifestyle"><a href="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg" data-lightbox="photos"><img class="img-fluid" src="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg"></a></div>
                <div class="col-sm-6 col-md-4 col-lg-3 item filter setup"><a href="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg" data-lightbox="photos"><img class="img-fluid" src="https://demo.tutorialzine.com/2017/02/freebie-4-bootstrap-gallery-templates/images/park.jpg"></a></div>
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

<script type="text/javascript">
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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/js/lightbox.min.js"></script>
</body>
</html>