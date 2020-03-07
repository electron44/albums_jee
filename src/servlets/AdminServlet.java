package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.SendResult;

import beans.Albums;
import beans.Picture;
import beans.User;
import dao.AlbumsDAO;
import dao.PictureDAO;
import dao.UserDAO;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin/*")
@MultipartConfig(maxFileSize = 1024*1024*5,maxRequestSize = 1024*1024*3*3) 

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDAO userDAO;
	private AlbumsDAO albumsDAO;
	private PictureDAO pictureDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ousmane_j";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
        albumsDAO = new AlbumsDAO(jdbcURL, jdbcUsername, jdbcPassword);
        pictureDAO = new PictureDAO(jdbcURL, jdbcUsername, jdbcPassword);

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requested = request.getRequestURI();
		
		if(requested.endsWith("/upload")) {
			getServletContext().getRequestDispatcher("/WEB-INF/admin/upload.jsp").forward(request, response);
		}else if(requested.endsWith("/accueil")) {
			getServletContext().getRequestDispatcher("/WEB-INF/admin/index.jsp").forward(request, response);
		}
		else if(requested.endsWith("/listAlbums")) {
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				
				List<Albums> personnalAlbum = null;
				List<Albums> albumPartager = null;
				if(Integer.valueOf(id)!=null) {
					personnalAlbum = albumsDAO.listAlbumsPersonal(id);
					albumPartager= albumsDAO.listSharedAlbum(id);
				}
				request.setAttribute("shared", albumPartager);
				request.setAttribute("personnalAlbum", personnalAlbum);
				listAlbums(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(requested.endsWith("/listUtilisateurs")) {
			try {
				listUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(requested.endsWith("/gestionAlbums")) {
			try {
				listImage(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String requested = request.getRequestURI();
		 if(requested.endsWith("/ajoutPhoto")) {		   
			 try {
					addPicture(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}      
	            
		}else if(requested.endsWith("/publicationAlbum")) {
			String erreur = "";
			String nomAlbum = request.getParameter("titreAlbum");
			String typeAlbum = request.getParameter("typeAlbum");
			int idOwner = Integer.parseInt(request.getParameter("idOwner"));
			
			
			Albums albums = new Albums();
			albums.setNom(nomAlbum);
			albums.setType(typeAlbum);
			User user = new User(idOwner);
			albums.setUser(user);
			
				try {
					erreur = "Enregistrement pris en compte !";
					albumsDAO.addAlbums(albums);
					HttpSession session = request.getSession(false);
			        List<Albums> ListPersonalAlbums = albumsDAO.listAlbumsPersonal(idOwner);
			        request.setAttribute("personnalAlbum", ListPersonalAlbums);
					listAlbums(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}else if(requested.endsWith("/insertUser")) {
			try {
				insertUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(requested.endsWith("/editAlbum")) {
			 try {
				editAlbum(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(requested.endsWith("/editUser")) {
			try {
				editUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	 private void listUser(HttpServletRequest request, HttpServletResponse response)
	           throws SQLException, IOException, ServletException {
	        List<User> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/ListUsers.jsp");
	        dispatcher.forward(request, response);
	    }
	 
	 private void listAlbums(HttpServletRequest request, HttpServletResponse response)
	           throws SQLException, IOException, ServletException {
	        List<Albums> listAlbums = albumsDAO.listAlbums();
	        request.setAttribute("listAlbum", listAlbums);
	        getServletContext().getRequestDispatcher("/WEB-INF/admin/List.jsp").forward(request, response);
	    }
	 
	
	 
	    private void updateUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        String name = request.getParameter("name");
	        String lastname = request.getParameter("lastname");
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        
	        User user = new User(id,name, lastname,username,password);
	        userDAO.updateUser(user);
	        response.sendRedirect("list");
	    }
	 
	    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	 
	        User User = new User(id);
	        userDAO.deleteUser(User);
	        response.sendRedirect("list");
	 
	    }
	    
	    private void insertUser(HttpServletRequest request, HttpServletResponse response)
		        throws SQLException,ServletException, IOException {
		        Map<String, String> erreurs = new HashMap<String, String>();
		        String name = request.getParameter("names");
		        String lastname = request.getParameter("lastname");
		        String password  = request.getParameter("password");
		        String username = request.getParameter("username");
		        String password_conf = request.getParameter("password_confirm");
		        int type = Integer.parseInt(request.getParameter("typeUser"));
		        //int idSubmiter = Integer.parseInt(request.getParameter("id"));
		        
		        try {
					validationMotsDePasse(password, password_conf);
				} catch (Exception exception) {
					erreurs.put("password",exception.getMessage());
				}
		        
		        try {
					validationUsername(username);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					erreurs.put("username",e.getMessage());
				}
		        
		        if(erreurs.isEmpty()) {
		        	 User newUser = new User();
		        	 newUser.setUsername(username);
		 	         newUser.setLastname(lastname);
		 	         newUser.setPassword(password);
		 	         newUser.setName(name);
		 	         newUser.setTypeUser(type);
		 	         userDAO.insertUserByAdmin(newUser);
			         response.sendRedirect("listUtilisateurs");
		        }else {
		        	request.setAttribute("erreurs", erreurs);
		        	getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
		        }
		        
		       
		        
		       
		        
		    }
		 
		 private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
			    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
			        if (!motDePasse.equals(confirmation)) {
			            throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			        } else if (motDePasse.trim().length() < 3) {
			            throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
			        }
			    } else {
			        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
			    }
			}

			/**
			 * Valide le nom d'utilisateur saisi.
			 */
			private void validationUsername( String name ) throws Exception {
			    if ( name != null && name.trim().length() < 3 ) {
			        throw new Exception( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
			    }
			}
	    
		
			/*
			 * Méthode utilitaire qui a pour unique but d'analyser l'en-tête
			 * "content-disposition", et de vérifier si le paramètre "filename" y est
			 * présent. Si oui, alors le champ traité est de type File et la méthode
			 * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
			 * la méthode retourne null.
			 */
			private static String getNomFichier( Part part ) {
			    for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {

			        if ( contentDisposition.trim().startsWith( "filename" ) ) {
			            return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
			        }
			    }
			    return null;
			}
	    
	   private void addPicture(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException, SQLException {
		   		String erreur= "";
	    	 	String titrePhoto = request.getParameter("titrePhoto");
		        String descriptionPhoto = request.getParameter("description");
		        int albumId = Integer.parseInt(request.getParameter("albumId"));
		        String keywords = request.getParameter("keywords");
		        String dateCreation = new Date().toString();
		        Part partfile = request.getPart("monimage");
		        Picture picture = new Picture();
		        picture.setTitre(titrePhoto);
		        picture.setDescription(descriptionPhoto);
		        picture.setDateCreation(dateCreation);
		        picture.setKeywords(keywords);
		        
		        
		        picture.setFichierName(getNomFichier(partfile));
		        Albums albums = new Albums(albumId);
		        picture.setAlbum(albums);
		        
		        boolean yup =  pictureDAO.insertPicture(picture, partfile);
		        PrintWriter out = response.getWriter();
		       
		        if(yup)
		        	response.sendRedirect("accueil"); 
		        else {
		        	
		        	
		        }
	   }
	   
	   private void listImage(HttpServletRequest request, HttpServletResponse response)
	           throws SQLException, IOException, ServletException {
		   int id = Integer.parseInt(request.getParameter("id"));
	        List<Picture> listImage = pictureDAO.listPictureByAlbum(id);
	        request.setAttribute("listImage", listImage);
	        getServletContext().getRequestDispatcher("/WEB-INF/admin/gestionAlbum.jsp").forward(request, response);
	    }
	   
	   private void editAlbum(HttpServletRequest request , HttpServletResponse response) throws SQLException, IOException {
		   int id1 = Integer.parseInt(request.getParameter("id1"));
		   int id = Integer.parseInt(request.getParameter("id"));

		   String nom = request.getParameter("nom");
		   String typeAlbum = request.getParameter("typeAlbum");
		   Albums albums = new Albums();
		  albums.setId(id1);
		  albums.setNom(nom);
		  albums.setType(typeAlbum);
		  albumsDAO.updateAlbum(albums);
		  response.sendRedirect("listAlbums?id="+id);
	}
	   
	   private void editUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		   int id1 = Integer.parseInt(request.getParameter("id1"));
		   int id = Integer.parseInt(request.getParameter("id"));
		   User user =new User(id1);
		   String nom = request.getParameter("nom");
		   user.setName(nom);
		   String prenom= request.getParameter("prenom");
		   user.setLastname(prenom);
		   String username = request.getParameter("username");
		   user.setUsername(username);
		   userDAO.updateUser(user);
		   response.sendRedirect("listUtilisateurs");
	   }
	   
	   

}
