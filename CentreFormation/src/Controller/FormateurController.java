package Controller;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Model.FormateurModel;
import Model.FormationModel;
import Service.FormateurService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class FormateurController implements Initializable{
	 	FormateurService frm=new FormateurService();
	    FormateurModel f=new FormateurModel();
	  	@FXML
	    private TextField nomFo,prenomFo,domaineFo,emailFo,tlfFo,searchBox;
	    @FXML
	    private DatePicker date;
	    @FXML
	    private RadioButton hommeFo,femmeFo;
	    @FXML
	    private ToggleGroup sexe;
	    String sexee;
	    @FXML
	    private TableView<FormateurModel> tableview;
		@FXML
		private Label idFo,nomTab,domTab,sexeTab,emailTab,tlfTab,tlfErr,nomErr,prenomErr,domErr,dateErr, emailErr,sexeErr; 
		@FXML
	    private Button Ajouter,modifier, supprimer;
	    
	    @FXML
	    private TableColumn<FormateurModel, Integer> idCol,tlfCol;

	    @FXML
	    private TableColumn<FormateurModel, String> nomCol,prenomCol,sexeCol, domCol, emailCol;
	  
	    @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
	    	showFormateurList();
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
				    	FormateurModel f = tableview.getSelectionModel().getSelectedItem();
			  	    	idFo.setText(Integer.toString(f.getIdFo()));
				    	nomFo.setText(f.getNom());
				    	prenomFo.setText(f.getPrenom());
				    	Date datee =f.getDateD();  
		                LocalDate l=datee.toLocalDate();
		                date.setValue(l);
				    	if (f.getSexe().equals("Femme"))femmeFo.setSelected(true);	  	    		
				    	if (f.getSexe().equals("Homme"))hommeFo.setSelected(true);	  	    		
				    	domaineFo.setText(f.getDomaine());
				    	emailFo.setText(f.getEmail());
				    	tlfFo.setText(Integer.toString(f.getNumTlf()));
				    				    	
				    }
				    }
				    });	
	    	 
	    	 int year = Calendar.getInstance().get(Calendar.YEAR);
	    	 date.setValue(LocalDate.of(year-18, 1, 1));
	    	

	          
	    }
	    public void showFormateurList(){
	    	ObservableList<FormateurModel> list = frm.showFormateurs();
	    	idCol.setCellValueFactory(new PropertyValueFactory<>("idFo"));
			nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
			prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
			sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));
			domCol.setCellValueFactory(new PropertyValueFactory<>("domaine"));
			emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
			tlfCol.setCellValueFactory(new PropertyValueFactory<>("numTlf"));
			
			tableview.setItems(list);
	    }
	    public boolean isValidEmailAddress(String email) {
	           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	           java.util.regex.Matcher m = p.matcher(email);
	           return m.matches();
	    }
	    
	    @FXML
	    void btnAjoutF(ActionEvent event) {
	    	int year = Calendar.getInstance().get(Calendar.YEAR);
		    int y= Integer.parseInt(((TextField)date.getEditor()).getText().substring(6,10));

	    	try{
	    	    int entier = Integer.parseInt(tlfFo.getText());
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
	    		 if(tlfFo.getText().equals("")) {
	    			 tlfErr.setText("Le numéro de téléphone est obligatoire");
	          	}else
	          		tlfErr.setText("Le numéro de téléphone est un nombre");
	    		}
	    	
	    	if(!nomFo.getText().isEmpty() && !prenomFo.getText().isEmpty() && y<=(year-18) && (femmeFo.isSelected() || hommeFo.isSelected())&& !domaineFo.getText().isEmpty()
	    		 && isValidEmailAddress(emailFo.getText()) && Integer.parseInt(tlfFo.getText())>0 ) {
	    			       		

			    	if (femmeFo.isSelected()) sexee="Femme";
		    		else sexee="Homme";
			    	f.setNom(nomFo.getText());
			    	f.setPrenom(prenomFo.getText());
			    	java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(date.getValue());
					f.setDateD(gettedDatePickerDate);
		    		f.setSexe(sexee);
			    	f.setDomaine(domaineFo.getText());
		    		f.setEmail(emailFo.getText());
		    		f.setNumTlf(Integer.parseInt(tlfFo.getText()));
		    		frm.insertFormateur(f);
		    		btnAnnuler(event);
		    		showFormateurList();
	    	}else {
		    	if(nomFo.getText().isEmpty()) {
	   	       	 nomErr.setText("Le nom est obligatoire");
	   	   		}    
	   			else {
	   	       	nomErr.setText("");
	   			}
		    	if(prenomFo.getText().isEmpty()) {
		   	       	 prenomErr.setText("Le prenom est obligatoire");
		   	   		}    
		   			else {
			   	       	prenomErr.setText("");

		   			}
		    	if(emailFo.getText().isEmpty()) {
		   	       	 emailErr.setText("L'email est obligatoire");
		   	   		}    
		   			else {
		   				
	   			  if (isValidEmailAddress(emailFo.getText()) )
		   				emailErr.setText("");
	   			  else
	   				emailErr.setText("Vérifier votre email");
		   			}
	   	   	if(domaineFo.getText().isEmpty()) {
	   	   		domErr.setText("Le domaine est obligatoire");
	   	   	}
	   	   	else {
	   	       	domErr.setText("");
	   			}
		   	 if( (((TextField)date.getEditor()).getText().isEmpty())) {
		    		dateErr.setText("La date est obligatoire");
		    	}
		    	else {
		    		
			    	if(y>(year-18)) {		   
			    		dateErr.setText("Vérifier la date de naissance");
			    	}
			    	else
		    		dateErr.setText("");
				}	
		    	if(femmeFo.isSelected() || hommeFo.isSelected()) {
		    		sexeErr.setText("");
		    	}    
				else {
					sexeErr.setText("Le sexe est obligatoire");
				}
		    }
	    	
		   }

	    @FXML
	    void btnAnnuler(ActionEvent event) {
	    	nomFo.setText("");
	    	prenomFo.setText("");
		    ((TextField)date.getEditor()).setText("");
		    femmeFo.setSelected(false);
		    hommeFo.setSelected(false);
	    	domaineFo.setText("");
	    	emailFo.setText("");
	    	tlfFo.setText("");
	    	idFo.setText("");
			tableview.getSelectionModel().clearSelection();
			tlfErr.setText("");
			nomErr.setText("");
			prenomErr.setText("");
			domErr.setText("");
			dateErr.setText("");
			emailErr.setText("");
			sexeErr.setText("");
			Ajouter.setDisable(false);
	    	modifier.setDisable(true);
	    	supprimer.setDisable(true);
			
	    }
	    @FXML
        private void btnModifierF(ActionEvent event) throws IOException{  
	    	 int year = Calendar.getInstance().get(Calendar.YEAR);
		    	int y= Integer.parseInt(((TextField)date.getEditor()).getText().substring(6,10));
    		if(tableview.getSelectionModel().getSelectedItem() != null) 
		    {    
    			

    	    	try{
    	    	    int entier = Integer.parseInt(tlfFo.getText());
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
    	    		 if(tlfFo.getText().equals("")) {
    	    			 tlfErr.setText("Le numéro de téléphone est obligatoire");
    	          	}else
    	          		tlfErr.setText("Le numéro de téléphone est un nombre");
    	    		}
    	    	
    	    	if(!nomFo.getText().isEmpty() && !prenomFo.getText().isEmpty() && y<=(year-18) && (femmeFo.isSelected() || hommeFo.isSelected())&& !domaineFo.getText().isEmpty()
    	    		 && isValidEmailAddress(emailFo.getText()) && Integer.parseInt(tlfFo.getText())>0 ) {

		       		if (femmeFo.isSelected()) sexee="Femme";
	            		else sexee="Homme";
	        	    	f.setNom(nomFo.getText());
	        	    	f.setPrenom(prenomFo.getText());
	        	    	java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(date.getValue());
						f.setDateD(gettedDatePickerDate);
						f.setSexe(sexee);
	        	    	f.setDomaine(domaineFo.getText());
	            		f.setEmail(emailFo.getText());
	            		f.setNumTlf(Integer.parseInt(tlfFo.getText()));
	            		f.setIdFo(Integer.parseInt(idFo.getText()));
	
	            		frm.updateFormateur(f);;
	            		btnAnnuler(event);
	            		showFormateurList();
    	    	}else {
    		    	if(nomFo.getText().isEmpty()) {
    	   	       	 nomErr.setText("Le nom est obligatoire");
    	   	   		}    
    	   			else {
    	   	       	nomErr.setText("");
    	   			}
    		    	if(prenomFo.getText().isEmpty()) {
    		   	       	 prenomErr.setText("Le prenom est obligatoire");
    		   	   		}    
    		   			else {
    			   	       	prenomErr.setText("");

    		   			}
    		    	if(emailFo.getText().isEmpty()) {
    		   	       	 emailErr.setText("L'email est obligatoire");
    		   	   		}    
    		   			else {
    		   				
    	   			  if (isValidEmailAddress(emailFo.getText()) )
    		   				emailErr.setText("");
    	   			  else
    	   				emailErr.setText("Vérifier votre email");
    		   			}
    	   	   	if(domaineFo.getText().isEmpty()) {
    	   	   		domErr.setText("Le domaine est obligatoire");
    	   	   	}
    	   	   	else {
    	   	       	domErr.setText("");
    	   			}
    		   	 if( (((TextField)date.getEditor()).getText().isEmpty())) {
    		    		dateErr.setText("La date est obligatoire");
    		    	}
    		    	else {
    		    		
    			    	if(y>(year-18)) {		   
    			    		dateErr.setText("Vérifier la date de naissance");
    			    	}
    			    	else
    		    		dateErr.setText("");
    				}	
    		    	if(femmeFo.isSelected() || hommeFo.isSelected()) {
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
		        alert.setContentText("Il faut que Vous choisissez une formateur à modifier");
		        alert.showAndWait();
		    }
     }
	    @FXML
	    void deleteFormateur(ActionEvent event) {
	    	if(tableview.getSelectionModel().getSelectedItem() != null) 
		    {    
	    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setResizable(false);
				alert.setContentText("Voulez-vous vraiment supprimer ce formateur");
				Optional<ButtonType> result = alert.showAndWait();
				 if(result.get() == ButtonType.OK) {
					f.setIdFo(Integer.parseInt(idFo.getText()));
					frm.deleteFormateur(f);
					btnAnnuler(event);
	            	showFormateurList();
	            	}
			}
			else
		    {
		    	Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("Avertissement");
		        alert.setHeaderText(null);
		        alert.setContentText("Il faut que Vous choisissez un formateur à supprimer");
		        alert.showAndWait();
		    }
	    }
	    
	    @FXML
        private void filter(KeyEvent ke) {
            ObservableList<FormateurModel> list = frm.showFormateurs();
            FilteredList<FormateurModel> filterData = new FilteredList<>(list, p -> true);
            searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
                filterData.setPredicate(forma -> {

                    if (newvalue == null || newvalue.isEmpty()) {
                        return true;
                    }
                    String typedText = newvalue.toLowerCase();
                    if (forma.getDomaine().toLowerCase().indexOf(typedText) != -1) {

                        return true;
                    }
                    if (forma.getNom().toLowerCase().indexOf(typedText) != -1) {

                        return true;
                    }
                   
                    return false;
                });
                SortedList<FormateurModel> sortedList = new SortedList<>(filterData);
                sortedList.comparatorProperty().bind(tableview.comparatorProperty());
                tableview.setItems(sortedList);
                           
                
            });

        }
}
