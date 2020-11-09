package Function;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class navigation {
	
    private final String login="/view/Login.fxml";
    private final String homeAdmin="/view/HomeAdmin.fxml";
    private final String homeSec="/view/HomeSec.fxml";
    private final String homeFinan="/view/HomeFinan.fxml";
    private final String formation="/view/formation.fxml";
    private final String formateur="/view/formateur.fxml";
    private final String dashboard="/view/dashboard.fxml";
    private final String client="/view/client.fxml";
    private final String session="/view/session.fxml";
    private final String salle="/view/salle.fxml";
    private final String inscription="/view/inscription.fxml";
    private final String revenue="/view/revenue.fxml";
    private final String depense="/view/depense.fxml";
    private final String rapport="/view/rapport.fxml";
    private final String statistique="/view/statistique.fxml";

    public Image applicationIcon = new Image(getClass().getResourceAsStream("/img/graduation.png"));

    public String getLogin(){
        return login;
    }

    public String getHomeAdmin(){
        return homeAdmin;
    }
    public String getHomeSec(){
        return homeSec;
    }
    public String getHomeFinan(){
        return homeFinan;
    }
    public String getDashboard(){
        return dashboard;
    }
    public String getFormation(){
        return formation;
    }
    public String getFormateur(){
        return formateur;
    }
    public String getClient(){
        return client;
    } 
    public String getSession(){
        return session;
    }
    public String getSalle(){
        return salle;
    }
    public String getInscription(){
        return inscription;
    }
    public String getRevenue(){
        return revenue;
    }
    public String getDepense(){
        return depense;
    }
    public String getRapport(){
        return rapport;
    }
    public String getStatistique(){
        return statistique;
    }
    
    //alert
    public void showAlert(AlertType type, String title, String header, String text){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
