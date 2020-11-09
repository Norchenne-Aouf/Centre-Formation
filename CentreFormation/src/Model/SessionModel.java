package Model;

import java.sql.Date;
import java.util.Vector;

public class SessionModel {

	private int idS;
	private String nomFormation;
	private FormationModel frmtion;
	private String prenomFormateur;
	private Vector<FormateurModel> frmteur; 
	private String nomS;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	private int capacite;
	private String heurDebut;
	private String heurFin;
	private String nomSalle;
	private SalleModel salle;
	private String jour;
	private int frais;
	private int nbreHeur;
	
	public SessionModel() {
		super();
	}
	public SessionModel(int idS, FormationModel frmtion, String nomS, String description, Date dateDebut,
			Date dateFin, int capacite, String heurDebut, String heurFin, SalleModel salle, String jour, int frais,int nbreHeur) {
		super();
		this.idS = idS;
		this.frmtion = frmtion;
		this.nomS = nomS;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.capacite = capacite;
		this.heurDebut = heurDebut;
		this.heurFin = heurFin;
		this.salle = salle;
		this.jour = jour;
		this.frais = frais;
		this.nbreHeur = nbreHeur;
	}
	public SessionModel(int idS, FormationModel frmtion, Vector<FormateurModel> frmteur, String nomS, String description,
			Date dateDebut, Date dateFin, int capacite, String heurDebut, String heurFin, SalleModel salle,
			String jour, int frais) {
		super();
		this.idS = idS;
		this.frmtion = frmtion;
		this.frmteur = frmteur;
		this.nomS = nomS;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.capacite = capacite;
		this.heurDebut = heurDebut;
		this.heurFin = heurFin;
		this.salle = salle;
		this.jour = jour;
		this.frais = frais;
	}



	

	public int getIdS() {
		return idS;
	}
	public void setIdS(int idS) {
		this.idS = idS;
	}
	public String getNomFormation() {
		return nomFormation;
	}
	public void setNomFormation(String nomFormation) {
		this.nomFormation = nomFormation;
	}
	
	public String getNomS() {
		return nomS;
	}
	public void setNomS(String nomS) {
		this.nomS = nomS;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	

	public String getPrenomFormateur() {
		return prenomFormateur;
	}

	public void setPrenomFormateur(String prenomFormateur) {
		this.prenomFormateur = prenomFormateur;
	}

	public FormationModel getFrmtion() {
		return frmtion;
	}

	public void setFrmtion(FormationModel frmtion) {
		this.frmtion = frmtion;
	}
	public Vector<FormateurModel> getFrmteur() {
		return frmteur;
	}

	public void setFrmteur(Vector<FormateurModel> frmteur) {
		this.frmteur = frmteur;
	}

	public String getNomSalle() {
		return nomSalle;
	}
	public void setNomSalle(String nomSalle) {
		this.nomSalle = nomSalle;
	}
	public SalleModel getSalle() {
		return salle;
	}
	public void setSalle(SalleModel salle) {
		this.salle = salle;
	}
	public String getJour() {
		return jour;
	}
	public void setJour(String jour) {
		this.jour = jour;
	}
	public int getFrais() {
		return frais;
	}
    public void setFrais(int frais) {
		this.frais = frais;
	}

	public String getHeurDebut() {
		return heurDebut;
	}

	public void setHeurDebut(String heurDebut) {
		this.heurDebut = heurDebut;
	}

	public String getHeurFin() {
		return heurFin;
	}

	public void setHeurFin(String heurFin) {
		this.heurFin = heurFin;
	}
	public int getNbreHeur() {
		return nbreHeur;
	}
	public void setNbreHeur(int nbreHeur) {
		this.nbreHeur = nbreHeur;
	}
	
	
	
}
