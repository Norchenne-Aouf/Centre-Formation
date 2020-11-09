package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import Model.DepenseModel;
import Model.FormationModel;
import Model.RevenueModel;
import Model.SalleModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepenseService {
Connexion connectionDB =new Connexion();
	
	public void insertDepense(DepenseModel depense){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            ps =  connectionDB.con.prepareStatement("insert into depense(dateD,montant,moyenpaiement,regle,description,label) values(?,?,?,?,?,?)");
            ps.setDate(1, depense.getDateD());
            ps.setInt(2,  depense.getMontant());
            ps.setString(3,  depense.getMoyenPaiement());
            ps.setString(4,  depense.getRegle());
            ps.setString(5,  depense.getDescription());
            ps.setString(6,  depense.getLabel());

            ps.execute();
        } catch (Exception e) {
              System.out.println(e);
        }
    }
	
	 public ObservableList<DepenseModel> showDepenses() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query = "SELECT * FROM depense order by idD asc";
	    	ObservableList<DepenseModel> depenseList = FXCollections.observableArrayList();
	    	try {
	            res = connectionDB.stat.executeQuery(query);
				DepenseModel depenses;
				while(res.next()) {
					depenses = new DepenseModel(res.getInt("idD"),res.getDate("dateD"),res.getInt("montant")
							,res.getString("moyenpaiement"),res.getString("regle"),res.getString("description"),res.getString("label"));
					depenseList.add(depenses);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return depenseList;
		}
	

	
	 public void updateDepense(DepenseModel depense){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps;
	            ps =  connectionDB.con.prepareStatement("update depense "
	            	  + "set dateD=? ,montant=? ,moyenpaiement=? ,regle=?,description=?,label=? where idD=?");
	            ps.setDate(1, depense.getDateD());
	            ps.setInt(2,  depense.getMontant());
	            ps.setString(3,  depense.getMoyenPaiement());
	            ps.setString(4,  depense.getRegle());
	            ps.setString(5,  depense.getDescription());
	            ps.setString(6,  depense.getLabel());
	            ps.setInt(7, depense.getIdD());

	            ps.execute();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deleteDepense(DepenseModel depense){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from depense where idD='"+depense.getIdD()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
		 
		 
		 public  Vector<DepenseModel>  showDepensesRapportAnnuel(int annuel) {
		        connectionDB.connex();
		        Vector<DepenseModel> V= new Vector();
			 	ResultSet res = null;
		    	String query = "SELECT SUM(Montant) FROM depense where dateD like '%/"+annuel+"%'";
		    	try {
					res = connectionDB.stat.executeQuery(query);
					while (res.next()) {
						V.add(new DepenseModel(res.getInt("idD"),res.getDate("dateD"),res.getInt("montant")
								,res.getString("moyenpaiement"),res.getString("regle"),res.getString("description"),res.getString("label")));
							
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return V;
			}
		 
		 public  int  totalDepensesRapportAnnuel(int annuel) {
		        connectionDB.connex();
			 	ResultSet res = null;
			 	int a = 0;
		    	String query = "select sum(montant) as total from depense where dateD like '%/"+annuel+"%'";
		    	try {
					res = connectionDB.stat.executeQuery(query);
					while (res.next()) {
						a=res.getInt("total");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return a;
			}
		 public  Vector<DepenseModel>  showDepensesRapportMensuel(int mensuel,int annuel) {
		        connectionDB.connex();
		        Vector<DepenseModel> V= new Vector();
			 	ResultSet res = null;
		    	String query = "SELECT * FROM depense where dateD like '%"+mensuel+"/"+annuel+"'";
		    	try {
					res = connectionDB.stat.executeQuery(query);
					while (res.next()) {
						V.add(new DepenseModel(res.getInt("idD"),res.getDate("dateD"),res.getInt("montant")
								,res.getString("moyenpaiement"),res.getString("regle"),res.getString("description"),res.getString("label")));
							
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return V;
			}
		 
		 public  int  totalDepensesRapportMensuel(int mensuel,int annuel) {
		        connectionDB.connex();
			 	ResultSet res = null;
			 	int a = 0;
		    	String query = "select sum(montant) as total from depense where dateD like '%"+mensuel+"/"+annuel+"%'";
		    	try {
					res = connectionDB.stat.executeQuery(query);
					while (res.next()) {
						a=res.getInt("total");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return a;
			}
		 
		 
		 public ResultSet show(String y) {
			 	ResultSet res = null;
		        connectionDB.connex();
		    	String query = "SELECT sum(montant) as m  FROM depense WHERE dateD like '%/"+y+"%'";
		    	try {
		            res = connectionDB.stat.executeQuery(query);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				return res;
			}
}
