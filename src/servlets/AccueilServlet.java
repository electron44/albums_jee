package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Picture;
import dao.AlbumsDAO;
import dao.PictureDAO;
import dao.UserDAO;

/**
 * Servlet implementation class AccueilServlet
 */
@WebServlet("")
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PictureDAO pictureDAO = null;
	
	 public void init() {
	        String jdbcURL = "jdbc:mysql://localhost:3306/ousmane_j";
	        String jdbcUsername = "root";
	        String jdbcPassword = "";
	        pictureDAO = new PictureDAO(jdbcURL, jdbcUsername, jdbcPassword);

	    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccueilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			getALLPublicPicture(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	private void getALLPublicPicture(HttpServletRequest request , HttpServletResponse response) throws SQLException, IOException {
		List<Picture> listImages =  new ArrayList<Picture>();
		listImages = pictureDAO.getAllPicturePublic();
		request.setAttribute("galerie", listImages);
	}
	
	
	
	


}
