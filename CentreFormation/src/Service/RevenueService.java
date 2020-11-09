package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import Model.DepenseModel;
import Model.RevenueModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RevenueService {
Connexion connectionDB =new Connexion();
	
	public void insertRevenue(RevenueModel revenue){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            ps =  connectionDB.con.prepareStatement("insert into revenue(dateR,montant,moyenpaiement,regle,description,etat,label) values(?,?,?,?,?,?,?)");
            ps.setDate(1, revenue.getDateR());
            ps.setInt(2,  revenue.getMontant());
            ps.setString(3,  revenue.getMoyenPaiement());
            ps.setString(4,  revenue.getRegle());
            ps.setString(5,  revenue.getDescription());
            ps.setInt(6,  revenue.getEtat());
            ps.setString(7,  revenue.getLabel());
            ps.execute();
        } catch (Exception e) {
              System.out.println(e);
        }
    }
	
	 public ObservableList<RevenueModel> showRevenus() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query = "SELECT * FROM revenue order by idR asc";
	    	ObservableList<RevenueModel> revenueList = FXCollections.observableArrayList();
	    	try {
	            res = connectionDB.stat.executeQuery(query);
				RevenueModel revenues;
				while(res.next()) {
					revenues = new RevenueModel(res.getInt("idR"),res.getDate("dateR"),res.getInt("montant")
							,res.getString("moyenpaiement"),res.getString("regle"),res.getString("description"),res.getInt("etat"),res.getString("label"));
					revenueList.add(revenues);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return revenueList;
		}
	 public void updateRevenue(RevenueModel revenue){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps;
	            ps =  connectionDB.con.prepareStatement("update revenue "
	            	  + "set dateR=? ,montant=? ,moyenpaiement=? ,regle=?,description=?,LABEL=? where idR=?");
	            ps.setDate(1, revenue.getDateR());
	            ps.setInt(2,  revenue.getMontant());
	            ps.setString(3,  revenue.getMoyenPaiement());
	            ps.setString(4,  revenue.getRegle());
	            ps.setString(5,  revenue.getDescription());
	            ps.setString(6,  revenue.getLabel());
	            ps.setInt(7, revenue.getIdR());

	            ps.execute();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deleteRevenue(RevenueModel revenue){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from revenue where idR='"+revenue.getIdR()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
		 
		 public  Vector<RevenueModel>  showRevenuesRapportAnnuel(int annuel) {
		        connectionDB.connex();
		        Vector<RevenueModel> V= new Vector();
			 	ResultSet res = null;
		    	String query = "SELECT * FROM revenue where dateR like '%/"+annuel+"%'";
		    	try {
					res = connectionDB.stat.executeQuery(query);
					while (res.next()) {
						V.add(new RevenueModel(res.getInt("idR"),res.getDate("dateR"),res.getInt("montant")
								,res.getString("moyenpaiement"),res.getString("regle"),res.getString("description"),res.getInt("etat"),res.getString("label")));
									
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return V;
			}
		 
		 public  int  totalRevenuesRapportAnnuel(int annuel) {
		        connectionDB.connex();
			 	ResultSet res = null;
			 	int a = 0;
		    	String query = "select sum(montant) as total from revenue where dateR like '%/"+annuel+"%'";
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
		 
		 public  Vector<RevenueModel>  showRevenuesRapportMensuel(int mensuel,int annuel) {
		        connectionDB.connex();
		        Vector<RevenueModel> V= new Vector();
			 	ResultSet res = null;
		    	String query = "SELECT * FROM revenue where dateR like '%"+mensuel+"/"+annuel+"'";
		    	try {
					res = connectionDB.stat.executeQuery(query);
					while (res.next()) {
						V.add(new RevenueModel(res.getInt("idR"),res.getDate("dateR"),res.getInt("montant")
								,res.getString("moyenpaiement"),res.getString("regle"),res.getString("description"),res.getInt("etat"),res.getString("label")));
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return V;
			}
		 
		 public  int  totalRevenuesRapportMensuel(int mensuel,int annuel) {
		        connectionDB.connex();
			 	ResultSet res = null;
			 	int a = 0;
		    	String query = "select sum(montant) as total from revenue where dateR like '%"+mensuel+"/"+annuel+"%'";
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
		    	String query = "SELECT sum(montant) as m  FROM revenue WHERE dateR like '%/"+y+"%'";
		    	try {
		            res = connectionDB.stat.executeQuery(query);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				return res;
			}
}
