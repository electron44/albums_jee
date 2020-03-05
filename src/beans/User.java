package beans;

public class User{
	

	private int id;
	
	private String name;
	private String lastname;
	private String username;
	private String password;
	private int typeUser;
	private int is_deleted;
	
	
	public User() {
		
	}
	
	public User(int id) {
		this.id =id;
	}
	
	
	
	public User(int id, String name, String lastname, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}

	public User(int id, String name, String lastname, String username, String password, int typeUser, int is_deleted) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.typeUser = typeUser;
		this.is_deleted = is_deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}

	public int getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(int is_deleted) {
		this.is_deleted = is_deleted;
	}

}