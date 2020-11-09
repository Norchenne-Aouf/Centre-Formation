package Model;

import java.sql.Date;

public class RevenueModel {
	private int idR;
	private Date dateR;
	private int montant;
	private String moyenPaiement;
	private String regle;
	private String description;
	private int etat;
	private String label;
		
	public RevenueModel() {
		super();
	}
	public RevenueModel(int idR, Date dateR, int montant, String moyenPaiement, String regle, String description,int etat,String label) {
		super();
		this.idR = idR;
		this.dateR = dateR;
		this.montant = montant;
		this.moyenPaiement = moyenPaiement;
		this.regle = regle;
		this.description = description;
		this.etat = etat;
		this.label = label;
	}
	public int getIdR() {
		return idR;
	}
	public void setIdR(int idR) {
		this.idR = idR;
	}
	public Date getDateR() {
		return dateR;
	}
	public void setDateR(Date dateR) {
		this.dateR = dateR;
	}
	public int getMontant() {
		return montant;
	}
	public void setMontant(int montant) {
		this.montant = montant;
	}
	public String getMoyenPaiement() {
		return moyenPaiement;
	}
	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}
	public String getRegle() {
		return regle;
	}
	public void setRegle(String regle) {
		this.regle = regle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getEtat() {
		return etat;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
	
}
