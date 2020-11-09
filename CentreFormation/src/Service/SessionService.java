package Service;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Collection;
import java.util.Vector;

import Model.FormateurModel;
import Model.FormationModel;
import Model.SalleModel;
import Model.SessionModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.FormateurModel;

public class SessionService {
Connexion connectionDB =new Connexion();

// pour remplir le combobox formation
	public  Vector<FormationModel>  formation() {
		Vector<FormationModel> V= new Vector();
        connectionDB.connex();
	 	ResultSet res = null;
    	String formation = "SELECT * FROM formation ";	
    	try {
			res = connectionDB.stat.executeQuery(formation);
			
			while (res.next()) {  
				//Integer idF, String nomF, String domaineF,String typeF, String motCleF, String descF
				V.add(new FormationModel(res.getInt("idf"),res.getString("nom"),res.getString("domaine"),res.getString("type"),res.getString("motcle"),res.getString("description")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return V;
	}
	
	public  String  verifType(String nomFomation) {
        connectionDB.connex();
        String type = "";
	 	ResultSet res ;
    	String formation = "SELECT type FROM formation where nom='"+nomFomation+"'";
    	try {
			res = connectionDB.stat.executeQuery(formation);
			while(res.next()) {
				type=res.getString("type");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return type ;
	}
	
	// pour remplir le combobox formateur
	public  Vector<FormateurModel>  formateur() {
        connectionDB.connex();
        Vector<FormateurModel> V= new Vector();
	 	ResultSet res = null;
    	String formateur = "SELECT * FROM formateur ";	
    	try {                       //Integer idFo, String nom, String prenom, String dateD, String sexe, String domaine,String email, Integer numTlf
			
			res = connectionDB.stat.executeQuery(formateur);
			while(res.next()) {
				V.add(new FormateurModel(res.getInt("idfo"),res.getString("nom"),res.getString("prenom"),res.getDate("dated"),res.getString("sexe"),res.getString("domaine"),res.getString("email"), res.getInt("numtlf")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return V;
	}
	// pour remplir le combobox salle
	public  Vector<SalleModel>  salle() {
        connectionDB.connex();
        Vector<SalleModel> V= new Vector();
	 	ResultSet res = null;
    	String salle = "SELECT * FROM salle ";	
    	try {
			res = connectionDB.stat.executeQuery(salle);
			while (res.next()) {
				V.add(new SalleModel(res.getInt("idSa"),res.getString("nomSa"),res.getString("type1"),res.getInt("nbreSa"),
						res.getInt("nbreEtage"),res.getInt("capacite"),res.getString("projection")));

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return V;
	}
	
	
	
	public  int  salleId(String nomSalle) {
        connectionDB.connex();
        int idSa = 0;
	 	ResultSet res ;
    	String salle = "SELECT idSa FROM salle where nomSa='"+nomSalle+"'";
    	try {
			res = connectionDB.stat.executeQuery(salle);
			while(res.next()) {
				idSa=res.getInt("idSa");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return idSa ;
	}
	
	// pour verifier disponibilité de la salle
		public  ResultSet  salleNonDispo(int i) {
	        connectionDB.connex();
		 	ResultSet res = null;
	    	String salleDis = "SELECT * FROM sessionn where salle='"+i+"'";
	    	try {
				res = connectionDB.stat.executeQuery(salleDis);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return res;
		}
		public  int  salleDispo(int i) {
	        connectionDB.connex();
		 	ResultSet res = null;
		 	int count = 0;
	    	String salleDis = "SELECT count(*) FROM sessionn WHERE EXISTS ( SELECT *  FROM sessionn  WHERE salle = '"+i+"') ";
	    	try {
				res = connectionDB.stat.executeQuery(salleDis);
				while(res.next()) {
					 count = res.getInt(1);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return count;
		}
		
	public  String  formationNom(int idFomation) {
        connectionDB.connex();
        String nom = null;
	 	ResultSet res ;
    	String formation = "SELECT * FROM formation where idF='"+idFomation+"'";
    	try {
			res = connectionDB.stat.executeQuery(formation);
			while(res.next()) {
				nom=res.getString("nom");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nom ;
	}
	
	
	public  String  formateurPrenom(int idFo) {
        connectionDB.connex();
        String prenomFomateur = null ;
	 	ResultSet res ;
    	String formateur = "SELECT prenom FROM formateur where idFo='"+idFo+"'";
    	try {
			res = connectionDB.stat.executeQuery(formateur);
			while(res.next()) {
				prenomFomateur=res.getString("prenom");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return prenomFomateur ;
	}


	public void insertSession(SessionModel session){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            PreparedStatement ps1;
            ps =  connectionDB.con.prepareStatement(""
            		+ "insert into sessionn(FORMATION,NOMS,Description,DATEDEBUT,DATEFIN,CAPACITE,HEURDEBUT,HEURFIN,jour,frais,salle,nbreHeur)"
            		+ " values(?,?,?,?,?,?,?,?,?,?,?,?)",new String[]{"ids"});
            
            ps.setInt(1,  session.getFrmtion().getIdF());
            ps.setString(2,  session.getNomS());
            ps.setString(3,  session.getDescription());
            ps.setDate(4,  session.getDateDebut());
            ps.setDate(5,  session.getDateFin());
            ps.setInt(6,  session.getCapacite());
            ps.setString(7,  session.getHeurDebut());
            ps.setString(8,  session.getHeurFin());
            ps.setString(9,  session.getJour());
            ps.setInt(10,  session.getFrais());
            ps.setInt(11,  session.getSalle().getIdSa());
            ps.setInt(12,  session.getNbreHeur());
            ps.execute();
            System.out.println("premiére requete");
           ResultSet idsess=ps.getGeneratedKeys();
           idsess.next();
            int K=idsess.getInt(1);
            
            for(int i=0; i<session.getFrmteur().size();i++) {
            	ps1=connectionDB.con.prepareStatement("insert into sessionformateur (ids,idfo) values(?,?)");
            	ps1.setInt(1, K);
            	ps1.setInt(2, session.getFrmteur().elementAt(i).getIdFo());
            	ps1.execute();
            }
            
        } catch (Exception e) {
              System.out.println(e.getMessage());
        }
    }
	
	
	 public ObservableList<SessionModel> showSessions() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query1 = "SELECt * "+

				"FROM sessionn s";
	    	ObservableList<SessionModel> SessionList = FXCollections.observableArrayList();
	    	try {        	
	    		res = connectionDB.stat.executeQuery(query1);
	            while(res.next()) {
	           
			        FormationModel formation=new FormationModel();
		            SalleModel salle=new SalleModel();	
		            
		            SessionModel session= new SessionModel();
					session.setIdS(res.getInt("idS"));
					
		            formation.setIdF(res.getInt("formation"));
					FormationService formationService=new FormationService();
					formation=formationService.recherche(formation);
					session.setNomFormation(formation.getNomF());
							
					salle.setIdSa(res.getInt("salle"));
					SalleService salleService = new SalleService();
					salle=salleService.recherche(salle);
					session.setNomSalle(salle.getNomSa());
					
					session.setNomS(res.getString("nomS"));
					session.setDateDebut(res.getDate("dateDebut"));
					session.setDateFin(res.getDate("dateFin"));
					session.setCapacite(res.getInt("capacite"));
					session.setHeurDebut(res.getString("heurDebut"));
					session.setHeurFin(res.getString("heurFin"));
					session.setJour(res.getString("jour"));
					session.setDescription(res.getString("description"));
					session.setFrais(res.getInt("frais")); 
		            SessionList.add(session);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SessionList;
		}
	 public void updateSession(SessionModel session){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps,ps1;
	            ps =  connectionDB.con.prepareStatement("update sessionn "
	            	  + "set formation=?,  nomS=? ,description=? ,dateDebut=?"
	            	  + " ,dateFin=?,capacite=?,heurDebut=?,heurFin=?, jour=?, frais=?, salle=?, nbreHeur=?"
	            	  + " where idS=?",new String[]{"ids"});
	            ps.setInt(1, session.getFrmtion().getIdF());
	            ps.setString(2,session.getNomS());
	            ps.setString(3, session.getDescription());
	            ps.setDate(4, session.getDateDebut());
	            ps.setDate(5, session.getDateFin());
	            ps.setInt(6, session.getCapacite());
	            ps.setString(7, session.getHeurDebut());
	            ps.setString(8, session.getHeurFin());
	            ps.setString(9, session.getJour());
	            ps.setInt(10, session.getFrais());
	            ps.setInt(11, session.getSalle().getIdSa());
	            ps.setInt(12, session.getNbreHeur());
	            ps.setInt(13, session.getIdS());

	            ps.execute();
	            System.out.println("premiére requete");
	            ResultSet idsess=ps.getGeneratedKeys();
	            idsess.next();
	             int K=idsess.getInt(1);
	             for(int i=0; i<session.getFrmteur().size();i++) {
	             	  ps1 =  connectionDB.con.prepareStatement("update sessionformateur "
	    	            	  + "set idFo=? where idS=?");
		            ps1.setInt(1, session.getFrmteur().elementAt(i).getIdFo());
	             	ps1.setInt(2, K);
	             	ps1.execute();
	             }
	             
	            
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deleteSession(SessionModel session){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from sessionn where idS='"+session.getIdS()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
		 
			public ResultSet showFormateur(int idC) {
			 	ResultSet res = null;
		        connectionDB.connex();
		    	String query = "select * from  SESSIONFORMATEUR sf " + 
		    			"inner join formateur f on f.idFo=sf.idFo " + 
		    			"	where sf.idS="+idC;
		    	try {
		            res = connectionDB.stat.executeQuery(query);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				return res;
			}
		
}
