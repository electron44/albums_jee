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
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" crossorigin="anonymous">
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
<% User administrateur = (User) session.getAttribute("userConnected");   
   List<User> listUser = (List<User>) request.getAttribute("listUser");
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

<div class="row-fluid">
		<div class="col-md-12">
			<div class="row-fluid">
				<div class="col-md-12">
					 <table id="example" class="table table-striped table-bordered">
						<thead>
				            <tr>
				                <th>id</th>
				                <th>Nom</th>
				                <th>Prenom</th>
				                <th>Username</th>
				                <th>Action</th>
				            </tr>
				            </thead>
				            <tbody>
				            <c:forEach var="user" items="${listUser}">
				                <tr>
				                    <td><c:out value="${user.id}"></c:out></td>
				                    <td><c:out value="${user.name}"></c:out></td>
				                    <td><c:out value="${user.lastname}"></c:out></td>
				                    <td><c:out value="${user.username}"></c:out></td>
				                    <td>
				                        <a class="btn btn-success"  data-toggle="modal" data-target="#myModal<c:out value='${user.id}' />">
				                        	<i class="fa fa-edit"></i>
				                        </a>
				                        &nbsp;&nbsp;&nbsp;&nbsp;
				                        <a class="btn btn-danger" href="delete?login=<c:out value='${user.id}' />">
				                        <i class="fa fa-trash"></i>
				                        </a>                     
				                    </td>
				                </tr>
				                <div id="myModal<c:out value='${user.id}' />" class="modal fade" role="dialog">
				  <div class="modal-dialog">
				    <!-- Modal content-->
				    <div class="modal-content">
				      <div class="modal-header">
				        <h4 class="modal-title">Modification de l'utilisateur</h4>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
         						 <span aria-hidden="true">&times;</span>
      					  </button>
				      </div>
				      <div class="modal-body">
				      	<form method="post" action="editUser">
				      		<input type="hidden" name="id"  value="<%= administrateur.getId()%>" />
				      		
				  			<input type="hidden" name="id1" value="${user.id }" />
				      
				      		<div class="form-group">
				       			<label for="nom">Nom</label>
				       			<input type="text" name="nom" class ="form-control" value="${user.name }" required>
				       		</div>
				       		<div class="form-group">
				       			<label for="nom">Prenom</label>
				       			<input type="text" name="prenom" class ="form-control" value="${user.lastname }" required>
				       		</div>
				       		
				       		<div class="form-group">
				       			<label for="nom">Username</label>
				       			<input type="text" name="username" class ="form-control" value="${user.username }" required>
				       		</div>
				       		
							
							<div class="form-group">
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
				
				<div class="col-md-5">
					<div class="main">
				<div class="main-center">
				<h5 class="text-center">Ajouter un utilisateur</h5>
					<form class="" method="post" action="insertUser">
						<div class="form-group">
							<label for="name">Nom de l'utilisateur</label>
								<div class="input-group">
									<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input type="text"  required class="form-control" name="names" id="name"  placeholder="Donner le titre de la photo"/>
							</div>
						</div>
						<div class="form-group">
							<label for="prenomUser">Prénom de l'utilisateur</label>
								<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input type="text"  required class="form-control" name="lastname" id="name"  placeholder="prenom de l'utilisateur"/>
							    </select>
							</div>
						</div>
						<div class="form-group">
							<label for="albumsName">Username</label>
								<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input class="form-control" required  name="username"  id="exampleFormControlInput" placeholder="Nom de l'utilisateur" rows="3"/>
							</div>
						</div>
						<div class="form-group">
							<label for="albumsName">Mot de passe</label>
								<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input class="form-control" required  name="password"  id="exampleFormControlInput" placeholder="Nom de l'utilisateur" rows="3"/>
							</div>
						</div>
							
							<div class="form-group">
							<label for="albumsName">Confirmation mot de passe </label>
								<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
				<input class="form-control" required  name="password_confirm"  id="exampleFormControlInput" placeholder="Confirmation de mot de passe" rows="3"/>
							</div>
							</div>
							
							<div class="form-group">
							<label for="typeuser">Type utilisateur</label>
								<div class="input-group">
									    <select name="typeUser" class="form-control">
									      <option value="1">Admin</option>
									      <option value="0">Simple</option>
									    </select>
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
$(function(){
	
})

</script>
</body>
</html>