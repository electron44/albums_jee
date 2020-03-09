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

import beans.User;
import databases.DatabaseConnection;
public class UserDAO {
  
	 private String jdbcURL;
	 private String jdbcUsername;
	 private String jdbcPassword;
	 private Connection jdbcConnection;
   
	 public UserDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
     
    
   
     
    public boolean insertUser(User User) throws SQLException {
        String sql = "INSERT INTO user (name, lastname, username,password) VALUES (?,?,?,?)";   
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, User.getName());
        statement.setString(2, User.getLastname());
        statement.setString(3, User.getUsername());
        statement.setString(4, User.getPassword());
        
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
    
    public boolean insertUserByAdmin(User User) throws SQLException {
        String sql = "INSERT INTO user (name, lastname, username,password,typeUser) VALUES (?,?,?,?,?)";   
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, User.getName());
        statement.setString(2, User.getLastname());
        statement.setString(3, User.getUsername());
        statement.setString(4, User.getPassword());
        statement.setInt(5, User.getTypeUser());
        
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
    
    public boolean insertAdmin(User User) throws SQLException {
        String sql = "INSERT INTO user (name, lastname, username,password,typeUser) VALUES (?,?,?,?,?)";   
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, User.getName());
        statement.setString(2, User.getLastname());
        statement.setString(3, User.getUsername());
        statement.setString(4, User.getPassword());
        statement.setInt(5, 1); 
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowInserted;
    }
     
    public List<User> listAllUsers() throws SQLException {
        List<User> listUser = new ArrayList<>();
        User User = null;
        String sql = "SELECT * FROM User where typeUser = 0 and is_deleted  = 0";
         
        connect();
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String lastname = resultSet.getString("name");
            String name = resultSet.getString("lastname");
            String password = resultSet.getString("password");
            int type = resultSet.getInt("typeUser");
            int is_deleted = resultSet.getInt("is_deleted");
             
            User = new User(id,name,lastname,username,password,type,is_deleted);
            listUser.add(User);
        }
         
        resultSet.close();
        statement.close();

         disconnect();
        return listUser;
    }
     
    public boolean deleteUser(User User) throws SQLException {
        String sql = "Update User set is_deleted = 1 where id = ?";
        connect(); 
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, User.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;     
    }
    
     
    public boolean updateUser(User User) throws SQLException {
        String sql = "UPDATE User SET username = ?, name = ?, lastname = ?";
        sql += " WHERE id = ? and is_deleted = 0";
         connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, User.getUsername());
        statement.setString(2, User.getName());
        statement.setString(3, User.getLastname());
        statement.setInt(4, User.getId());
       
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;     
    }
     
    public User getUser(int id) throws SQLException {
        User User = null;
        String sql = "SELECT * FROM User WHERE id = ? and is_deleted = 0";
         connect();
         
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
        	id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String lastname = resultSet.getString("lastname");
            String password = resultSet.getString("password");
            String username = resultSet.getString("username");
            int type =   resultSet.getInt("typeUser");
            int is_deleted = resultSet.getInt("is_deleted");
            User = new User(id, name, lastname,username, password,type,is_deleted);
        }
         
        resultSet.close();
        statement.close();
        disconnect();
        return User;
    }
    
    public User connectUser(String username , String password) throws SQLException{
    	User user = null;
    	String sql = "SELECT * from  user where username  = ?  and password = ? and is_deleted=0";
    	connect();
    	PreparedStatement statement  = jdbcConnection.prepareStatement(sql);
    	statement.setString(1, username);
    	statement.setString(2, password);
    	
    	ResultSet rs =  statement.executeQuery();
    	
    	if(rs.next()) {
    		int id = rs.getInt("id");
            String name = rs.getString("name");
            String lastname = rs.getString("lastname");
            int type =  rs.getInt("typeUser");
            int is_deleted = rs.getInt("is_deleted");  
            user = new User(id, name, lastname,username, password,type,is_deleted);
            
    	}
    	rs.close();
    	statement.close();
    	disconnect();
    	
    	return user;
    }
    
    public List<User> listAllOtherUsers(int identifiant) throws SQLException {
        List<User> listUser = new ArrayList<>();
        User User = null;
        String sql = "SELECT * FROM User where is_deleted  = 0 and id <> ?";
         
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1,identifiant);
        ResultSet resultSet = statement.executeQuery();
        
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String lastname = resultSet.getString("name");
            String name = resultSet.getString("lastname");
            String password = resultSet.getString("password");
            int type = resultSet.getInt("typeUser");
            int is_deleted = resultSet.getInt("is_deleted");
             
            User = new User(id,name,lastname,username,password,type,is_deleted);
            listUser.add(User);
        }
         
        resultSet.close();
        statement.close();

         disconnect();
        return listUser;
    }
    
    public boolean  checkExistence(String username) throws SQLException {
    	String sql  = "Select * from  user where username = ?";
    	connect();
    	PreparedStatement  preparedStatement=  jdbcConnection.prepareStatement(sql);
    	boolean exists = false;
    	preparedStatement.setString(1, username);
    	
    	ResultSet rs = preparedStatement.executeQuery();
    	
    	if(rs.next()) {
    		exists = true;
    	}
    	return exists;
    }
    
    
}