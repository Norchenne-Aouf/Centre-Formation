package Model;

import java.sql.Date;

public class ClientModel {

	private int idC;
	private String nomC;
	private String prenomC;
	private Date dateNaiss;
	private String ville;
	private String metier;
	private String sexe;
	private Integer numTlf;
	
	
	public ClientModel() {
		super();
	}
	public ClientModel(int idC, String nomC, String prenomC, Date dateNaiss, String ville, String metier, String sexe,
			Integer numTlf) {
		super();
		this.idC = idC;
		this.nomC = nomC;
		this.prenomC = prenomC;
		this.dateNaiss = dateNaiss;
		this.ville = ville;
		this.metier = metier;
		this.sexe = sexe;
		this.numTlf = numTlf;
	}
	public int getIdC() {
		return idC;
	}
	public void setIdC(int idC) {
		this.idC = idC;
	}
	public String getNomC() {
		return nomC;
	}
	public void setNomC(String nomC) {
		this.nomC = nomC;
	}
	public String getPrenomC() {
		return prenomC;
	}
	public void setPrenomC(String prenomC) {
		this.prenomC = prenomC;
	}
	public Date getDateNaiss() {
		return dateNaiss;
	}
	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getMetier() {
		return metier;
	}
	public void setMetier(String metier) {
		this.metier = metier;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public Integer getNumTlf() {
		return numTlf;
	}
	public void setNumTlf(Integer numTlf) {
		this.numTlf = numTlf;
	}
	
	
}
