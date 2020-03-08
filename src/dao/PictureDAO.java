package dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Part;

import com.mysql.cj.xdevapi.Result;

import beans.Albums;
import beans.Picture;
import beans.User;
import databases.DatabaseConnection;
public class PictureDAO {
  
	 private String jdbcURL;
	 private String jdbcUsername;
	 private String jdbcPassword;
	 private Connection jdbcConnection;
   
	 public PictureDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
     
    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                                        jdbcURL, jdbcUsername, jdbcPassword);
        }
    }
     
    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
     
    
   
     
    public boolean insertPicture(Picture picture,Part parfile) throws SQLException, IOException {
        String sql = "INSERT INTO Picture (titre, description,keywords,dateCreation,fichier,albumID,fichierName) VALUES (?,?,?,?,?,?,?)";   
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1,picture.getTitre());
        statement.setString(2, picture.getDescription());
        statement.setString(3,picture.getKeywords());
        statement.setString(4,picture.getDateCreation().toString());
        InputStream inputStream = parfile.getInputStream();
        
        if(inputStream !=null)
        	statement.setBlob(5, inputStream);
        statement.setInt(6, picture.getAlbum().getId());
        statement.setString(7,picture.getFichierName());
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
     
    
    public List<Picture> getAllPicturePublic() throws SQLException, IOException {
    	 List<Picture> listPictures = new ArrayList<>();
         Picture picture = null;
         
          String sql = "SELECT * FROM picture INNER JOIN albums on albums.id = picture.albumID where albums.type LIKE \"public\"";
          connect();
    
         
          PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql);
          ResultSet resultSet = preparedStatement.executeQuery();
          
         while (resultSet.next()) {
           int id = resultSet.getInt("id");
           String titre =  resultSet.getString("titre");
           String description = resultSet.getString("description");
           String dateCreation = resultSet.getString("dateCreation");
           Blob blob = resultSet.getBlob("fichier");
           String keywords = resultSet.getString("keywords");
           
           InputStream inputStream = blob.getBinaryStream();
           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
           byte[] buffer = new byte[4096];
           int bytesRead = -1;
            
           while ((bytesRead = inputStream.read(buffer)) != -1) {
               outputStream.write(buffer, 0, bytesRead);
           }
            
           byte[] imageBytes = outputStream.toByteArray();
            
           String fichierName = Base64.getEncoder().encodeToString(imageBytes);
            
           inputStream.close();
           outputStream.close();
           picture = new Picture();
           picture.setId(id);
           picture.setTitre(titre);
           picture.setDescription(description);
           picture.setFichierName(fichierName);  
           picture.setKeywords(keywords);
           
           listPictures.add(picture);
           inputStream.close();
           outputStream.close();
         }
 		return listPictures;
    }
    
    
    @SuppressWarnings("null")
	public List<Picture> listPersonalPicture(int identifiant) throws SQLException, IOException {
        List<Picture> listPictures = new ArrayList<>();
        Picture picture = null;
        
         String sql = "SELECT picture.titre, picture.id, picture.dateCreation, picture.fichier FROM Picture INNER JOIN Albums"
         														   + " on picture.albumID = albums.id "
         															        + "INNER JOIN user on albums.userID = ?";
         connect();
   
        
         PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql);
         preparedStatement.setInt(1, identifiant);
         ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
          int id = resultSet.getInt("picture.id");
          String titre =  resultSet.getString("picture.titre");
          String description = resultSet.getString("picture.description");
          String dateCreation = resultSet.getString("picture.dateCreation");
          Blob blob = resultSet.getBlob("picture.fichier");
          
          
          InputStream inputStream = blob.getBinaryStream();
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          byte[] buffer = new byte[4096];
          int bytesRead = -1;
           
          while ((bytesRead = inputStream.read(buffer)) != -1) {
              outputStream.write(buffer, 0, bytesRead);
          }
           
          byte[] imageBytes = outputStream.toByteArray();
           
          String fichierName = Base64.getEncoder().encodeToString(imageBytes);
           
          inputStream.close();
          outputStream.close();
          picture = new Picture();
          picture.setId(id);
          picture.setTitre(titre);
          picture.setDescription(description);
          picture.setFichierName(fichierName);           
          
          listPictures.add(picture);
          inputStream.close();
          outputStream.close();
        }
		return listPictures;
    }
    
    
    
    public List<Picture> listPictureByAlbum(int identifiant) throws SQLException, IOException {
        List<Picture> listPictures = new ArrayList<>();
        Picture picture = null;
        
         String sql = "SELECT picture.titre, picture.id, picture.description, picture.dateCreation, picture.fichier FROM picture  inner join Albums on albums.id = picture.albumID inner join user on user.id = albums.userID where picture.albumID = ? ";
         connect();
   
        
         PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql);
         preparedStatement.setInt(1, identifiant);
         ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
          int id = resultSet.getInt("picture.id");
          String titre =  resultSet.getString("picture.titre");
          String description = resultSet.getString("picture.description");
          String dateCreation = resultSet.getString("picture.dateCreation");
          Blob blob = resultSet.getBlob("picture.fichier");
          
          InputStream inputStream = blob.getBinaryStream();
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          byte[] buffer = new byte[4096];
          int bytesRead = -1;
           
          while ((bytesRead = inputStream.read(buffer)) != -1) {
              outputStream.write(buffer, 0, bytesRead);
          }
           
          byte[] imageBytes = outputStream.toByteArray();
           
          String fichierName = Base64.getEncoder().encodeToString(imageBytes);
           
          inputStream.close();
          outputStream.close();
          picture = new Picture();
          picture.setId(id);
          picture.setTitre(titre);
          picture.setDescription(description);
          picture.setFichierName(fichierName);           
          
          listPictures.add(picture);
          inputStream.close();
          outputStream.close();
        }
		return listPictures;
    }
    
    public List<Picture> listPictureByAlbumPartager(int identifiant) throws SQLException, IOException {
        List<Picture> listPictures = new ArrayList<>();
        Picture picture = null;
        
         String sql = "Select * from picture inner join albums on picture.albumID =albums.id INNER JOIN sharedalbum on sharedalbum.idAlbum =albums.id where albums.userID <> ? and sharedalbum.usersID = ? ";
         connect();
   
        
         PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql);
         preparedStatement.setInt(1, identifiant);
         preparedStatement.setInt(2, identifiant);
         ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
          int id = resultSet.getInt("picture.id");
          String titre =  resultSet.getString("picture.titre");
          String description = resultSet.getString("picture.description");
          String dateCreation = resultSet.getString("picture.dateCreation");
          Blob blob = resultSet.getBlob("picture.fichier");
          
          InputStream inputStream = blob.getBinaryStream();
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          byte[] buffer = new byte[4096];
          int bytesRead = -1;
           
          while ((bytesRead = inputStream.read(buffer)) != -1) {
              outputStream.write(buffer, 0, bytesRead);
          }
           
          byte[] imageBytes = outputStream.toByteArray();
           
          String fichierName = Base64.getEncoder().encodeToString(imageBytes);
           
          inputStream.close();
          outputStream.close();
          picture = new Picture();
          picture.setId(id);
          picture.setTitre(titre);
          picture.setDescription(description);
          picture.setFichierName(fichierName);           
          
          listPictures.add(picture);
          inputStream.close();
          outputStream.close();
        }
		return listPictures;
    }
    
    
    
}