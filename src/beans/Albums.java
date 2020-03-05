package beans;

import java.sql.Date;

public class Albums {
	private int id;
	private String nom;
	private String type;
	private User user;
	
	
	public Albums() {
		
	}
	
	public Albums(int id) {
		this.id = id;
	}

	public Albums(int id , String nom ,String type,User user) {
		this.id   =  id;
		this.nom  =  nom;
		this.type =  type;
		this.user = user;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
}
