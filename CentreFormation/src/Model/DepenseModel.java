package Model;

import java.sql.Date;

public class DepenseModel {
	private int idD;
	private Date dateD;
	private int montant;
	private String moyenPaiement;
	private String regle;
	private String description;
	private String label;

	
	public DepenseModel() {
		super();
	}
	
	public DepenseModel(int idD, Date dateD, int montant, String moyenPaiement, String regle, String description,String label) {
		super();
		this.idD = idD;
		this.dateD = dateD;
		this.montant = montant;
		this.moyenPaiement = moyenPaiement;
		this.regle = regle;
		this.description = description;
		this.label = label;

	}

	public int getIdD() {
		return idD;
	}
	public void setIdD(int idD) {
		this.idD = idD;
	}
	public Date getDateD() {
		return dateD;
	}
	public void setDateD(Date dateD) {
		this.dateD = dateD;
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
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
