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

import beans.Albums;
import beans.Picture;
import beans.User;
import dao.AlbumsDAO;
import dao.PictureDAO;
import dao.UserDAO;

/**
 * Servlet implementation class LoginLogoutServlet
 */
@WebServlet({"/admin/logout","/user/logout","/login","/user/user_dashboard","/admin/accueil","/signup"})
public class LoginLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * 
     */
	private UserDAO userDAO;
	private AlbumsDAO albumsDAO;
	private PictureDAO pictureDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public void init() {
        String jdbcURL = "jdbc:mysql://localhost:3306/ousmane_j";
        String jdbcUsername = "root";
        String jdbcPassword = "";
        userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
        albumsDAO = new AlbumsDAO(jdbcURL, jdbcUsername, jdbcPassword);
        pictureDAO = new PictureDAO(jdbcURL, jdbcUsername, jdbcPassword);

    }
    public LoginLogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requested =  request.getRequestURI();
		
		if(requested.endsWith("/login")) {
			getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}else if(requested.endsWith("/user/user_dashboard")) {
			try {
				HttpSession session = request.getSession(false);
				listImage(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/utilisateur/user_dashboard.jsp").forward(request, response);
		}else if (requested.endsWith("/signup")) {
			getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
		}else if (requested.endsWith("/admin/accueil")) {
			try {
				HttpSession session = request.getSession(false);
				listImage(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getServletContext().getRequestDispatcher("/WEB-INF/admin/index.jsp").forward(request, response);
		}else if(requested.endsWith("/admin/logout") || requested.endsWith("/user/logout")) {
			logout(request, response);
			response.sendRedirect(request.getContextPath());
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requested =  request.getRequestURI();
		
		if(requested.endsWith("/login")) {
			try {
				connectUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(requested.endsWith("/user_dashboard")) {
			getServletContext().getRequestDispatcher("/WEB-INF/utilisateur/user_dashboard.jsp").forward(request, response);
		}else if(requested.endsWith("/signup")) {
			try {
				insertUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	 private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	HttpSession session = request.getSession();
	    	session.invalidate();
	    }
	 
	 private void connectUser(HttpServletRequest request, HttpServletResponse response) 
				throws SQLException, ServletException, IOException {
			String messageErreur = "Login ou mot de passe incorrect !";
			String username  = request.getParameter("username");
			String password = request.getParameter("password");
			int id=0;
			User userConnected =  null;
			List<Picture> partageIma = null ;
			if((userConnected = userDAO.connectUser(username, password))!= null) {
				id =userConnected.getId();
				HttpSession session = request.getSession(false);
				partageIma = new ArrayList<Picture>();
				partageIma = pictureDAO.listPictureByAlbumPartager(id);
				session.setAttribute("userConnected", userConnected);
				session.setAttribute("pictureShared",partageIma);

				if(userConnected.getTypeUser() == 1) {
					response.sendRedirect("admin/accueil");
				}else {
					response.sendRedirect("user/user_dashboard");
				}
			}else {
				request.setAttribute("erreur", messageErreur);
				getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				}
	}
	 
	 
	 private void insertUser(HttpServletRequest request, HttpServletResponse response)
		        throws SQLException,ServletException, IOException {
		        Map<String, String> erreurs = new HashMap<String, String>();
		        String name = request.getParameter("names");
		        String lastname = request.getParameter("lastname");
		        String password  = request.getParameter("password");
		        String username = request.getParameter("username");
		        String password_conf = request.getParameter("password_confirm");
		        
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
		 	         userDAO.insertUser(newUser);
			         response.sendRedirect("login");
		 	         
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
			
			private void connexion(HttpServletRequest request, HttpServletResponse response) 
					throws SQLException, ServletException, IOException {
				String messageErreur = "Login ou mot de passe incorrect !";
				String username  = request.getParameter("username");
				String password = request.getParameter("password");
				User userConnected =  new User();

				if((userConnected = userDAO.connectUser(username, password))!= null) {
					HttpSession session = request.getSession(false);
					List<Albums> listPersonalAlbum =new ArrayList<Albums>();
					listPersonalAlbum = albumsDAO.listAlbumsPersonal(userConnected.getId());
					request.setAttribute("listPersonalAlbum", listPersonalAlbum);
					session.setAttribute("le_membre", userConnected);
					response.sendRedirect("dashboard");
				}else {
					request.setAttribute("erreur", messageErreur);
					getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				}
				
			}
			
			private void listImage(HttpServletRequest request, HttpServletResponse response)
			           throws SQLException, IOException, ServletException {
			        List<Picture> listImages = pictureDAO.getAllPicturePublic();
			        request.setAttribute("galerie", listImages);
			 }
}
