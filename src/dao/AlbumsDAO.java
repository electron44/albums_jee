package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Result;

import beans.Albums;
import beans.Picture;
import beans.User;
import databases.DatabaseConnection;
public class AlbumsDAO {
  
	 private String jdbcURL;
	 private String jdbcUsername;
	 private String jdbcPassword;
	 private Connection jdbcConnection;
   
	 public AlbumsDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
     
   
     
    public boolean addAlbums(Albums albums) throws SQLException {
    	
        String sql = "INSERT INTO albums (nom,type,userID) VALUES (?,?,?)";   
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, albums.getNom());
        statement.setString(2,albums.getType());
        statement.setInt(3, albums.getUser().getId());
        
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
    
      
     
    public List<Albums> listAlbums() throws SQLException {
        List<Albums> listAlbums = new ArrayList<>();
        Albums albums = null;
        User user = null;
         String sql = "SELECT * FROM albums where type not LIKE \"prive\" ";
         connect();
   
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String type = resultSet.getString("type");
            int ownerID = resultSet.getInt("userID");
             albums = new Albums();
             albums.setId(id);
             albums.setNom(nom);
             albums.setType(type);
             user = getProprietaire(ownerID);
             albums.setUser(user);           
            listAlbums.add(albums);
        }
         
        resultSet.close();
        statement.close();

         disconnect();
        return listAlbums;
    }
    
  
    
    public List<Albums> listAlbumsPersonal(int identifiant) throws SQLException {
        List<Albums> listAlbums = new ArrayList<>();
        Albums albums = null;
        User user = null;
         String sql = "SELECT * FROM albums where userID = ?";
         connect();
   
        
         PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql);
         preparedStatement.setInt(1, identifiant);
         ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String type = resultSet.getString("type");
            int ownerID = resultSet.getInt("userID");
             albums = new Albums();
             albums.setId(id);
             albums.setNom(nom);
             albums.setType(type);
             user = getProprietaire(ownerID);
             albums.setUser(user);           
            listAlbums.add(albums);
        }
         
        resultSet.close();
        preparedStatement.close();

         disconnect();
        return listAlbums;
    }
    public List<Albums> listAllPrivate(int identifiant) throws SQLException {
        List<Albums> listAlbums = new ArrayList<>();
        Albums albums = null;
        User user = null;
         String sql = "SELECT * FROM albums where userID =   ? and type like \"prive\" ";
         connect();
   
        
         PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql);
         preparedStatement.setInt(1, identifiant);
         ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String type = resultSet.getString("type");
            int ownerID = resultSet.getInt("userID");
             albums = new Albums();
             albums.setId(id);
             albums.setNom(nom);
             albums.setType(type);
             user = getProprietaire(ownerID);
             albums.setUser(user);           
            listAlbums.add(albums);
        }
         
        resultSet.close();
        preparedStatement.close();

         disconnect();
        return listAlbums;
    }
    
     
    public User getProprietaire(int id) throws SQLException {
    	String sql = "Select name ,lastname , username from albums INNER JOIN user on user.id = ?";
    	User admin = null;
    	connect();
    	PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    	statement.setInt(1, id);
    	ResultSet rs  =  statement.executeQuery();
    	while(rs.next()) {
    		String name = rs.getString("name");
    		String lastname = rs.getString("lastname");
    		admin = new User();
    		admin.setLastname(lastname);
    		admin.setName(name);
    	}
    	
    	return admin;
    }
       
    
    public boolean deleteAlbums(Albums albums) throws SQLException {
        String sql = "DELETE FROM albums where id = ?";
        connect(); 
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, albums.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
     
    public Albums getAlbums(int id) throws SQLException {
        Albums albums = null;
        String sql = "SELECT * FROM albums WHERE id = ?";
         connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
        	id = resultSet.getInt("id");
            String name = resultSet.getString("nom");
            String type = resultSet.getString("type");
            int ownerID = resultSet.getInt("userID");
            
      
            albums = new Albums(id,name,type,new User(ownerID));
        }
         
        resultSet.close();
        statement.close();
        disconnect();
        return albums;
    }
    
    public boolean updateAlbum(Albums albums) throws SQLException {
        String sql = "UPDATE Albums SET nom = ?, type = ?";
        sql += " WHERE id = ?";
         connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, albums.getNom());
        statement.setString(2, albums.getType());
        statement.setInt(3, albums.getId()); 
       
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;     
    }
    
    public boolean addPartage(int idAlbum , int usersId) throws SQLException {
    	String sql = "INSERT into sharedalbum (idAlbum,UsersId) values (?,?)";
    	connect();
    	PreparedStatement statement = jdbcConnection.prepareStatement(sql);
    	statement.setInt(2, usersId);
    	statement.setInt(1, idAlbum);
    	
    	boolean inserted = statement.executeUpdate() > 0;
    	statement.close();
        disconnect();
        return inserted;
    }
    
    public List<Albums> listSharedAlbum(int identifiant) throws SQLException{
    	List<Albums> listAlbums = new ArrayList<>();
        Albums albums = null;
        User user = null;
        
    	String sql = " Select * from albums inner join sharedalbum on sharedalbum.idalbum = albums.id  where albums.userID <> ?  and albums.type like \"prive\"";
    	connect();
    	   
        
        PreparedStatement preparedStatement = jdbcConnection.prepareStatement(sql);
        preparedStatement.setInt(1, identifiant);
        ResultSet resultSet = preparedStatement.executeQuery();
        
       while (resultSet.next()) {
           int id = resultSet.getInt("id");
           String nom = resultSet.getString("nom");
           String type = resultSet.getString("type");
           int ownerID = resultSet.getInt("userID");
            albums = new Albums();
            albums.setId(id);
            albums.setNom(nom);
            albums.setType(type);
            user = getProprietaire(ownerID);
            albums.setUser(user);           
           listAlbums.add(albums);
       }
        
       resultSet.close();
       preparedStatement.close();

        disconnect();
       return listAlbums;
    }
    
    
    
    
   
    
      
}