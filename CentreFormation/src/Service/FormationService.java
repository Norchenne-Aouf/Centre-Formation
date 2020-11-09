package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.FormationModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormationService {
	
	Connexion connectionDB =new Connexion();
	
	public void insertFormation(FormationModel formation){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            ps =  connectionDB.con.prepareStatement("insert into FORMATION(nom,DOMAINE,type,motcle,description) values(?,?,?,?,?)");
            ps.setString(1, formation.getNomF());
            ps.setString(2, formation.getDomaineF());
            ps.setString(3,formation.getTypeF());
            ps.setString(4, formation.getMotCleF());
            ps.setString(5, formation.getDescF());
            ps.execute();
        } catch (Exception e) {
              System.out.println(e);
        }
    }
	
	 public ObservableList<FormationModel> showFormations() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query = "SELECT * FROM formation order by idF asc";
	    	ObservableList<FormationModel> FormationList = FXCollections.observableArrayList();
	    	try {
	            res = connectionDB.stat.executeQuery(query);
				FormationModel formations;
				while(res.next()) {
					formations = new FormationModel(res.getInt("idF"),res.getString("nom")
							,res.getString("domaine"), res.getString("type"),res.getString("motcle"),res.getString("description"));
					FormationList.add(formations);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return FormationList;
		}
	 public void updateFormation(FormationModel formation){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps;
	            ps =  connectionDB.con.prepareStatement("update FORMATION "
	            	  + "set nom=? ,DOMAINE=? ,type=?,motcle=?,description=? where idF=?");
	            ps.setString(1, formation.getNomF());
	            ps.setString(2, formation.getDomaineF());
	            ps.setString(3,formation.getTypeF());
	            ps.setString(4, formation.getMotCleF());
	            ps.setString(5, formation.getDescF());
	            ps.setInt(6, formation.getIdF());

	            ps.execute();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deleteFormation(FormationModel formation){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from formation where idF='"+formation.getIdF()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
		 public FormationModel recherche(FormationModel formation) {
		        String sql = "SELECT nom FROM formation WHERE idF=?";
		        FormationModel frm = new FormationModel();
		        try {
		            connectionDB.connex();
		            PreparedStatement ps;
		            ps =  connectionDB.con.prepareStatement(sql);
		            ps.setInt(1, formation.getIdF());
		            ResultSet res = ps.executeQuery();
		            if (res.next()) {
		                formation.setNomF(res.getString("nom"));
		                frm = formation;
		            }
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        return frm;
		    }
	 
		
	
}
