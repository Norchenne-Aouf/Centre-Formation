package Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import Model.ClientModel;
import Model.FormationModel;
import dbConnexion.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientService {
Connexion connectionDB =new Connexion();
	
	public void insertClient(ClientModel client){
        try {
            connectionDB.connex();
            PreparedStatement ps;
            ps =  connectionDB.con.prepareStatement("insert into Client(nomC,prenomC,dateNaiss,ville,metier,sexe,numtlf) values(?,?,?,?,?,?,?)");
            ps.setString(1, client.getNomC());
            ps.setString(2,  client.getPrenomC());
            ps.setDate(3,  client.getDateNaiss());
            ps.setString(4,  client.getVille());
            ps.setString(5,  client.getMetier());
            ps.setString(6,  client.getSexe());
            ps.setInt(7,  client.getNumTlf());
            ps.execute();
        } catch (Exception e) {
              System.out.println(e);
        }
    }
	
	 public ObservableList<ClientModel> showClients() {
		 	ResultSet res;
	        connectionDB.connex();
	    	String query = "SELECT * FROM client order by idC asc";
	    	ObservableList<ClientModel> ClientList = FXCollections.observableArrayList();
	    	try {
	            res = connectionDB.stat.executeQuery(query);
				ClientModel clients;
				while(res.next()) {
					clients = new ClientModel(res.getInt("idC"),res.getString("nomC")
							,res.getString("prenomC"),res.getDate("dateNaiss"),res.getString("ville"),res.getString("metier"),res.getString("sexe"), res.getInt("numTlf"));
					ClientList.add(clients);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ClientList;
		}
	 
	 
	 public ResultSet showClientsMetier() {
		 	ResultSet res = null;
	        connectionDB.connex();
	    	String query = "select count (*) as nbre, metier from  client group by metier";
	    	try {
	            res = connectionDB.stat.executeQuery(query);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			return res;
		}
	 
	 public int showClientsTOTAL() {
		 	ResultSet res = null;
		 	int a = 0;
	        connectionDB.connex();
	    	String query = "select count(*) as nbre from  client ";
	    	try {
	            res = connectionDB.stat.executeQuery(query);
	            while(res.next()) {
	            	a=res.getInt("nbre");
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
			return a;
		}
	 
	
	 public void updateClient(ClientModel client){
	        try {
	        	connectionDB.connex();
	            PreparedStatement ps;
	            ps =  connectionDB.con.prepareStatement("update Client "
	            	  + "set nomC=? ,prenomC=? ,dateNaiss=? ,ville=?,metier=?,sexe=?,numTlf=? where idC=?");
	            ps.setString(1, client.getNomC());
	            ps.setString(2, client.getPrenomC());
	            ps.setDate(3,client.getDateNaiss());
	            ps.setString(4, client.getVille());
	            ps.setString(5, client.getMetier());
	            ps.setString(6, client.getSexe());
	            ps.setInt(7, client.getNumTlf());
	            ps.setInt(8, client.getIdC());

	            ps.execute();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    }
		
		 public void deleteClient(ClientModel client){
		        try {
		        	Connexion connectionDB =new Connexion();
		            connectionDB.connex();
		            connectionDB.stat.executeUpdate("delete from client where idC='"+client.getIdC()+"'");
		        } catch (Exception e) {
		            System.out.println(e);
		        }
		        
		    }
}
