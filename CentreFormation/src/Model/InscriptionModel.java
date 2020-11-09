package Model;

import java.sql.Date;

public class InscriptionModel {

	private int idI;
	private SessionModel session;
	private ClientModel client;
	private Date dateCreation;
	private String moyenPaiement;
	private int count;
	
	public InscriptionModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public InscriptionModel(int idI, SessionModel session, ClientModel client, Date dateCreation,String moyenPaiement) {
		super();
		this.idI = idI;
		this.session = session;
		this.client = client;
		this.dateCreation = dateCreation;
		this.moyenPaiement=moyenPaiement;
	}

	
	public int getIdI() {
		return idI;
	}


	public void setIdI(int idI) {
		this.idI = idI;
	}


	public SessionModel getSession() {
		return session;
	}


	public void setSession(SessionModel session) {
		this.session = session;
	}


	public ClientModel getClient() {
		return client;
	}


	public void setClient(ClientModel client) {
		this.client = client;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getNomSValue() {
        return session.getNomS();
    }
	public String getHeurDebutValue() {
        return session.getHeurDebut();
    }
	public String getHeurFinValue() {
        return session.getHeurFin();
    }
	public String getJourValue() {
        return session.getJour();
    }
	public String getFormationValue() {
        return session.getFrmtion().getNomF();
    }
	
	public String getSalleValue() {
        return session.getSalle().getNomSa();
    }
	
	public String getClientValue() {
        return client.getNomC()+" "+client.getPrenomC();
    }

	public void setCount(int count) {
		this.count = count;
	}
	public int getCountValue() {
        return count;
    }
	
	public String getMoyenPaiement() {
		return moyenPaiement;
	}


	public void setMoyenPaiement(String moyenPaiement) {
		this.moyenPaiement = moyenPaiement;
	}
	
	
	
	
	
}
