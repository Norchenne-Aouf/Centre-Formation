package Controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import Model.ClientModel;
import Model.FormateurModel;
import Model.FormationModel;
import Model.InscriptionModel;
import Model.RevenueModel;
import Model.SalleModel;
import Model.SessionModel;
import Service.InscriptionService;
import Service.RevenueService;
import Service.SessionService;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class InscriptionController implements Initializable{
	InscriptionService ins=new InscriptionService();
    InscriptionModel i=new InscriptionModel();
	@FXML
    private ComboBox<String> ComSession, ComClient,comP;

    @FXML
    private Label form,idI,sessErr, clientErr,frais,pErr;
    
    @FXML
    private Button ajouter, modifier,supprimer;

  
    Vector<ClientModel> resClient;
	Vector<SessionModel> resSession;
	
	@FXML
    private TableView<InscriptionModel> tableview;

    @FXML
    private TableColumn<InscriptionModel, Integer> idCol;
    @FXML
    private TableColumn<InscriptionModel, String> sessionCol, formationCol, clientCol, salleCol,
    					dateCol,heurDebutCol, heurFinCol,jCol;
    @FXML
    private TextField searchBox;
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	ajouter.setDisable(false);
   	   	modifier.setDisable(true);
   	   	supprimer.setDisable(true);	
        resSession= ins.Session();
        resClient= ins.client();
        comP.getItems().addAll("Espèces", "Chèque","Carte de crédit");



       try {  
         
         for (int i=0; i<resClient.size();i++) {
 			ComClient.getItems().add(resClient.elementAt(i).getNomC()+ " "+ resClient.elementAt(i).getPrenomC());	
 		}
	
            	
    	for (int i=0; i<resSession.size();i++) {
  			ComSession.getItems().add(resSession.elementAt(i).getNomS());	
  		}
    	
    	ComSession.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) { 
             	String g =ComSession.getSelectionModel().getSelectedItem();

    	    	SessionService sess=new SessionService();
    	    	form.setText(sess.formationNom(ins.formationId(g)));
    	    	frais.setText(Integer.toString(ins.tarif(g))+" DT");
            }    
        });
		
		} catch (Exception e) {
			e.printStackTrace();
		}
       tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
		    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		    //Check whether item is selected and set value of selected item to Label
		    if(tableview.getSelectionModel().getSelectedItem() != null) 
		    {    
		    	ajouter.setDisable(true);
		    	modifier.setDisable(false);
		    	supprimer.setDisable(false);
		    	InscriptionModel ii = tableview.getSelectionModel().getSelectedItem();
	  	    	idI.setText(Integer.toString(ii.getIdI()));
	  	    	ComSession.setValue(ii.getNomSValue());
	  	    	ComClient.setValue(ii.getClientValue());
	  	    	comP.setValue(ii.getMoyenPaiement());

		    				    	
		    }
		    }
		    });
       showInscriptionList();
       
    }
    public void showInscriptionList(){
    	ObservableList<InscriptionModel> list = ins.showInscriptions();

    	idCol.setCellValueFactory(new PropertyValueFactory<>("idI"));
    	dateCol.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
    	
    	sessionCol.setCellValueFactory(new PropertyValueFactory<>("nomSValue"));
    	formationCol.setCellValueFactory(new PropertyValueFactory<>("FormationValue"));
    	salleCol.setCellValueFactory(new PropertyValueFactory<>("SalleValue"));
    	heurDebutCol.setCellValueFactory(new PropertyValueFactory<>("HeurDebutValue"));
    	heurFinCol.setCellValueFactory(new PropertyValueFactory<>("HeurFinValue"));
    	jCol.setCellValueFactory(new PropertyValueFactory<>("JourValue"));

    	
    	clientCol.setCellValueFactory(new PropertyValueFactory<>("clientValue"));
		
		tableview.setItems(list);
		
    } 
    @FXML
    void btnAjoutI(ActionEvent event) {
    	if(!ComSession.getSelectionModel().isEmpty() && !comP.getSelectionModel().isEmpty() && !ComClient.getSelectionModel().isEmpty()) { 
    	
    	int sesss =ComSession.getSelectionModel().getSelectedIndex();
    	i.setSession(resSession.elementAt(sesss));
    	
    	int client =ComClient.getSelectionModel().getSelectedIndex();
    	i.setClient(resClient.elementAt(client));
    	
		i.setMoyenPaiement(comP.getValue());

    	RevenueService rv=new RevenueService();
        RevenueModel r=new RevenueModel();
    	r.setMoyenPaiement(comP.getValue());
	    r.setRegle(resClient.elementAt(client).getNomC()+ ""+(resClient.elementAt(client).getPrenomC()));
	    r.setDescription(resSession.elementAt(sesss).getNomS());
	    java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    r.setDateR(sqlDate);
	    r.setMontant(resSession.elementAt(sesss).getFrais());
	    r.setEtat(0);
	    r.setLabel("Inscription");
	    
		rv.insertRevenue(r);   
		
		ins.insertInscription(i);
		
		btnAnnuler(event);
		showInscriptionList();}
    	
    
    else {
    if(ComSession.getSelectionModel().isEmpty()) {
	       	 sessErr.setText("La session est obligatoire");
	   		}    
		else {
	sessErr.setText("");
			}
    if(comP.getSelectionModel().isEmpty()) {
	       	 pErr.setText("Le moyen de paiement est obligatoire");
	   		}    
		else {
				pErr.setText("");
			}
	if(ComClient.getSelectionModel().isEmpty()) {
	       	 clientErr.setText("Le client est obligatoire");
	   		}    
		else {
				clientErr.setText("");
			}
    }
}
    
    @FXML
    void btnAnnuler(ActionEvent event) {
		 tableview.getSelectionModel().clearSelection();

    	ComSession.getSelectionModel().clearSelection();
    	ComClient.getSelectionModel().clearSelection();
    	form.setText("");
    	idI.setText("");
    	ajouter.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
    }
    
    
    @FXML
    void btnModifierC(ActionEvent event) {
    	
    	if(!ComSession.getSelectionModel().isEmpty() && !comP.getSelectionModel().isEmpty() && !ComClient.getSelectionModel().isEmpty()) { 
        	
        	int sesss =ComSession.getSelectionModel().getSelectedIndex();
        	i.setSession(resSession.elementAt(sesss));
        	
        	int client =ComClient.getSelectionModel().getSelectedIndex();
        	i.setClient(resClient.elementAt(client));
    		i.setMoyenPaiement(comP.getValue());

        	RevenueService rv=new RevenueService();
            RevenueModel r=new RevenueModel();
        	r.setMoyenPaiement(comP.getValue());
    	    r.setRegle(resClient.elementAt(client).getNomC()+ ""+(resClient.elementAt(client).getPrenomC()));
    	    r.setDescription(resSession.elementAt(sesss).getNomS() +" "+resSession.elementAt(sesss).getDescription());
    	    java.util.Date utilDate = new java.util.Date();
    	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    	    r.setDateR(sqlDate);
    	    r.setMontant(resSession.elementAt(sesss).getFrais());
    	    r.setEtat(0);
    	    
    		rv.insertRevenue(r);    
    		
    		i.setIdI(Integer.parseInt(idI.getText()));

           	
		ins.updateInscription(i);
		btnAnnuler(event);
		showInscriptionList();}
    	 else {
    		    if(ComSession.getSelectionModel().isEmpty()) {
    			       	 sessErr.setText("La session est obligatoire");
    			   		}    
    				else {
    			sessErr.setText("");
    					}
    		    if(comP.getSelectionModel().isEmpty()) {
    			       	 pErr.setText("Le moyen de paiement est obligatoire");
    			   		}    
    				else {
    						pErr.setText("");
    					}
    			if(ComClient.getSelectionModel().isEmpty()) {
    			       	 clientErr.setText("Le client est obligatoire");
    			   		}    
    				else {
    						clientErr.setText("");
    					}
    		    }
    	
    }
    
    @FXML
    void deleteInscription(ActionEvent event) {
    	if(tableview.getSelectionModel().getSelectedItem() != null) 
	    { 
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setResizable(false);
			alert.setContentText("Voulez-vous supprimer cette inscription");
			Optional<ButtonType> result = alert.showAndWait();
			 if(result.get() == ButtonType.OK) {
				 i.setIdI(Integer.parseInt(idI.getText()));
				 ins.deleteInscription(i);
				 showInscriptionList();
	        	 btnAnnuler(event);
			 }
		}
		else
	    {
	    	Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Avertissement");
	        alert.setHeaderText(null);
	        alert.setContentText("il faut que Vous choisissez une session à supprimer");
	        alert.showAndWait();
	    }
    }
    
    @FXML
    void filter(KeyEvent event) {
    	  ObservableList<InscriptionModel> list = ins.showInscriptions();
          FilteredList<InscriptionModel> filterData = new FilteredList<>(list, p -> true);
          searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
              filterData.setPredicate(ii -> {

                  if (newvalue == null || newvalue.isEmpty()) {
                      return true;
                  }
                  String typedText = newvalue.toLowerCase();
                  if (ii.getFormationValue().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  if (ii.getClientValue().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  
                  if (ii.getNomSValue().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                  
                  if (ii.getSalleValue().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }              
                 
                  return false;
              });
              SortedList<InscriptionModel> sortedList = new SortedList<>(filterData);
              sortedList.comparatorProperty().bind(tableview.comparatorProperty());
              tableview.setItems(sortedList);
                         
              
          });
    }
}
