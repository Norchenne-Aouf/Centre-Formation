package Controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.ClientModel;
import Model.RevenueModel;
import Model.SessionModel;
import Service.RevenueService;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class RevenueController implements Initializable{
	RevenueService rv=new RevenueService();
    RevenueModel r=new RevenueModel();
	@FXML
    private Button ajouter,modifier,supprimer;

    @FXML
    private DatePicker dateC;

    @FXML
    private TextField regle, searchBox,montant,label;
    @FXML
    ComboBox<String> com;
    @FXML
    private TableView<RevenueModel> tableview;
    @FXML
    private TableColumn<RevenueModel, Integer> idCol,montantCol;

    @FXML
    private TableColumn<RevenueModel, Date> dateCol;

    @FXML
    private TableColumn<RevenueModel, String> moyenCol,regleCol,descCol,labelCol;
    @FXML
    private Label montantErr, descriptionErr,dateErr,comErr, regleErr,idR,etat,labelErr;

    @FXML
    private TextArea description;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	ajouter.setDisable(false);
     	modifier.setDisable(true);
     	supprimer.setDisable(true);	
        com.getItems().addAll("Espèces", "Chèque","Carte de crédit");
		showRevenueList();
   	    dateC.setValue(LocalDate.now());

		tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		    if(tableview.getSelectionModel().getSelectedItem() != null) 
		    { 	 
		    	RevenueModel rr = tableview.getSelectionModel().getSelectedItem();
	  	    	if(rr.getLabel().equals("Inscription")) {
	  	    		ajouter.setDisable(true);
	  	    		modifier.setDisable(true);
	  	    		supprimer.setDisable(false); 
	  	    	}
	  	    	else {
	  	    		ajouter.setDisable(true);
		    		modifier.setDisable(false);
		    		supprimer.setDisable(false); 
	  	    	}
		    	com.setValue(rr.getMoyenPaiement());
	  	    	idR.setText(Integer.toString(rr.getIdR()));
	  	    	etat.setText(Integer.toString(rr.getEtat()));
		    	montant.setText(Integer.toString(rr.getMontant()));
		    	description.setText(rr.getDescription());
                Date date =rr.getDateR();  
                LocalDate l=date.toLocalDate();
                dateC.setValue(l);
		    	regle.setText(rr.getRegle());
		    	label.setText(rr.getLabel());
		    	
		    
		    }
		    }
		    });	

    }
    
    @FXML
    void btnAjoutC(ActionEvent event) {
    	int year = Calendar.getInstance().get(Calendar.YEAR);
    	int y= Integer.parseInt(dateC.getValue().toString().substring(0,4));
    	try{
    	    int entier = Integer.parseInt(montant.getText());
    	    montantErr.setText("");       		
    		if(entier>0) {
    	    	montantErr.setText("");        		
    	    }    	 
    		else {
    			montantErr.setText("Le montant doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(montant.getText().equals("")) {
    			 montantErr.setText("Le montant est obligatoire");
          	}else
          		montantErr.setText("Le montant est un nombre");
    		}
    	
    	if(!com.getSelectionModel().isEmpty() && !label.getText().isEmpty() && !regle.getText().isEmpty() && !description.getText().isEmpty() &&  y<=year  && Integer.parseInt(montant.getText())>0 ) {
    		r.setEtat(1);	       		
			r.setMoyenPaiement(com.getValue());
		    r.setRegle(regle.getText());
		    r.setDescription(description.getText());
		    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateC.getValue());
		    r.setDateR(gettedDatePickerDate);
			r.setMontant(Integer.parseInt(montant.getText()));
		    r.setLabel(label.getText());
			rv.insertRevenue(r);
			showRevenueList();
    		btnAnnuler(event);  
		}
    	else {
    		if(com.getSelectionModel().isEmpty()) {
	  	       	 comErr.setText("Le moyen de paiement est obligatoire");
	  	   		}    
	  		else {
	  				comErr.setText("");
	  			}
    		if(label.getText().isEmpty()) {
	  	       	 labelErr.setText("Le label est obligatoire");
	  	   		}    
	  		else {
	  			labelErr.setText("");
	  			}
    	if(regle.getText().isEmpty()) {
  	       	 regleErr.setText("La régle à est obligatoire");
  	   		}    
  			else {
  				regleErr.setText("");
  			}
	    	if(description.getText().isEmpty()) {
	    		descriptionErr.setText("La description est obligatoire");
	   	   		}    
	   			else {
	   				descriptionErr.setText("");

	   			}
	    	
		   	 if( (((TextField)dateC.getEditor()).getText().isEmpty())) {
		    		dateErr.setText("La date est obligatoire");
		    	}
		    	else {
		    		if(y>year) {		   
			    		dateErr.setText("Vérifier la date ");
			    	}
			    	else
		    		dateErr.setText("");
				}	
		    	
	    }
    }
    
    
    @FXML
    void btnAnnuler(ActionEvent event) {
		 com.getSelectionModel().clearSelection();
		
	  		regle.setText("");
	    label.setText("");
  		montant.setText("");
   	    dateC.setValue(LocalDate.now());
  		description.setText("");
  		descriptionErr.setText("");
  		dateErr.setText("");
  		montantErr.setText("");
  		comErr.setText("");
      	idR.setText("");
		tableview.getSelectionModel().clearSelection();
		regleErr.setText("");
		ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
		labelErr.setText("");

    }
    
    public void showRevenueList(){
    	ObservableList<RevenueModel> list = rv.showRevenus();
    	idCol.setCellValueFactory(new PropertyValueFactory<>("idR"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dateR"));
		montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
		moyenCol.setCellValueFactory(new PropertyValueFactory<>("moyenPaiement"));
		regleCol.setCellValueFactory(new PropertyValueFactory<>("regle"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		labelCol.setCellValueFactory(new PropertyValueFactory<>("label"));
		tableview.setItems(list);
    } 
    
    @FXML
    void btnModifierR(ActionEvent event) {
    	if(tableview.getSelectionModel().getSelectedItem() != null) 
	    {    
    		int year = Calendar.getInstance().get(Calendar.YEAR);
        	int y= Integer.parseInt(((TextField)dateC.getEditor()).getText().substring(6,10));
        	try{
        	    int entier = Integer.parseInt(montant.getText());
        	    montantErr.setText("");       		
        		if(entier>0) {
        	    	montantErr.setText("");        		
        	    }    	 
        		else {
        			montantErr.setText("Le montant doit etre positif et différent de zéro");  
        		}
        	}catch(Exception parEx){
        		 if(montant.getText().equals("")) {
        			 montantErr.setText("Le montant est obligatoire");
              	}else
              		montantErr.setText("Le montant est un nombre");
        		}
        	
        	if(!com.getSelectionModel().isEmpty() && !label.getText().isEmpty() && !regle.getText().isEmpty() && !description.getText().isEmpty() &&  y<=year  && Integer.parseInt(montant.getText())>0 ) {
        			       		
    			r.setMoyenPaiement(com.getValue());
    		    r.setRegle(regle.getText());
    		    r.setDescription(description.getText());
    		    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateC.getValue());
    		    r.setDateR(gettedDatePickerDate);
    			r.setMontant(Integer.parseInt(montant.getText()));
    		    r.setLabel(label.getText());

        		r.setIdR(Integer.parseInt(idR.getText()));

        		rv.updateRevenue(r);
        		showRevenueList();
        		btnAnnuler(event);  
    		}
        	else {
        		if(com.getSelectionModel().isEmpty()) {
    	  	       	 comErr.setText("Le moyen de paiement est obligatoire");
    	  	   		}    
    	  		else {
    	  				comErr.setText("");
    	  			}
        		if(label.getText().isEmpty()) {
   	  	       	 labelErr.setText("Le label est obligatoire");
   	  	   		}    
   	  		else {
   	  			labelErr.setText("");
   	  			}
        	if(regle.getText().isEmpty()) {
      	       	 regleErr.setText("La régle à est obligatoire");
      	   		}    
      			else {
      				regleErr.setText("");
      			}
    	    	if(description.getText().isEmpty()) {
    	    		descriptionErr.setText("La description est obligatoire");
    	   	   		}    
    	   			else {
    	   				descriptionErr.setText("");

    	   			}
    	    	
    		   	 if( (((TextField)dateC.getEditor()).getText().isEmpty())) {
    		    		dateErr.setText("La date est obligatoire");
    		    	}
    		    	else {
    		    		if(y>year) {		   
    			    		dateErr.setText("Vérifier la date ");
    			    	}
    			    	else
    		    		dateErr.setText("");
    				}	
    		    	
        	}}
        			       	
		else
	    {
	    	Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Avertissement");
	        alert.setHeaderText(null);
	        alert.setContentText("Vous choisissez une revenue à modifier");
	        alert.showAndWait();
	    }
    }

    @FXML
    void deleteRevenue(ActionEvent event) {
    	if(tableview.getSelectionModel().getSelectedItem() != null) 
	    {    
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setResizable(false);
			alert.setContentText("Voulez-vous supprimer cette revenue");
			Optional<ButtonType> result = alert.showAndWait();
			 if(result.get() == ButtonType.OK) {
				r.setIdR(Integer.parseInt(idR.getText()));
		    	rv.deleteRevenue(r);
				btnAnnuler(event);
	        	showRevenueList();
			}	
			
		}
		else
	    {
	    	Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Avertissement");
	        alert.setHeaderText(null);
	        alert.setContentText("Vous choisissez une revenue à supprimer");
	        alert.showAndWait();
			
			
	    }
    }

    @FXML
    void filter(KeyEvent event) {
    	  ObservableList<RevenueModel> list = rv.showRevenus();
          FilteredList<RevenueModel> filterData = new FilteredList<>(list, p -> true);
          searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
              filterData.setPredicate(rrv -> {

                  if (newvalue == null || newvalue.isEmpty()) {
                      return true;
                  }
                  String typedText = newvalue.toLowerCase();
                  
                  if (Integer.toString(rrv.getMontant()).toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                 
                  if (rrv.getDateR().toString().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  
                  if (rrv.getRegle().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  return false;
              });
              SortedList<RevenueModel> sortedList = new SortedList<>(filterData);
              sortedList.comparatorProperty().bind(tableview.comparatorProperty());
              tableview.setItems(sortedList);
                         
              
          });
    }

}
