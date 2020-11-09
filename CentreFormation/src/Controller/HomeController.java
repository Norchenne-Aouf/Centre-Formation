package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Function.navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController implements Initializable{
	
    navigation nav = new navigation();
	@FXML
    private Pane rootPane;
	@FXML
	private Label a;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		AnchorPane pane;
		try {
			pane = FXMLLoader.load(getClass().getResource(nav.getDashboard()));
			rootPane.getChildren().setAll(pane);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@FXML
    private void btndashboard(ActionEvent event) throws IOException {
    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getDashboard()));
		rootPane.getChildren().setAll(pane);	
    }
	
	 @FXML
	    private void btnFormation(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getFormation()));
			rootPane.getChildren().setAll(pane);	
	    }
	 @FXML
	    private void btnFormateur(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getFormateur()));
			rootPane.getChildren().setAll(pane);	
	    }
	
	 @FXML
	    private void btnClient(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getClient()));
			rootPane.getChildren().setAll(pane);	
	    }
	 @FXML
	    private void btnSession(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getSession()));
			rootPane.getChildren().setAll(pane);	
	 }
	 
	 @FXML
	    private void btnSalle(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getSalle()));
			rootPane.getChildren().setAll(pane);	
	 }
	 
	 @FXML
	    private void btnInscription(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getInscription()));
			rootPane.getChildren().setAll(pane);	
	 }
	 
	 @FXML
	    private void btnDepense(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getDepense()));
			rootPane.getChildren().setAll(pane);	
	 }
	 
	 @FXML
	    private void btnRevenue(ActionEvent event)  throws IOException {
	    	AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getRevenue()));
			rootPane.getChildren().setAll(pane);	
	 }
	 
	 @FXML
	    void btnDeconnexion(ActionEvent event) throws IOException {
	    	Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
	        stage2.hide();
	        FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource(nav.getLogin()));
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
            stage.setTitle("Login");
            stage.show();
	    }
	 @FXML
	 private void btnRapport(ActionEvent event) throws IOException {
		 AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getRapport()));
		 rootPane.getChildren().setAll(pane);	
	    }
	 
	 @FXML
	 private void btnStatistique(ActionEvent event) throws IOException {
		 AnchorPane pane= FXMLLoader.load(getClass().getResource(nav.getStatistique()));
		 rootPane.getChildren().setAll(pane);	
	    }
}
