package Service;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.util.Vector;

import Model.ClientModel;
import Model.FormateurModel;
import Model.FormationModel;
import Model.InscriptionModel;
import Model.SalleModel;
import Model.SessionModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

public class InscriptionService {
Connexion connectionDB =new Connexion();


public  Vector<SessionModel>  Session() {
    connectionDB.connex();
    Vector<SessionModel> V= new Vector();
    FormationModel f= new FormationModel();
    SalleModel s= new SalleModel();
 	ResultSet res = null;
	String session = "SELECT * FROM sessionn ";	
	try {                      
		res = connectionDB.stat.executeQuery(session);
		while(res.next()) {
			f.setIdF(res.getInt("formation"));
			s.setIdSa(res.getInt("salle"));
			V.add(new SessionModel(res.getInt("idS"),f,res.getString("nomS"), res.getString("description"),
					res.getDate("dateDebut"), res.getDate("dateFin"), res.getInt("capacite") , res.getString("heurDebut"),res.getString("heurFin"), s,
			res.getString("jour"), res.getInt("frais"),res.getInt("nbreHeur")));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return V;
}


public  Vector<ClientModel> client() {
	    connectionDB.connex();
	    Vector<ClientModel> V= new Vector();
		ResultSet res = null;
	 	String client = "SELECT * FROM client ";	
	 	try {
				res = connectionDB.stat.executeQuery(client);
				while(res.next()) {
						V.add(new ClientModel(res.getInt("idC"),res.getString("nomC"), res.getString("prenomC"), res.getDate("dateNaiss"),  res.getString("ville") , res.getString("metier"), res.getString("sexe"),
						res.getInt("numTlf")) ); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return V;
		}
	
	public  int  ClientId(String nomClient) {
        connectionDB.connex();
        int idC = 0;
	 	ResultSet res ;
    	String client = "SELECT idC FROM client where nomC='"+nomClient+"'";
    	try {
			res = connectionDB.stat.executeQuery(client);
			while(res.next()) {
				idC=res.getInt("idC");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return idC ;
	}
	
	public  int  formationId(String nomSession) {
        connectionDB.connex();
        int idF= 0;
	 	ResultSet res ;
    	String fo = "SELECT formation FROM sessionn where nomS='"+nomSession+"'";
    	try {
			res = connectionDB.stat.executeQuery(fo);
			while(res.next()) {
				idF=res.getInt("formation");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return idF ;
	}

	public  int  tarif(String nomSession) {
        connectionDB.connex();

       int frais = 0;
	 	ResultSet res ;
    	String fo = "SELECT frais FROM sessionn where nomS='"+nomSession+"'";
    	try {
			res = connectionDB.stat.executeQuery(fo);
			while(res.next()) {
				frais=res.getInt("frais");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return frais ;
	}

	public void insertInscription(InscriptionModel inscription){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            ps =  connectionDB.con.prepareStatement("insert into Inscription(idS,idC,dateCreation,moyenpaiement) values(?,?,SYSDATE,?)");
            ps.setInt(1, inscription.getSession().getIdS());
            ps.setInt(2,  inscription.getClient().getIdC());
            ps.setString(3,  inscription.getMoyenPaiement());
            
            ps.execute();
        } catch (Exception e) {
              System.out.println(e);
        }
    }
	
	 public ObservableList<InscriptionModel> showInscriptions() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query = "SELECT * FROM Inscription i inner join sessionn s on i.idS=s.idS  inner join formation f on s.formation=f.idF inner join salle ss on ss.idSa=s.salle " + 
	    			"inner join client c on i.idC=c.idC   order by idI asc";
	    	ObservableList<InscriptionModel> InscriptionList = FXCollections.observableArrayList();
	    	try {
	            res = connectionDB.stat.executeQuery(query);
				while(res.next()) {
					InscriptionModel inscrit= new InscriptionModel();
					
					inscrit.setIdI(res.getInt("idI"));
		            inscrit.setDateCreation(res.getDate("dateCreation"));
		            inscrit.setMoyenPaiement(res.getString("moyenpaiement"));

					SessionModel session= new SessionModel();
					FormationModel formation= new FormationModel();				
					SalleModel salle= new SalleModel();				
					
			         formation.setNomF(res.getString("nom"));
			         session.setFrmtion(formation);		            

			         salle.setNomSa(res.getString("nomSa"));
			         session.setSalle(salle);

		            session.setIdS(res.getInt("idS"));		            
		            session.setNomS(res.getString("nomS"));		            
		            session.setHeurDebut(res.getString("heurDebut"));
		            session.setHeurFin(res.getString("heurFin"));
		            session.setJour(res.getString("jour"));
		            
		            inscrit.setSession(session);
		            
		            ClientModel client= new ClientModel();
		            client.setNomC(res.getString("nomC"));		            
		            client.setPrenomC(res.getString("prenomC"));		            
		            inscrit.setClient(client);
		            
		            
					InscriptionList.add(inscrit);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return InscriptionList;
		}
	 public void updateInscription(InscriptionModel inscription){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps;
	            ps =  connectionDB.con.prepareStatement("update Inscription "
	            	  + "set idS=? ,idC=?,moyenpaiement=? where idI=?");
	            ps.setInt(1, inscription.getSession().getIdS());
	            ps.setInt(2,  inscription.getClient().getIdC());
	            ps.setString(3,  inscription.getMoyenPaiement());
	            ps.setInt(4,  inscription.getIdI());
	           

	            ps.execute();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deleteInscription(InscriptionModel inscription){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from Inscription where idI='"+inscription.getIdI()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
		 
		 public ResultSet showClientsIncrtiptions() {
			 	ResultSet res = null;
		        connectionDB.connex();
		    	String query = "SELECT count(*), c.nomC , c.prenomC " + 
		    			"FROM Inscription i, client c " + 
		    			"where i.idC=c.idC " + 
		    			"group by c.nomC , c.prenomC ";
		    	try {
		            res = connectionDB.stat.executeQuery(query);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				return res;
			} 
	
		 public ObservableList<InscriptionModel> showClientsIncrtiptionsTable() {
			 	ResultSet res;
		        connectionDB.connex();
		    	String query = "SELECT count(*) as count, c.nomC , c.prenomC " + 
		    			"FROM Inscription i, client c " + 
		    			"where i.idC=c.idC " + 
		    			"group by c.nomC , c.prenomC "
		    			+ "order by count desc";
		    	ObservableList<InscriptionModel> InscriptionList = FXCollections.observableArrayList();
		    	try {
		            res = connectionDB.stat.executeQuery(query);
					while(res.next()) {
						InscriptionModel inscrit= new InscriptionModel();
						ClientModel client= new ClientModel();
			            client.setNomC(res.getString("nomC"));		            
			            client.setPrenomC(res.getString("prenomC"));		            
			            inscrit.setClient(client);
			            
			            inscrit.setCount(res.getInt("count"));
			            
						InscriptionList.add(inscrit);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return InscriptionList;
			}
		
		
}
