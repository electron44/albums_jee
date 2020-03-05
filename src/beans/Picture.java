package beans;

import java.io.File;
import java.sql.Date;

public class Picture {
		private Integer id;
		private String titre;
		
		private String description;
		private Albums album;
		private String keywords;
		private String dateCreation;
		private byte[] fichier;
		private String fichierName;		
		
		public Picture() {
			
		}
		
		public Picture(int id) {
			
		}
		
		public Picture(Integer id, String titre, String description, Albums album, String keywords, String dateCreation,
				byte[] fichier, String fichierName) {
			super();
			this.id = id;
			this.titre = titre;
			this.description = description;
			this.album = album;
			this.keywords = keywords;
			this.dateCreation = dateCreation;
			this.fichier = fichier;
			this.fichierName = fichierName;
		}
		
		public Picture(Integer id , String titre, String description,String fichierName) {
			this.id = id;
			this.titre = titre;
			this.description = description;
			this.fichierName = fichierName;
		}
		
		public String getTitre() {
			return titre;
		}

		public void setTitre(String titre) {
			this.titre = titre;
		}

		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Albums getAlbum() {
			return album;
		}
		public void setAlbum(Albums album) {
			this.album = album;
		}
		public String getKeywords() {
			return keywords;
		}
		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}
		public String getDateCreation() {
			return dateCreation;
		}
		public void setDateCreation(String dateCreation2) {
			this.dateCreation = dateCreation2;
		}
		public byte[] getFichier() {
			return fichier;
		}
		public void setFichier(byte[] fichier) {
			this.fichier = fichier;
		}
		public String getFichierName() {
			return fichierName;
		}
		public void setFichierName(String fichierName) {
			this.fichierName = fichierName;
		}
		
		
		
		
		
		
		
		
}
