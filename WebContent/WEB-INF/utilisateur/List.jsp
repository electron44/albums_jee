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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" rel="stylesheet">
<script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
<script type="text/javascript" language="javascript" src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.0/js/jquery.dataTables.js"></script>
 <script>
 $(document).ready(function() {
		$('#example').DataTable();
});
 </script>
  
</head>

<body>
<% User utilisateur = (User) session.getAttribute("userConnected");   
   List<Albums> listAlbums = (List<Albums>) request.getAttribute("listAlbums");
%>
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <a href="#" class="navbar-brand">Galerie Photo</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="accueil" class="nav-item nav-link active">Home</a>
             <a href="#" class="nav-item nav-link active">Albums</a>
        </div>
        <div class="navbar-nav ml-auto">
        	<a href="#" class="nav-item nav-link">Bienvenue  ${sessionScope.userConnected.lastname} !</a>
            <a href="logout" class="nav-item nav-link">Déconnecter</a>
        </div>
    </div>
</nav>

<div class="container-fluid">
<div class="row-fluid">
	<div class="col-md-12">
				
	</div>
</div>

<div class="row">
	<div class="col-md-12 text-center">
			<h2>Mes Albums</h2>
	</div>
</div>
<div class="row-fluid">
		<div class="col-md-12">
		<div class="row-fluid">
			<div class="col-md-7">
				<div class="row-fluid">
					<div class="col-md-12">
					<div class="main">
				<div class="main-center">
				<h5 class="text-center">Ajouter un album</h5>
					<form class="" method="post" action="publicationAlbum">
						<input name="idOwner" type="hidden" value="<c:out value='${sessionScope.userConnected.id}'/>">
						<div class="form-group">
							<label for="name">Titre de l'album</label>
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input type="text" required class="form-control" name="titreAlbum" id="name"  placeholder="Donner le titre de l' album"/>
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
			
					</div>
					
					<div class="col-md-12">
					 <table  id="example" class="table table-stripped table-bordered">
			        <thead>
			        	<tr>
					        	<th>Titre</th>
					             <th>Type</th>
					             <th>Action</th>
			            </tr>
			        </thead>
			<tbody>
            <c:forEach var="album" items="${listAlbums}">
                <tr>
                    <td><a href="gestionAlbums?id=<c:out value='${album.id}' />"><c:out value="${album.getNom()}"/></a></td>
                    <td><c:out value="${album.getType()}"></c:out></td>
                    <td>
                        <a class="btn btn-success" data-toggle="modal" data-target="#myModal" />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a class="btn btn-danger" href="delete?id=<c:out value='${album.id}' />">Delete</a>                     
                    </td>
                </tr>
                <div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
      <div class="modal-body">
        <p>Some text in the modal.</p>
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
				
				</div>
							
			</div>
			
			<div class="col-md-5">
				<div class="main">
				<div class="main-center">
				<h5 class="text-center">Ajouter photo aux albums</h5>
					<form class="" method="post" action="ajoutPhoto">
						<input name="idOwner" type="hidden" value="<%= utilisateur.getId() %>">
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
							      <c:forEach var="album" items="${listAlbums}">
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
							<label for="name">mots clés ( Séparés par des points virgules )</label>
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