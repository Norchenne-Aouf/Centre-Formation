package Model;

public class FormationModel {
	private Integer idF;
	private String nomF;
	private String domaineF;
	private String typeF;
	private String motCleF;
	private String descF;
	
	
	public FormationModel() {
		super();
	}
	
	public FormationModel(Integer idF, String nomF, String domaineF,String typeF, String motCleF, String descF) {
		super();
		this.idF = idF;
		this.nomF = nomF;
		this.domaineF = domaineF;
		this.typeF = typeF;
		this.motCleF = motCleF;
		this.descF = descF;
	}
	public Integer getIdF() {
		return idF;
	}
	public void setIdF(Integer idF) {
		this.idF = idF;
	}
	public String getNomF() {
		return nomF;
	}
	public void setNomF(String nomF) {
		this.nomF = nomF;
	}
	public String getDomaineF() {
		return domaineF;
	}
	public void setDomaineF(String domaineF) {
		this.domaineF = domaineF;
	}
	public String  getTypeF() {
		return typeF;
	}
	public void setTypeF(String typeF ) {
		this.typeF = typeF;
	}
	public String getMotCleF() {
		return motCleF;
	}
	public void setMotCleF(String motCleF) {
		this.motCleF = motCleF;
	}
	public String getDescF() {
		return descF;
	}
	public void setDescF(String descF) {
		this.descF = descF;
	}

	

}
