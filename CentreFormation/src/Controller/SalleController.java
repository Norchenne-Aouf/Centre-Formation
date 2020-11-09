package Controller;

import java.net.URL;

import java.util.Optional;
import java.util.ResourceBundle;

import Model.SalleModel;
import Service.SalleService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class SalleController  implements Initializable {
	SalleService sal=new SalleService();
    SalleModel sa=new SalleModel();
	@FXML
    private TextField nomSa, nbreSalle, nbreEtage, capaciteSa, searchBox;
	@FXML
    private RadioButton oui,non;
    @FXML
    private ToggleGroup projection;
    @FXML
    private ComboBox<String> type1; 
    @FXML
    private Label nomErr, nbreSalleErr, nbreEtageErr,capaciteErr, type1Err, type3Err, idSa;
    @FXML
    private TableView<SalleModel> tableview;
    @FXML
    private TableColumn<SalleModel, Integer> idCol,numSalleCol,nbreEtageCol,capCol;
    @FXML
    private TableColumn<SalleModel, String> nomCol, type1Col,projectionCol;
    @FXML
    private Button Ajouter,modifier, supprimer;
    private String project;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        type1.getItems().addAll("Salle de réunion", "Salle de cours","Salle de TD","Salle de TP");
       showSalleList();
        Ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
        tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		    //Check whether item is selected and set value of selected item to Label
		    if(tableview.getSelectionModel().getSelectedItem() != null) 
		    {   
		    	Ajouter.setDisable(true);
		    	modifier.setDisable(false);
		    	supprimer.setDisable(false);
		    	SalleModel saa = tableview.getSelectionModel().getSelectedItem();
	  	    	idSa.setText(Integer.toString(saa.getIdSa()));
	  	    	nomSa.setText(saa.getNomSa());
	  	    	nbreSalle.setText(Integer.toString(saa.getNbreSalle()));
	  	    	nbreEtage.setText(Integer.toString(saa.getNbreEtage()));
	  	    	capaciteSa.setText(Integer.toString(saa.getCapacite()));
		    	type1.setValue(saa.getType1());
		    	if (saa.getProjection().equals("Oui"))oui.setSelected(true);	  	    		
		    	if (saa.getProjection().equals("Non"))non.setSelected(true);	  	    		
		    				    
		    }
		   
		    }
		    });	
	}
	
	 public void showSalleList(){
	    	ObservableList<SalleModel> list = sal.showSalles();
	    	idCol.setCellValueFactory(new PropertyValueFactory<>("idSa"));
			nomCol.setCellValueFactory(new PropertyValueFactory<>("nomSa"));
			numSalleCol.setCellValueFactory(new PropertyValueFactory<>("nbreSalle"));
			nbreEtageCol.setCellValueFactory(new PropertyValueFactory<>("nbreEtage"));
			capCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));
			type1Col.setCellValueFactory(new PropertyValueFactory<>("type1"));
			projectionCol.setCellValueFactory(new PropertyValueFactory<>("projection"));

			tableview.setItems(list);
	    }
	
	
	@FXML
    void btnAjoutSa(ActionEvent event) {
		
		try{
    	    int entier = Integer.parseInt(nbreSalle.getText());
    	    nbreSalleErr.setText("");       		
    		if(entier>0) {
    	    	nbreSalleErr.setText("");        		
    	    }    	 
    		else {
    			nbreSalleErr.setText("Le numéro de salle doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(nbreSalle.getText().equals("")) {
    			 nbreSalleErr.setText("Le numéro de salle est obligatoire");
          	}else
          		nbreSalleErr.setText("Le numéro de salle est un nombre");
    		}
		
		try{
    	    int entier = Integer.parseInt(nbreEtage.getText());
    	    nbreEtageErr.setText("");       		
    		if(entier>0) {
    			nbreEtageErr.setText("");        		
    	    }    	 
    		else {
    			nbreEtageErr.setText("Le numéro d'étage doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(nbreEtage.getText().equals("")) {
    			 nbreEtageErr.setText("Le numéro d'étage est obligatoire");
          	}else
          		nbreEtageErr.setText("Le numéro d'étage est un nombre");
    		}

		try{
    	    int entier = Integer.parseInt(capaciteSa.getText());
    	    capaciteErr.setText("");       		
    		if(entier>0) {
    			capaciteErr.setText("");        		
    	    }    	 
    		else {
    			capaciteErr.setText("La capacité doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(capaciteSa.getText().equals("")) {
    			 capaciteErr.setText("La capacité est obligatoire");
          	}else
          		capaciteErr.setText("La capacité est un nombre");
    		}

    	if(!nomSa.getText().isEmpty() && Integer.parseInt(nbreSalle.getText())>0 && Integer.parseInt(nbreEtage.getText())>0
    			&& Integer.parseInt(capaciteSa.getText())>0 && !type1.getSelectionModel().isEmpty()&& (oui.isSelected() || non.isSelected()))
    	{
    	    sa.setNomSa(nomSa.getText());
	    	sa.setNbreSalle(Integer.parseInt(nbreSalle.getText()));
	    	sa.setNbreEtage(Integer.parseInt(nbreEtage.getText()));
	    	sa.setCapacite(Integer.parseInt(capaciteSa.getText()));
			sa.setType1(type1.getValue());
			if (oui.isSelected()) project="Oui";
			else project="Non";
			sa.setProjection(project);

			sal.insertSalle(sa);
			btnAnnuler(event);
        	showSalleList();
			
    	}
    	else {
    		if(nomSa.getText().isEmpty()) {
     	       	 nomErr.setText("Le nom de la salle est obligatoire");
     	   		}    
     		else {
     	       	nomErr.setText("");
     		}
    		if(type1.getSelectionModel().isEmpty()) {
	  	       	 type1Err.setText("Le type 1 est obligatoire");
	  	   		}    
	  		else {
	  			type1Err.setText("");
	  			}
    		if(oui.isSelected() || non.isSelected()) {
    			type3Err.setText("");
	    	}    
			else {
				type3Err.setText("La projection est obligatoire");
			}
    		
    	}
    }
	
	@FXML
    void btnAnnuler(ActionEvent event) {
		nomSa.setText("");
		nbreSalle.setText("");
		nbreEtage.setText("");
		capaciteSa.setText("");
		oui.setSelected(false);
		non.setSelected(false);
		type1.setValue("");
		tableview.getSelectionModel().clearSelection();
		nomErr.setText("");
		nbreSalleErr.setText("");
		nbreEtageErr.setText("");
		capaciteErr.setText("");
		type3Err.setText("");	
		Ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
    }
	@FXML
    void btnModifierSa(ActionEvent event) {
		if(tableview.getSelectionModel().getSelectedItem() != null) 
	    { 
				try{
		    	    int entier = Integer.parseInt(nbreSalle.getText());
		    	    nbreSalleErr.setText("");       		
		    		if(entier>0) {
		    	    	nbreSalleErr.setText("");        		
		    	    }    	 
		    		else {
		    			nbreSalleErr.setText("Le numéro de salle doit etre positif et différent de zéro");  
		    		}
		    	}catch(Exception parEx){
		    		 if(nbreSalle.getText().equals("")) {
		    			 nbreSalleErr.setText("Le numéro de salle est obligatoire");
		          	}else
		          		nbreSalleErr.setText("Le numéro de salle est un nombre");
		    		}
				
				try{
		    	    int entier = Integer.parseInt(nbreEtage.getText());
		    	    nbreEtageErr.setText("");       		
		    		if(entier>0) {
		    			nbreEtageErr.setText("");        		
		    	    }    	 
		    		else {
		    			nbreEtageErr.setText("Le numéro d'étage doit etre positif et différent de zéro");  
		    		}
		    	}catch(Exception parEx){
		    		 if(nbreEtage.getText().equals("")) {
		    			 nbreEtageErr.setText("Le numéro d'étage est obligatoire");
		          	}else
		          		nbreEtageErr.setText("Le numéro d'étage est un nombre");
		    		}
		
				try{
		    	    int entier = Integer.parseInt(capaciteSa.getText());
		    	    capaciteErr.setText("");       		
		    		if(entier>0) {
		    			capaciteErr.setText("");        		
		    	    }    	 
		    		else {
		    			capaciteErr.setText("La capacité doit etre positif et différent de zéro");  
		    		}
		    	}catch(Exception parEx){
		    		 if(capaciteSa.getText().equals("")) {
		    			 capaciteErr.setText("La capacité est obligatoire");
		          	}else
		          		capaciteErr.setText("La capacité est un nombre");
		    		}
				if(!nomSa.getText().isEmpty() && Integer.parseInt(nbreSalle.getText())>0 && Integer.parseInt(nbreEtage.getText())>0
		    			&& Integer.parseInt(capaciteSa.getText())>0 && !type1.getSelectionModel().isEmpty()&& (oui.isSelected() || non.isSelected()))
		    	{
		    	    sa.setNomSa(nomSa.getText());
			    	sa.setNbreSalle(Integer.parseInt(nbreSalle.getText()));
			    	sa.setNbreEtage(Integer.parseInt(nbreEtage.getText()));
			    	sa.setCapacite(Integer.parseInt(capaciteSa.getText()));
					sa.setType1(type1.getValue());
					if (oui.isSelected()) project="Oui";
					else project="Non";
					sa.setProjection(project);
					
					sa.setIdSa(Integer.parseInt(idSa.getText()));
		
		    		sal.updatesalle(sa);
		    		btnAnnuler(event);
		    		showSalleList();
		    		
					
		    	}
		    	else {
		    		if(nomSa.getText().isEmpty()) {
		     	       	 nomErr.setText("Le nom de la salle est obligatoire");
		     	   		}    
		     		else {
		     	       	nomErr.setText("");
		     		}
		    		if(type1.getSelectionModel().isEmpty()) {
			  	       	 type1Err.setText("Le type 1 est obligatoire");
			  	   		}    
			  		else {
			  			type1Err.setText("");
			  			}
		    		if(oui.isSelected() || non.isSelected()) {
		    			type3Err.setText("");
			    	}    
					else {
						type3Err.setText("La projection est obligatoire");
					}
		    	}
	    }else
	    {
	    	
	    	Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Avertissement");
	        alert.setHeaderText(null);
	        alert.setContentText("Vous choisissez une salle à modifier");
	        alert.showAndWait();
	    }
    }
	
	 @FXML
	    void deleteSalle(ActionEvent event) {
	    	if(tableview.getSelectionModel().getSelectedItem() != null) 
		    {    
	    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setResizable(false);
				alert.setContentText("Voulez-vous supprimer cette salle");
				Optional<ButtonType> result = alert.showAndWait();
				 if(result.get() == ButtonType.OK) {
					sa.setIdSa(Integer.parseInt(idSa.getText()));
					sal.deletesalle(sa);
					btnAnnuler(event);
					showSalleList();
	            	}
			}
			else
		    {
		    	Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("Avertissement");
		        alert.setHeaderText(null);
		        alert.setContentText("Vous choisissez une salle à supprimer");
		        alert.showAndWait();
		    }
	    }
	    
	    @FXML
     private void filter(KeyEvent ke) {
         ObservableList<SalleModel> list = sal.showSalles();
         FilteredList<SalleModel> filterData = new FilteredList<>(list, p -> true);
         searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
             filterData.setPredicate(sall -> {

                 if (newvalue == null || newvalue.isEmpty()) {
                     return true;
                 }
                 String typedText = newvalue.toLowerCase();
                 if (sall.getNomSa().toLowerCase().indexOf(typedText) != -1) {

                     return true;
                 }
                 
                
                 return false;
             });
             SortedList<SalleModel> sortedList = new SortedList<>(filterData);
             sortedList.comparatorProperty().bind(tableview.comparatorProperty());
             tableview.setItems(sortedList);
                        
             
         });

     }
}
