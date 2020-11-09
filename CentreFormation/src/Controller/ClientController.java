package Controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import Model.ClientModel;
import Service.ClientService;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class ClientController implements Initializable {
	ClientService clt=new ClientService();
    ClientModel c=new ClientModel();
	@FXML
    private TextField nomC,prenomC,villeC,metierC,tlfC,searchBox;
    @FXML
    private DatePicker dateC;
    @FXML
    private RadioButton hommeC,femmeC;
    @FXML
    private ToggleGroup sexe;
    @FXML
    private Label idC, tlfErr,nomErr, prenomErr, villeErr, dateErr, metierErr, sexeErr;
    String sexee;
    
    @FXML
    private TableView<ClientModel> tableview;

    @FXML
    private TableColumn<ClientModel, Integer> idCol,tlfCol;

    @FXML
    private TableColumn<ClientModel, String> nomCol,prenomCol, sexeCol,villeCol,metierCol;
    
    @FXML
    private Button ajouter,modifier,supprimer;
    
    @Override
  		public void initialize(URL arg0, ResourceBundle arg1) {
  	     showClientList();
  	   ajouter.setDisable(false);
  	   modifier.setDisable(true);
  	   supprimer.setDisable(true);	
  	 	 tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			    //Check whether item is selected and set value of selected item to Label
			    if(tableview.getSelectionModel().getSelectedItem() != null) 
			    {    
			    	 ajouter.setDisable(true);
				    	modifier.setDisable(false);
				    	supprimer.setDisable(false);
			    	ClientModel c = tableview.getSelectionModel().getSelectedItem();
		  	    	idC.setText(Integer.toString(c.getIdC()));
			    	nomC.setText(c.getNomC());
			    	prenomC.setText(c.getPrenomC());
			    	Date date =c.getDateNaiss();  
	                LocalDate l=date.toLocalDate();
	                dateC.setValue(l);
			    	if (c.getSexe().equals("Femme"))femmeC.setSelected(true);	  	    		
			    	if (c.getSexe().equals("Homme"))hommeC.setSelected(true);	  	    		
			    	metierC.setText(c.getMetier());
			    	villeC.setText(c.getVille());
			    	tlfC.setText(Integer.toString(c.getNumTlf()));
			    				    	
			    }
			    }
			    });	
		  	 	int year = Calendar.getInstance().get(Calendar.YEAR);
		   	    dateC.setValue(LocalDate.of(year-18, 1, 1));
		   	  
		    	
  		}    
    
    public void showClientList(){
    	ObservableList<ClientModel> list = clt.showClients();
    	idCol.setCellValueFactory(new PropertyValueFactory<>("idC"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nomC"));
		prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenomC"));
		sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));
		villeCol.setCellValueFactory(new PropertyValueFactory<>("ville"));
		metierCol.setCellValueFactory(new PropertyValueFactory<>("metier"));
		tlfCol.setCellValueFactory(new PropertyValueFactory<>("numTlf"));
		
		tableview.setItems(list);
    } 
    @FXML
    void btnAjoutC(ActionEvent event) {
    	int year = Calendar.getInstance().get(Calendar.YEAR);
    	int y= Integer.parseInt(dateC.getValue().toString().substring(0,4));
    	try{
    	    int entier = Integer.parseInt(tlfC.getText());
    	    tlfErr.setText("");       		
    		if(entier>0) {
    	    	System.out.println("Est un numero positif!");
    	    	tlfErr.setText("");        		
    	    }    	 
    		else {
    			System.out.println("changez le numero !");
    			tlfErr.setText("Le numéro de téléphone doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(tlfC.getText().equals("")) {
    			 tlfErr.setText("Le numéro de téléphone est obligatoire");
          	}else
          		tlfErr.setText("Le numéro de téléphone est un nombre");
    		}
    	
    	if(!nomC.getText().isEmpty() && !prenomC.getText().isEmpty() &&  y<=(year-18) && (femmeC.isSelected() || hommeC.isSelected())&& !metierC.getText().isEmpty()
    		 && !villeC.getText().isEmpty() && Integer.parseInt(tlfC.getText())>0 ) {
    			       		

		    	if (femmeC.isSelected()) sexee="Femme";
				else sexee="Homme";
		    	c.setNomC(nomC.getText());
		    	c.setPrenomC(prenomC.getText());
		    	java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateC.getValue());
				c.setDateNaiss(gettedDatePickerDate);
				c.setSexe(sexee);
		    	c.setMetier(metierC.getText());
				c.setVille(villeC.getText());
				c.setNumTlf(Integer.parseInt(tlfC.getText()));
				clt.insertClient(c);
			    showClientList();
	    		btnAnnuler(event);

			   }
    	else {
    	if(nomC.getText().isEmpty()) {
  	       	 nomErr.setText("Le nom est obligatoire");
  	   		}    
  			else {
  	       	nomErr.setText("");
  			}
	    	if(prenomC.getText().isEmpty()) {
	   	       	 prenomErr.setText("Le prenom est obligatoire");
	   	   		}    
	   			else {
		   	       	prenomErr.setText("");

	   			}
	    	if(villeC.getText().isEmpty()) {
	   	       	 villeErr.setText("La ville est obligatoire");
	   	   		}    
	   			else {	   			  			
	   				villeErr.setText("");  			
	   			}
	  	   	if(metierC.getText().isEmpty()) {
	  	   		metierErr.setText("La metier est obligatoire");
	  	   	}
	  	   	else {
	  	     	metierErr.setText("");
	  			}
		   	 if( (((TextField)dateC.getEditor()).getText().isEmpty())) {
		    		dateErr.setText("La date est obligatoire");
		    	}
		    	else {
		    		if(y>(year-18)) {		   
			    		dateErr.setText("Vérifier la date de naissance");
			    	}
			    	else
		    		dateErr.setText("");
				}	
		    	if(femmeC.isSelected() || hommeC.isSelected()) {
		    		sexeErr.setText("");
		    	}    
				else {
					sexeErr.setText("Le sexe est obligatoire");
				}
	    }

    }
    
    @FXML
    void btnAnnuler(ActionEvent event) {
    	nomC.setText("");
    	prenomC.setText("");
	    ((TextField)dateC.getEditor()).setText("");
	    femmeC.setSelected(false);
	    hommeC.setSelected(false);
    	metierC.setText("");
    	villeC.setText("");
    	tlfC.setText("");
		tableview.getSelectionModel().clearSelection();
		tlfErr.setText("");
		nomErr.setText("");
		prenomErr.setText("");
		villeErr.setText("");
		dateErr.setText("");
		metierErr.setText("");
		sexeErr.setText("");
		ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
    	
    }
    @FXML
    void btnModifierC(ActionEvent event) {
    	if(tableview.getSelectionModel().getSelectedItem() != null) 
	    {    
    		int year = Calendar.getInstance().get(Calendar.YEAR);
        	int y= Integer.parseInt(((TextField)dateC.getEditor()).getText().substring(6,10));
        	try{
        	    int entier = Integer.parseInt(tlfC.getText());
        	    tlfErr.setText("");       		
        		if(entier>0) {
        	    	System.out.println("Est un numero positif!");
        	    	tlfErr.setText("");        		
        	    }    	 
        		else {
        			System.out.println("changez le numero !");
        			tlfErr.setText("Le numéro de téléphone doit etre positif et différent de zéro");  
        		}
        	}catch(Exception parEx){
        		 if(tlfC.getText().equals("")) {
        			 tlfErr.setText("Le numéro de téléphone est obligatoire");
              	}else
              		tlfErr.setText("Le numéro de téléphone est un nombre");
        		}
        	
        	if(!nomC.getText().isEmpty() && !prenomC.getText().isEmpty() && y<=(year-18) && (femmeC.isSelected() || hommeC.isSelected())&& !metierC.getText().isEmpty()
        		 && !villeC.getText().isEmpty() && Integer.parseInt(tlfC.getText())>0 ) {
        			       		

        		if (femmeC.isSelected()) sexee="Femme";
        		else sexee="Homme";
    	    	c.setNomC(nomC.getText());
    	    	c.setPrenomC(prenomC.getText());
    	    	java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateC.getValue());
				c.setDateNaiss(gettedDatePickerDate);
				c.setSexe(sexee);
    	    	c.setMetier(metierC.getText());
        		c.setVille(villeC.getText());
        		c.setNumTlf(Integer.parseInt(tlfC.getText()));
        		c.setIdC(Integer.parseInt(idC.getText()));

        		clt.updateClient(c);
        		btnAnnuler(event);
        		showClientList();

    			   }
        	else {
        	if(nomC.getText().isEmpty()) {
      	       	 nomErr.setText("Le nom est obligatoire");
      	   		}    
      			else {
      	       	nomErr.setText("");
      			}
    	    	if(prenomC.getText().isEmpty()) {
    	   	       	 prenomErr.setText("Le prenom est obligatoire");
    	   	   		}    
    	   			else {
    		   	       	prenomErr.setText("");

    	   			}
    	    	if(villeC.getText().isEmpty()) {
    	   	       	 villeErr.setText("La ville est obligatoire");
    	   	   		}    
    	   			else {	   			  			
    	   				villeErr.setText("");  			
    	   			}
    	  	   	if(metierC.getText().isEmpty()) {
    	  	   		metierErr.setText("La metier est obligatoire");
    	  	   	}
    	  	   	else {
    	  	     	metierErr.setText("");
    	  			}
    		   	 if( (((TextField)dateC.getEditor()).getText().isEmpty())) {
    		    		dateErr.setText("La date est obligatoire");
    		    	}
    		    	else {
    		    		if(y>(year-18)) {		   
    			    		dateErr.setText("Vérifier la date de naissance");
    			    	}
    			    	else
    		    		dateErr.setText("");
    				}	
    		    	if(femmeC.isSelected() || hommeC.isSelected()) {
    		    		sexeErr.setText("");
    		    	}    
    				else {
    					sexeErr.setText("Le sexe est obligatoire");
    				}
    	    }

        }
        			       	
		else
	    {
	    	Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Avertissement");
	        alert.setHeaderText(null);
	        alert.setContentText("Vous choisissez un client à modifier");
	        alert.showAndWait();
	    }
    }

    @FXML
    void deleteClient(ActionEvent event) {
    	if(tableview.getSelectionModel().getSelectedItem() != null) 
	    {    
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setResizable(false);
			alert.setContentText("Voulez-vous supprimer ce client");
			Optional<ButtonType> result = alert.showAndWait();
			 if(result.get() == ButtonType.OK) {
				c.setIdC(Integer.parseInt(idC.getText()));
		    	clt.deleteClient(c);
				btnAnnuler(event);
	        	showClientList();
			}	
			
		}
		else
	    {
	    	Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Avertissement");
	        alert.setHeaderText(null);
	        alert.setContentText("Vous choisissez un client à supprimer");
	        alert.showAndWait();
			
			
	    }
    }

    @FXML
    void filter(KeyEvent event) {
    	  ObservableList<ClientModel> list = clt.showClients();
          FilteredList<ClientModel> filterData = new FilteredList<>(list, p -> true);
          searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
              filterData.setPredicate(cl -> {

                  if (newvalue == null || newvalue.isEmpty()) {
                      return true;
                  }
                  String typedText = newvalue.toLowerCase();
                  if (cl.getPrenomC().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  if (cl.getNomC().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                 
                  return false;
              });
              SortedList<ClientModel> sortedList = new SortedList<>(filterData);
              sortedList.comparatorProperty().bind(tableview.comparatorProperty());
              tableview.setItems(sortedList);
                         
              
          });
    }
}
