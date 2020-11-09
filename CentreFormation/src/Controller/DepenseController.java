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
import Model.DepenseModel;
import Model.RevenueModel;
import Model.SessionModel;
import Service.DepenseService;
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

public class DepenseController implements Initializable{
	DepenseService dp=new DepenseService();
    DepenseModel d=new DepenseModel();
	@FXML
    private Button ajouter,modifier,supprimer;

    @FXML
    private DatePicker dateC;

    @FXML
    private TextField regle, searchBox,montant,label;
    @FXML
    ComboBox<String> com;
    @FXML
    private TableView<DepenseModel> tableview;
    @FXML
    private TableColumn<DepenseModel, Integer> idCol,montantCol;

    @FXML
    private TableColumn<DepenseModel, Date> dateCol;

    @FXML
    private TableColumn<DepenseModel, String> labelCol,moyenCol,regleCol,descCol;
    @FXML
    private Label montantErr, descriptionErr,dateErr,comErr, regleErr,idD,labelErr;

    @FXML
    private TextArea description;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	ajouter.setDisable(false);
     	modifier.setDisable(true);
     	supprimer.setDisable(true);	
        com.getItems().addAll("Espèces", "Chèque","Carte de crédit");
		showDepenseList();
   	    dateC.setValue(LocalDate.now());
		tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		    if(tableview.getSelectionModel().getSelectedItem() != null) 
		    { 	ajouter.setDisable(true);
	    		modifier.setDisable(false);
	    		supprimer.setDisable(false);  
		    	DepenseModel dd = tableview.getSelectionModel().getSelectedItem();
	  	    	com.setValue(dd.getMoyenPaiement());
	  	    	idD.setText(Integer.toString(dd.getIdD()));
		    	montant.setText(Integer.toString(dd.getMontant()));
		    	description.setText(dd.getDescription());
                Date date =dd.getDateD();  
                LocalDate l=date.toLocalDate();
                dateC.setValue(l);
		    	regle.setText(dd.getRegle());
		    	label.setText(dd.getLabel());

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
    	
    	if(!com.getSelectionModel().isEmpty() && !label.getText().isEmpty() && !description.getText().isEmpty() &&  y<=year  && Integer.parseInt(montant.getText())>0 ) {
    			       		
			d.setMoyenPaiement(com.getValue());
		    d.setRegle(regle.getText());
		    d.setDescription(description.getText());
		    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateC.getValue());
		    d.setDateD(gettedDatePickerDate);
			d.setMontant(Integer.parseInt(montant.getText()));
		    d.setLabel(label.getText());

			dp.insertDepense(d);
			
			showDepenseList();
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
		    label.setText("");

  		 regle.setText("");
  		montant.setText("");
   	    dateC.setValue(LocalDate.now());
  		description.setText("");
  		descriptionErr.setText("");
  		 dateErr.setText("");
  		 montantErr.setText("");
  		 comErr.setText("");
      	idD.setText("");
		tableview.getSelectionModel().clearSelection();
		regleErr.setText("");
		labelErr.setText("");
		ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
	
    }
    
    public void showDepenseList(){
    	ObservableList<DepenseModel> list = dp.showDepenses();
    	idCol.setCellValueFactory(new PropertyValueFactory<>("idD"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dateD"));
		montantCol.setCellValueFactory(new PropertyValueFactory<>("montant"));
		moyenCol.setCellValueFactory(new PropertyValueFactory<>("moyenPaiement"));
		regleCol.setCellValueFactory(new PropertyValueFactory<>("regle"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		labelCol.setCellValueFactory(new PropertyValueFactory<>("label"));

		tableview.setItems(list);
    } 
    
    @FXML
    void btnModifierD(ActionEvent event) {
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
        	
        	if(!com.getSelectionModel().isEmpty()  && !label.getText().isEmpty() && !regle.getText().isEmpty() && !description.getText().isEmpty() &&  y<=year  && Integer.parseInt(montant.getText())>0 ) {
        			       		
    			d.setMoyenPaiement(com.getValue());
    		    d.setRegle(regle.getText());
    		    d.setDescription(description.getText());
    		    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateC.getValue());
    		    d.setDateD(gettedDatePickerDate);
    			d.setMontant(Integer.parseInt(montant.getText()));
    		    d.setLabel(label.getText());

        		d.setIdD(Integer.parseInt(idD.getText()));

        		dp.updateDepense(d);
        		showDepenseList();
        		btnAnnuler(event);  
    		}
        	else {
        		if(com.getSelectionModel().isEmpty()) {
    	  	       	 comErr.setText("Le moyen de paiement est obligatoire");
    	  	   		}    
    	  		else {
    	  				comErr.setText("");
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
    	    	if(label.getText().isEmpty()) {
      	  	       	 labelErr.setText("Le label est obligatoire");
      	  	   		}    
      	  		else {
      	  			labelErr.setText("");
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
	        alert.setContentText("Vous choisissez une depense à modifier");
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
			alert.setContentText("Voulez-vous supprimer cette depense");
			Optional<ButtonType> result = alert.showAndWait();
			 if(result.get() == ButtonType.OK) {
				d.setIdD(Integer.parseInt(idD.getText()));
		    	dp.deleteDepense(d);
				btnAnnuler(event);
	        	showDepenseList();
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
    	  ObservableList<DepenseModel> list = dp.showDepenses();
          FilteredList<DepenseModel> filterData = new FilteredList<>(list, p -> true);
          searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
              filterData.setPredicate(dd -> {

                  if (newvalue == null || newvalue.isEmpty()) {
                      return true;
                  }
                  String typedText = newvalue.toLowerCase();
                  if (dd.getDescription().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  if (Integer.toString(dd.getMontant()).toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  if (dd.getMoyenPaiement().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  if (dd.getDateD().toString().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  
                  if (dd.getRegle().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  return false;
              });
              SortedList<DepenseModel> sortedList = new SortedList<>(filterData);
              sortedList.comparatorProperty().bind(tableview.comparatorProperty());
              tableview.setItems(sortedList);
                         
              
          });
    }

}
