package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.SalleModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalleService {
Connexion connectionDB =new Connexion();
	
	public void insertSalle(SalleModel salle){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            ps =  connectionDB.con.prepareStatement("insert into salle(nomSa,type1,nbreSa,nbreEtage,capacite,projection) values(?,?,?,?,?,?)");
            ps.setString(1, salle.getNomSa());
            ps.setString(2,  salle.getType1());
            ps.setInt(3,  salle.getNbreSalle());
            ps.setInt(4,  salle.getNbreEtage());
            ps.setInt(5,  salle.getCapacite());
            ps.setString(6,  salle.getProjection());
            ps.execute();
        } catch (Exception e) {
              System.out.println(e);
        }
    }
	
	 public ObservableList<SalleModel> showSalles() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query = "SELECT * FROM salle order by idSa asc";
	    	ObservableList<SalleModel> salleList = FXCollections.observableArrayList();
	    	try {
	            res = connectionDB.stat.executeQuery(query);
				SalleModel salles;
				while(res.next()) {
					salles = new SalleModel(res.getInt("idSa"),res.getString("nomSa")
							,res.getString("type1"),res.getInt("nbreSa"),res.getInt("nbreEtage"),res.getInt("capacite"),res.getString("projection"));
					salleList.add(salles);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return salleList;
		}
	 public void updatesalle(SalleModel salle){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps;
	            ps =  connectionDB.con.prepareStatement("update salle "
	            	  + "set nomSa=? ,type1=? ,nbreSa=? ,nbreEtage=?,capacite=?,projection=? where idSa=?");
	            ps.setString(1, salle.getNomSa());
	            ps.setString(2,  salle.getType1());
	            ps.setInt(3,  salle.getNbreSalle());
	            ps.setInt(4,  salle.getNbreEtage());
	            ps.setInt(5,  salle.getCapacite());
	            ps.setString(6,  salle.getProjection());
	            
	            ps.setInt(7,  salle.getIdSa());

	            ps.execute();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deletesalle(SalleModel salle){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from salle where idSa='"+salle.getIdSa()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
		 
		 public SalleModel recherche(SalleModel salle) {
		        String sql = "SELECT * FROM salle WHERE idSa=?";
		        SalleModel sallee = new SalleModel();
		        try {
		            connectionDB.connex();
		            PreparedStatement ps;
		            ps =  connectionDB.con.prepareStatement(sql);
		            ps.setInt(1, salle.getIdSa());
		            ResultSet res = ps.executeQuery();
		            if (res.next()) {
		              salle.setNomSa(res.getString("nomSa"));

		              sallee = salle;
		            }
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        return sallee;
		    }
}