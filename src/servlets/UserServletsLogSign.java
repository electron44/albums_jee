package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.SendResult;

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
		}	
	}
	
	private void listAlbums(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletException {
		 int id = Integer.parseInt(request.getParameter("id"));
			
			List<Albums> personnalAlbum = null;
			if(Integer.valueOf(id)!=null) {
				try {
					personnalAlbum = albumsDAO.listAlbumsPersonal(id);
					request.setAttribute("listAlbums", personnalAlbum);
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
	
}
