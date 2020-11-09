package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Function.navigation;
import Model.FormationModel;
import Service.FormationService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class FormationController implements Initializable{

	
	navigation nav=new navigation();
    FormationService frm=new FormationService();
    FormationModel f=new FormationModel();
    @FXML
    private TextArea descF;
    @FXML
    private TextField mcF,domF,nomF,searchBox;
    @FXML
    private ComboBox<String> typeF;
    @FXML
    private Label idF,tarErr,domErr,nomErr;
    @FXML
    private TableView<FormationModel> tableview;
    @FXML
    private TableColumn<FormationModel, Integer> idCol;
    @FXML
    private TableColumn<FormationModel, String> nomCol,typeCol,domCol,mcCol,descCol;
    @FXML
    private Button ajouter,modifier,supprimer;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	showFormationList();
    	ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
        typeF.getItems().addAll("BTS", "BTP");

    	 tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			    //Check whether item is selected and set value of selected item to Label
			    if(tableview.getSelectionModel().getSelectedItem() != null) 
			    {    
			    	FormationModel f = tableview.getSelectionModel().getSelectedItem();

			    	nomF.setText(f.getNomF());
		  	    	domF.setText(f.getDomaineF());
			    	typeF.setValue(f.getTypeF());
		  	    	mcF.setText(f.getMotCleF());
		  	    	descF.setText(f.getDescF());
		  	    	idF.setText(Integer.toString(f.getIdF()));
		  	    	ajouter.setDisable(true);
			    	modifier.setDisable(false);
			    	supprimer.setDisable(false);
			    }
			    }
			    });	
	}    

    public void showFormationList(){
    	ObservableList<FormationModel> list = frm.showFormations();
    	idCol.setCellValueFactory(new PropertyValueFactory<>("idF"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nomF"));
		domCol.setCellValueFactory(new PropertyValueFactory<>("domaineF"));
		typeCol.setCellValueFactory(new PropertyValueFactory<>("typeF"));
		mcCol.setCellValueFactory(new PropertyValueFactory<>("motCleF"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("descF"));
		tableview.setItems(list);
    }
    
    
    @FXML
    void btnAnnuler(ActionEvent event) {
    	nomErr.setText("");
    	nomF.setText("");
    	descF.setText("");
    	domErr.setText("");	    	
	    domF.setText(""); 
		mcF.setText(""); 
		tarErr.setText("");
		typeF.setValue("");
		tableview.getSelectionModel().clearSelection();
	    idF.setText("");
	    ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
     }

    
    @FXML
    private void btnAjoutF(ActionEvent event) throws IOException{
    	
    	
    	if(!nomF.getText().isEmpty() && !domF.getText().isEmpty() &&  !typeF.getSelectionModel().isEmpty() ) {
    		f.setNomF(nomF.getText());
    		f.setDomaineF(domF.getText());
			f.setTypeF(typeF.getValue());
    		f.setMotCleF(mcF.getText());
    		f.setDescF(descF.getText());
    		frm.insertFormation(f);
    		btnAnnuler(event);
        	showFormationList();

    	}else {
    		if(nomF.getText().isEmpty()) {
    	       	 nomErr.setText("Le nom est obligatoire");
    	   	}    
    			else {
    	       	nomErr.setText("");
    			}
    	   	if(domF.getText().isEmpty()) {
    	   		domErr.setText("Le domaine est obligatoire");
    	   	}
    	   	else {
    	       	domErr.setText("");
    			}
    	if(typeF.getSelectionModel().isEmpty()) {
 	       	 tarErr.setText("Le type  est obligatoire");
 	   		}    
 		else {
 			tarErr.setText("");
 			}
    	}
    	}
    	@FXML
        private void btnModifierF(ActionEvent event) throws IOException{        
    		if(tableview.getSelectionModel().getSelectedItem() != null) 
		    {    
	        	
    			if(!nomF.getText().isEmpty() && !domF.getText().isEmpty() &&  !typeF.getSelectionModel().isEmpty() ) {
    	    		f.setNomF(nomF.getText());
    	    		f.setDomaineF(domF.getText());
    				f.setTypeF(typeF.getValue());
	        		f.setMotCleF(mcF.getText());
	        		f.setDescF(descF.getText());
	        		f.setIdF(Integer.parseInt(idF.getText()));
	        		frm.updateFormation(f);
	        		btnAnnuler(event);
	            	showFormationList();
	
	        	}else {
	        		if(nomF.getText().isEmpty()) {
	        	       	 nomErr.setText("Le nom est obligatoire");
	        	   	}    
	        			else {
	        	       	nomErr.setText("");
	        			}
	        	   	if(domF.getText().isEmpty()) {
	        	   		domErr.setText("Le domaine est obligatoire");
	        	   	}
	        	   	else {
	        	       	domErr.setText("");
	        			}
	        	   	if(typeF.getSelectionModel().isEmpty()) {
	        	       	 tarErr.setText("Le type  est obligatoire");
	        	   		}    
	        		else {
	        			tarErr.setText("");
	        			}
	        	}
	        }
			else
		    {
		    	Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("Avertissement");
		        alert.setHeaderText(null);
		        alert.setContentText("Vous choisissez une formation à modifier");
		        alert.showAndWait();
		    }
     }
    	@FXML
    	 public void deleteFormation(ActionEvent event) {
    			if(tableview.getSelectionModel().getSelectedItem() != null) 
    		    {    
    				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    				alert.setTitle("Confirmation");
    				alert.setResizable(false);
    				alert.setContentText("Voulez-vous supprimer cette formation");
    				Optional<ButtonType> result = alert.showAndWait();
    				 if(result.get() == ButtonType.OK) {
    					f.setIdF(Integer.parseInt(idF.getText()));
    				   	frm.deleteFormation(f);
    				   	btnAnnuler(event);
    				   	showFormationList();
                	}
    			}
    			else
    		    {
    		    	Alert alert = new Alert(Alert.AlertType.WARNING);
    		        alert.setTitle("Avertissement");
    		        alert.setHeaderText(null);
    		        alert.setContentText("Vous choisissez une formation à supprimer");
    		        alert.showAndWait();
    		    }
    		}


    	@FXML
        private void filter(KeyEvent ke) {
            ObservableList<FormationModel> list = frm.showFormations();
            FilteredList<FormationModel> filterData = new FilteredList<>(list, p -> true);
            searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
                filterData.setPredicate(forma -> {

                    if (newvalue == null || newvalue.isEmpty()) {
                        return true;
                    }
                    String typedText = newvalue.toLowerCase();
                    if (forma.getDomaineF().toLowerCase().indexOf(typedText) != -1) {

                        return true;
                    }
                    if (forma.getNomF().toLowerCase().indexOf(typedText) != -1) {

                        return true;
                    }
                    if (forma.getTypeF().toLowerCase().indexOf(typedText) != -1) {

                        return true;
                    }
                   
                    return false;
                });
                SortedList<FormationModel> sortedList = new SortedList<>(filterData);
                sortedList.comparatorProperty().bind(tableview.comparatorProperty());
                tableview.setItems(sortedList);
                           
                
            });

        }
}
