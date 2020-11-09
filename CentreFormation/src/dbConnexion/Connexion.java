package dbConnexion;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class Connexion {

	private String DATABASE_URL = "jdbc:oracle:thin:formation/azerty@localhost";
    private String DATABASE_USERNAME = "formation";
    private String DATABASE_PASSWORD = "azerty";
	public Connection con;
	public Statement stat;
	
    public void connex() {

	try {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		  con = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
		 stat= con.createStatement();
	}
	catch(Exception e) {		
		System.out.println(e );
	}
    }

}
