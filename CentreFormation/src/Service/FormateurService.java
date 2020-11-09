package Service;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.FormateurModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FormateurService {
Connexion connectionDB =new Connexion();
	
	public void insertFormateur(FormateurModel formateur){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            ps =  connectionDB.con.prepareStatement("insert into Formateur(nom,prenom,dateD,sexe,domaine,email,numTlf) values(?,?,?,?,?,?,?)");
            ps.setString(1, formateur.getNom());
            ps.setString(2,  formateur.getPrenom());
            ps.setDate(3,  formateur.getDateD());
            ps.setString(4,  formateur.getSexe());
            ps.setString(5,  formateur.getDomaine());
            ps.setString(6,  formateur.getEmail());
            ps.setInt(7,  formateur.getNumTlf());
            ps.execute();
        } catch (Exception e) {
              System.out.println(e);
        }
    }
	
	 public ObservableList<FormateurModel> showFormateurs() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query = "SELECT * FROM formateur order by idFo asc";
	    	ObservableList<FormateurModel> FormateurList = FXCollections.observableArrayList();
	    	try {
	            res = connectionDB.stat.executeQuery(query);
				FormateurModel formateurs;
				while(res.next()) {
					formateurs = new FormateurModel(res.getInt("idFo"),res.getString("nom")
							,res.getString("prenom"),res.getDate("dated"),res.getString("sexe"),res.getString("domaine"),res.getString("email"), res.getInt("numTlf"));
					FormateurList.add(formateurs);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return FormateurList;
		}
	 public void updateFormateur(FormateurModel formateur){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps;
	            ps =  connectionDB.con.prepareStatement("update FORMATEUR "
	            	  + "set nom=? ,prenom=? ,dated=? ,sexe=?,domaine=?,email=?,numTlf=? where idFo=?");
	            ps.setString(1, formateur.getNom());
	            ps.setString(2, formateur.getPrenom());
	            ps.setDate(3,formateur.getDateD());
	            ps.setString(4, formateur.getSexe());
	            ps.setString(5, formateur.getDomaine());
	            ps.setString(6, formateur.getEmail());
	            ps.setInt(7, formateur.getNumTlf());
	            ps.setInt(8, formateur.getIdFo());

	            ps.execute();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deleteFormateur(FormateurModel formateur){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from formateur where idFo='"+formateur.getIdFo()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
		 
		 public FormateurModel recherche(FormateurModel formateur) {
		        String sql = "SELECT nom, prenom FROM formateur WHERE idFo=?";
		        FormateurModel formateu = new FormateurModel();
		        try {
		            connectionDB.connex();
		            PreparedStatement ps;
		            ps =  connectionDB.con.prepareStatement(sql);
		            ps.setInt(1, formateur.getIdFo());
		            ResultSet res = ps.executeQuery();
		            if (res.next()) {
		                formateur.setNom(res.getString("nom"));
		                formateur.setPrenom(res.getString("prenom"));
		                formateu = formateur;
		            }
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        return formateu;
		    }
}
