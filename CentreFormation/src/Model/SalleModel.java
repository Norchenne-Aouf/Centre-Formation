package Model;

public class SalleModel {
	private int idSa;
	private String nomSa;
	private String type1;
	private int nbreSalle;
	private int nbreEtage;
	private int capacite;
	private String projection;
	
	
	
	public SalleModel() {
		super();
	}
	
	
	public SalleModel(int idSa, String nomSa, String type1, int nbreSalle, int nbreEtage, int capacite, String projection) {
		super();
		this.idSa = idSa;
		this.nomSa = nomSa;
		this.type1 = type1;
		this.nbreSalle = nbreSalle;
		this.nbreEtage = nbreEtage;
		this.capacite = capacite;
		this.projection = projection;
		
	}


	public int getIdSa() {
		return idSa;
	}
	public void setIdSa(int idSa) {
		this.idSa = idSa;
	}
	public String getNomSa() {
		return nomSa;
	}
	public void setNomSa(String nomSa) {
		this.nomSa = nomSa;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public int getNbreSalle() {
		return nbreSalle;
	}
	public void setNbreSalle(int nbreSalle) {
		this.nbreSalle = nbreSalle;
	}
	public int getNbreEtage() {
		return nbreEtage;
	}
	public void setNbreEtage(int nbreEtage) {
		this.nbreEtage = nbreEtage;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public String getProjection() {
		return projection;
	}
	public void setProjection(String projection) {
		this.projection = projection;
	}
	
}
