package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.SendResult;

import org.apache.jasper.tagplugins.jstl.core.Out;

import beans.Albums;
import beans.Picture;
import beans.User;
import dao.AlbumsDAO;
import dao.PictureDAO;
import dao.UserDAO;

/**
 * Servlet implementation class UserServletsLogSign
 */
@WebServlet({"/user/*"})
@MultipartConfig(maxFileSize = 1024*1024*5,maxRequestSize = 1024*1024*3*3) 
public class UserServletsLogSign extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private AlbumsDAO albumsDAO;
	private PictureDAO pictureDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServletsLogSign() {
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
		// TODO Auto-generated method stub
		 String requested = request.getRequestURI();
		
		 if(requested.endsWith("/dashboard")) {
			 try {
				listImage(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/utilisateur/user_dashboard.jsp").forward(request, response);
		 }else if(requested.endsWith("/mesAlbums")) {
			listAlbums(request, response);
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requested = request.getRequestURI();
		if(requested.endsWith("/publicationAlbum")) {
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
		        request.setAttribute("listAlbums", ListPersonalAlbums); 
		        getServletContext().getRequestDispatcher("/WEB-INF/utilisateur/List.jsp").forward(request, response);
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
		}else if(requested.endsWith("/ajoutPhoto")) {		   
			 try {
					addPicture(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}      
	            
		}else if(requested.endsWith("/addPartage")) {
			try {
				addPartage(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void listAlbums(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
		 int id = Integer.parseInt(request.getParameter("id"));
			
			List<Albums> personnalAlbum = null;
			List<Albums> albumApartager = null;
			if(Integer.valueOf(id)!=null) {
				try {
					donneListUser(request, response, id);
					personnalAlbum = albumsDAO.listAlbumsPersonal(id);
					request.setAttribute("listAlbums", personnalAlbum);
					albumApartager =  albumsDAO.listAllPrivate(id);
					request.setAttribute("partager", albumApartager);
					HttpSession session = request.getSession(false);

					getServletContext().getRequestDispatcher("/WEB-INF/utilisateur/List.jsp").forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	}
	
	 private void listImage(HttpServletRequest request, HttpServletResponse response)
	           throws SQLException, IOException, ServletException {
	        List<Picture> listImages = pictureDAO.getAllPicturePublic();
	        request.setAttribute("galerie", listImages);
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
		  response.sendRedirect("mesAlbums?id="+id);
	}
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
	       // out.print(albumId);
	       
	        if(yup)
	        	response.sendRedirect("dashboard"); 
	        else {
	        	
	        	
	        }
	        
	       
 }
	  private void donneListUser(HttpServletRequest request, HttpServletResponse response,int id) throws SQLException {
      		List<User> listShares = userDAO.listAllOtherUsers(id);
      		request.setAttribute("listShare", listShares);
      		
      }	
	  
	  private void addPartage(HttpServletRequest request ,HttpServletResponse response) throws SQLException, ServletException, IOException {
		  int idAlbum = Integer.parseInt(request.getParameter("albumId"));
		  int id = Integer.parseInt(request.getParameter("id"));
		  int usersId = Integer.parseInt(request.getParameter("usersId"));

		  boolean added = albumsDAO.addPartage(idAlbum, usersId);
		  String message ="";
		  if(added) {
			  message = "Votre album a bien été partagé";
			  
		  }else {
			  message = "Erreur lors du partage des albums";
		  }
		  request.setAttribute("messagePartage", message);
		  response.sendRedirect("mesAlbums?id="+id);
	  }
	  
	 
	
	
}
