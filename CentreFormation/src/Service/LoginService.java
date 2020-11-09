package Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbConnexion.Connexion;

public class LoginService {

	 public boolean login(String username, String password, String type){
	        try {
	        	Connexion connectionDB =new Connexion();
	        	ResultSet res;
	            connectionDB.connex();
	            Statement stat;
	            stat=connectionDB.con.createStatement();

	            res=stat.executeQuery("select * from utilisateur where username='"+username+"' and password='"+password+"' and type='"+type+"'");

	                    if( res.next()){
	                        return true;
	                       }       
	                } catch (SQLException e) {
	                    System.out.println(e);
	                }
	                return false;
	            }
}
