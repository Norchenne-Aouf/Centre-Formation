package Controller;


import Function.navigation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Service.LoginService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{

    navigation nav = new navigation();

	@FXML
    private TextField username;

    @FXML
    private Button btncnx;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> type;
 
 
	@FXML
    public void initialize(URL location, ResourceBundle resources) {
       type.setItems(FXCollections.observableArrayList("Directeur","Secrétaire","Financier"));
    }
    
    @FXML
    private void Login(ActionEvent event) throws IOException{
    	System.out.println(username.getText());
    	if(username.getText().isEmpty()) {
              nav.showAlert(AlertType.ERROR, "Error", null, "S'il vous plait entrer votre nom d'utilisateur");
              username.requestFocus();
            return;
        }
        if(password.getText().isEmpty()) {
          nav.showAlert(AlertType.ERROR, "Error", null, "S'il vous plait entrer votre mot de passe");
          password.requestFocus();

            return;
        }
        
        if(type.getSelectionModel().isEmpty()) {
            nav.showAlert(AlertType.ERROR, "Error", null, "S'il vous plaît sélectionner votre profession");
            password.requestFocus();

              return;
          }
    	LoginService loginService = new LoginService();
    	boolean flag = loginService.login(username.getText(), password.getText(),type.getValue());

    	if(!flag) {
    		  nav.showAlert(AlertType.ERROR, "Error", null, "S'il vous plait entrer correct nom d'utilisateur et le mot de passe");
    	}else {  
    		
    		Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
	        stage2.hide();
	        FXMLLoader loader = new FXMLLoader();
    		if (type.getValue().equals("Directeur")) {
	            loader.setLocation(getClass().getResource(nav.getHomeAdmin()));
	            
    		}
    		else if (type.getValue().equals("Secrétaire")) {
	            loader.setLocation(getClass().getResource(nav.getHomeSec()));

    		}
    		else if (type.getValue().equals("Financier")) {
	            loader.setLocation(getClass().getResource(nav.getHomeFinan()));

    		}
	            try {
	                loader.load();
	            } catch (Exception e) {
	            }
	            Parent p = loader.getRoot();
	            Stage stage = new Stage();
	            Scene pp = new Scene(p);
	            pp.setFill(javafx.scene.paint.Color.TRANSPARENT);
	            stage.setScene(pp);
	            stage.getIcons().add(nav.applicationIcon);
	            stage.setTitle("Home");
	            stage.show();
	            System.out.println("Login Successful!");
    		

            }
    }
    
    
   

    
}
