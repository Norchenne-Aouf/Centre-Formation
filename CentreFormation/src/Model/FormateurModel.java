package Model;

import java.sql.Date;

public class FormateurModel {
	private Integer idFo;
	private String nom;
	private String prenom;
	private Date dateD;
	private String sexe;
	private String domaine;
	private String email;
	private Integer numTlf;
	
	
	public FormateurModel() {
		super();
	}
	public FormateurModel(Integer idFo, String nom, String prenom, Date dateD, String sexe, String domaine,
			String email, Integer numTlf) {
		super();
		this.idFo = idFo;
		this.nom = nom;
		this.prenom = prenom;
		this.dateD = dateD;
		this.sexe = sexe;
		this.domaine = domaine;
		this.email = email;
		this.numTlf = numTlf;
	}
	public Integer getIdFo() {
		return idFo;
	}
	public void setIdFo(Integer idFo) {
		this.idFo = idFo;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Date getDateD() {
		return dateD;
	}
	public void setDateD(Date dateD) {
		this.dateD = dateD;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getDomaine() {
		return domaine;
	}
	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getNumTlf() {
		return numTlf;
	}
	public void setNumTlf(Integer numTlf) {
		this.numTlf = numTlf;
	}

	
}
