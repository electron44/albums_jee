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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/css/lightbox.min.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" crossorigin="anonymous"/>
<link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet"/>
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
<script type="text/javascript" language="javascript" src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.0/js/jquery.dataTables.js"></script>
 <script>
 $(document).ready(function() {
		$('#example').DataTable();
});
 </script>
</head>

<body>
<% User administrateur = (User) session.getAttribute("userConnected");   
   List<Albums> listAlbum = (List<Albums>) request.getAttribute("listAlbum");
   List<Albums> personnalAlbum = (List<Albums>) request.getAttribute("personnalAlbum");
%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a href="#" class="navbar-brand">Gallerie Photo</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="accueil" class="nav-item nav-link active">Home</a>
             <a href="#" class="nav-item nav-link active">Albums</a>
                          <a href="listImage" class="nav-item nav-link active">Image</a>
             <a href="listUtilisateurs" class="nav-item nav-link active">Users</a>
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
					<h1>Bienvenue <%= administrateur.getLastname() %> ! </h1>
	</div>
</div>

<div class="row">
	<div class="col-md-12 text-center">
			<h2>Liste des Albums ~~ <a href="#ajout">ajout d'album ou de photos</a></h2>
	</div>
</div>
<div class="row">
		<div class="col-md-12">
        <table id="example" class="table table-striped table-bordered" cellpadding="0">
        <thead>
        	    <tr>
                <th>Titre</th>
                <th>Type</th>
                <th>Propriétaire</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
        
        
            <c:forEach var="album" items="${listAlbum}">
                <tr>
					<td><a href="gestionAlbums?id=<c:out value='${album.id}' />"><c:out value="${album.getNom()}"/></a></td>
                    <td><c:out value="${album.getType()}"></c:out></td>
                    <td><c:out value="${album.getUser().name}"></c:out >&nbsp;<c:out value="${album.getUser().lastname}"></c:out></td>
                    <td>
                        <a class="btn btn-success"  data-toggle="modal" data-target="#myModal<c:out value='${album.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a class="btn btn-danger" href="delete?id=<c:out value='${album.id}' />">Delete</a>                     
                    </td>
                </tr> 
                <div id="myModal<c:out value='${album.id}' />" class="modal fade" role="dialog">
				  <div class="modal-dialog">
				    <!-- Modal content-->
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal">&times;</button>
				        <h4 class="modal-title">Modification de l'album</h4>
				      </div>
				      <div class="modal-body">
				      	<form method="post" action="editAlbum">
				      		<input type="hidden" name="id"  value="<%= administrateur.getId()%>" />
				      		
				  			<input type="hidden" name="id1" value="${album.id}" />
				      	
				      		<div class="form-group">
				       			<label for="nom">Titre</label>
				       			<input type="text" name="nom" class ="form-control" value="${album.getNom()}" required>
				       		</div>
				       		<div class="input-group">
				       		<label for="typeAlbum"></label>
								<select class="form-control" name="typeAlbum">
							      <option value="prive">privé</option>
							      <option value="public">public</option>
							    </select>
							</div>
							
							<div class="input-group">
								<button type="submit" class="btn btn-success btn-block">Enregistrer Modifications</button>
							</div>
							
				      	
				      	</form>
				       		
							
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				      </div>
				    </div>
				
				  </div>
				</div>
          
            </c:forEach>
            </tbody>
        </table>
    </div>   
	</div>
		<div class="row">
		<div class="col-md-6 col-sm-12">
					<div class="main">
				<div class="main-center">
				<h5 class="text-center">Ajouter un album</h5>
					<form class="" method="post" action="publicationAlbum">
						<input name="idOwner" type="hidden" value="<%= administrateur.getId() %>">
						<div class="form-group">
							<label for="name">Nom de l'alubm</label>
								<div class="input-group">
								<select class="form-control" required name="titreAlbum">
							      		<option value="vacance">Vacance</option>
							      		<option value="sport">Sport</option>
							      		<option value="voyage">Voyage</option>
							      		<option value="nature">Nature</option>
							      		<option value="technologie">Technologie</option>
							      		<option value="fanstastique">Fantastique</option>
							      		<option value="setup">Setup</option>
							    </select>
							</div>
						</div>
						<div class="form-group">
							<label for="email">Type d'album</label>
								<div class="input-group">
								<select class="form-control" name="typeAlbum">
							      <option value="prive">privé</option>
							      <option value="public">public</option>
							    </select>
							</div>
						</div>
				<button type="submit" class="btn btn-primary">enregistrer</button>
						
					</form>
				</div><!--main-center"-->
			</div>
				</form>
		</div>
		<div class="col-md-6 col-sm-12" id="ajout">
			<div class="main">
				<div class="main-center">
				<h5 class="text-center">Ajouter photo aux albums</h5>
 					<form class="" method="post" action="ajoutPhoto" enctype="multipart/form-data">
						<input name="idOwner" type="hidden" value="<%= administrateur.getId() %>">
						<div class="form-group">
							<label for="name">Titre de la photo</label>
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input type="text"  required class="form-control" name="titrePhoto" id="name"  placeholder="Donner le titre de la photo"/>
							</div>
						</div>
						<div class="form-group">
							<label for="albumsName">Nom de l'album</label>
								<div class="input-group">
								<select class="form-control" required name="albumId">
							      <c:forEach var="album" items="${personnalAlbum}">
							      		<option value="<c:out value="${album.id}"/>"><c:out value="${album.nom}"/></option>
							      </c:forEach>
							    </select>
							</div>
						</div>
						<div class="form-group">
							<label for="albumsName">Description</label>
								<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<textarea class="form-control" required  name="description"  id="exampleFormControlTextarea1" placeholder="Donner une description" rows="3"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="name">mots clés(Séparés par des points virgules)</label>
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input type="text" class="form-control" name="keywords" id="motcle"  placeholder="Donner des mots clés" required/>
							</div>
						</div>
						<div class="form-group">
							<label for="name">Inserer l'image</label>
							<div class="input-group custom-file">
						    <input type="file" accept="image/*" name="monimage" class="custom-file-input" id="customFile">
						    <label class="custom-file-label"  for="customFile" required>Choose file</label>
						 </div>	
						</div>
						
				<button type="submit" class="btn btn-primary">enregistrer</button>
						
					</form>
				</div><!--main-center"-->
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
</script>
</body>
</html>