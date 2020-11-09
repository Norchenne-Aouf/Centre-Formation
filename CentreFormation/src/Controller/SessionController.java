package Controller;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;


import Model.FormateurModel;
import Model.FormationModel;
import Model.InscriptionModel;
import Model.SalleModel;
import Model.SessionModel;
import Service.FormateurService;
import Service.SessionService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class SessionController implements Initializable{
	SessionService sess=new SessionService();
    SessionModel s=new SessionModel();
    
    Vector<FormationModel> resFormation;
	Vector<FormateurModel> resFormateur;
	Vector<SalleModel> resSalle;
	
    FormateurService FS=new FormateurService();
    @FXML
	ComboBox<String> ComFormation,ComSalle,comJour ;
    @FXML
    ListView<String> ComFormateur;
    
	@FXML
    private TextField nomS,nbreH,capS,heurDebutS, heurFinS, frais,jour;
	@FXML
    private TextArea domS;
    @FXML
    private DatePicker dateDebut,dateFin;  
    @FXML
    private TableView<SessionModel> tableview;

    @FXML
    private TableColumn<SessionModel, Integer> idCol,capCol;
  
   

    @FXML
    private TableColumn<SessionModel, String> nomCol,formationCol,salleCol,dateDebCol,dateFinCol,heurDebutCol,heurFinCol;

    @FXML
    private TextField searchBox;  
    
    @FXML
    private Label idS, formationErr,formateurErr,nomErr,dateDebutErr, dateFinErr,capErr,
    heurFinErr,salleErr,nbreErr, heurDebutErr, fraisErr, jourErr;

    @FXML
    private Button AjoutSession,modifier, supprimer,verifierSalle;
       
  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        comJour.getItems().addAll("Jour paire", "Jour impaire","Tous les jours","Jour spécifique");
		ComFormateur.setDisable(false);
       
        nbreH.setDisable(true);
        capS.setDisable(true);
		frais.setDisable(true);
		domS.setDisable(true);
        resFormation= sess.formation();
	    resFormateur= sess.formateur();
	    resSalle= sess.salle();
		
    	try {
			for (int i=0; i<resFormation.size();i++) {
				ComFormation.getItems().add(resFormation.elementAt(i).getNomF());
		
			}
			ComFormation.valueProperty().addListener(new ChangeListener<String>() {
		            @Override 
		            public void changed(ObservableValue ov, String t, String t1) {                
		            	String type=sess.verifType(ComFormation.getValue());
						if (type.equals("BTS"))	{
							ComFormateur.setDisable(false);
					        ComFormateur.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
					    }
						if (type.equals("BTP"))	{
							ComFormateur.setDisable(false);
					        ComFormateur.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
					    }
						
						
		            }    
		        });
			for (int i=0; i<resSalle.size();i++) {
				ComSalle.getItems().add(resSalle.elementAt(i).getNomSa());
		
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	showSessionList();
    	AjoutSession.setDisable(false);
    	modifier.setDisable(true);
    	supprimer.setDisable(true);
    	tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
			    if(tableview.getSelectionModel().getSelectedItem() != null) 
			    {   
			    	AjoutSession.setDisable(true);
			    	modifier.setDisable(false);
			    	supprimer.setDisable(false); 
			    	nbreH.setDisable(false);
    				capS.setDisable(false);
    				frais.setDisable(false);
    				domS.setDisable(false); 
			    	SessionModel ss = tableview.getSelectionModel().getSelectedItem();
		  	    	ComFormation.setValue(ss.getNomFormation());
		  	    	//ComFormateur.setItems(ss.getPrenomFormateur());
		  	    	ComSalle.setValue(ss.getNomSalle());

		  	    	idS.setText(Integer.toString(ss.getIdS()));
			    	nomS.setText(ss.getNomS());
			    	domS.setText(ss.getDescription());
			    	Date dateD =ss.getDateDebut();  
	                LocalDate l=dateD.toLocalDate();
	                dateDebut.setValue(l);
	                
	                Date dateF =ss.getDateFin();  
	                LocalDate lll=dateF.toLocalDate();
	                dateFin.setValue(lll);
			    	capS.setText(Integer.toString(ss.getCapacite()));
			    	heurDebutS.setText(ss.getHeurDebut());
			    	heurFinS.setText(ss.getHeurFin());
			    	frais.setText(Integer.toString(ss.getFrais()));
			    	jour.setText(ss.getJour());
			    	nbreH.setText(Integer.toString(ss.getNbreHeur()));
			    		
	            	if (ss.getJour().equals("Lundi, Mercredi, Vendredi")) {
	            		jour.setDisable(true);  
	            		comJour.setValue("Jour paire");
	            	}
	            	else if (ss.getJour().equals("Mardi, Jeudi, Samedi")) {
	            		jour.setDisable(true); 
	            		comJour.setValue("Jour impaire");

	            	}
	            	else if (ss.getJour().equals("Lundi, Mardi, Mercredi, Jeudi, Vendredi, Samedi")) {
	            		jour.setDisable(true);
	            		comJour.setValue("Tous les jours");

	            	}else {
	            		jour.setDisable(false);  
	            		jour.setText("");
	            		comJour.setValue("Jour spécifique");
	            	}
			    }
			    }
			    });	
	   	    dateDebut.setValue(LocalDate.now());
	   	    dateFin.setValue(LocalDate.now());
	   	    comJour.valueProperty().addListener(new ChangeListener<String>() {
	            @Override 
	            public void changed(ObservableValue ov, String t, String t1) {                
	            	if(comJour.getValue().equals("Jour spécifique")) {
	            		jour.setDisable(false);  
	            		jour.setText("");
	            	}
	            
	            	else if (comJour.getValue().equals("Jour paire")) {
	            		jour.setText("Lundi, Mercredi, Vendredi");
	            		jour.setDisable(true);       
	            	}
	            	else if (comJour.getValue().equals("Jour impaire")) {
	            		jour.setText("Mardi, Jeudi, Samedi");
	            		jour.setDisable(true);       
	            	}
	            	else if (comJour.getValue().equals("Tous les jours")) {
	            		jour.setText("Lundi, Mardi, Mercredi, Jeudi, Vendredi, Samedi");
	            		jour.setDisable(true);       
	            	}
	            	
	            }    
	        });
	  
	   	ObservableList<String> C = FXCollections.observableArrayList() ;
	   	for (int i=0;i<resFormateur.size();i++) {
			//ComFormateur.getItems().add(resFormateur.elementAt(i).getPrenom());
	   		//	ComFormateur.getSelectionModel().getSelectedItems();
			C.add(resFormateur.elementAt(i).getPrenom()+" "+resFormateur.elementAt(i).getNom());
		}
	   	
	   	ComFormateur.setItems(C); 
	   	ComFormateur.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); 
	   
	
   	}
	
	public Integer hD( ) {
		 int hD=Integer.parseInt(heurDebutS.getText().substring(0,2));
		 return hD;		
	}
	
	public Integer hF( ) {
		 int hF=Integer.parseInt(heurFinS.getText().substring(0,2));
		 return hF;		
	}
	public Integer mD( ) {
		 int mD=Integer.parseInt(heurDebutS.getText().substring(3,5));
		 return mD;		
	}
	public Integer mF( ) {
		 int mF=Integer.parseInt(heurFinS.getText().substring(3,5));
		 return mF;		
	}
	
	  
	@FXML
    void btnAjoutS(ActionEvent event) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		
    	int yDeb= Integer.parseInt(((TextField)dateDebut.getEditor()).getText().substring(6,10));
    	int yFin= Integer.parseInt(((TextField)dateFin.getEditor()).getText().substring(6,10));

    	int mDeb= Integer.parseInt(((TextField)dateDebut.getEditor()).getText().substring(3,5));
    	int mFin= Integer.parseInt(((TextField)dateFin.getEditor()).getText().substring(3,5));

    	int dDeb= Integer.parseInt(((TextField)dateDebut.getEditor()).getText().substring(0,2));
    	int dFin= Integer.parseInt(((TextField)dateFin.getEditor()).getText().substring(0,2));

    	
		try{
    	    int entier = Integer.parseInt(capS.getText());
    	    capErr.setText("");       		
    		if(entier>0) {
    	    	capErr.setText("");        		
    	    }    	 
    		else {
    			capErr.setText("La capacité doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(capS.getText().equals("")) {
    			 capErr.setText("La capacité est obligatoire");
          	}else
          		capErr.setText("La capacité est un nombre");
    		}
		try{
    	    int entier = Integer.parseInt(nbreH.getText());
    	    nbreErr.setText("");       		
    		if(entier>0) {
    			nbreErr.setText("");        		
    	    }    	 
    		else {
    			System.out.println("changez le numero !");
    			nbreErr.setText("Le nombre d'heure doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(nbreH.getText().equals("")) {
    			 nbreErr.setText("Le nombre d'heure est obligatoire");
          	}else
          		nbreErr.setText("Le nombre d'heure est un nombre");
    		}
		try{
    	    int entier = Integer.parseInt(frais.getText());
    	    fraisErr.setText("");       		
    		if(entier>0) {
    	    	System.out.println("Est un numero positif!");
    	    	fraisErr.setText("");        		
    	    }    	 
    		else {
    			System.out.println("changez le numero !");
    			fraisErr.setText("Le frais doit etre positif et différent de zéro");  
    		}
    	}catch(Exception parEx){
    		 if(frais.getText().equals("")) {
    			 fraisErr.setText("Le frais  est obligatoire");
          	}else
          		fraisErr.setText("Le frais est un nombre");
    		}
		
		if( !ComFormation.getSelectionModel().isEmpty() && !ComFormateur.getSelectionModel().isEmpty() 
				&& !nomS.getText().isEmpty() && (!heurDebutS.getText().isEmpty() && hD()>=8 && hD()<=21 && mD() >=0 && mD()<=59) && (!heurFinS.getText().isEmpty() && hF()>=8 && hF()<=21 && mF() >=0 && mF()<=59)  
				&& !jour.getText().isEmpty()	&& !domS.getText().isEmpty()  
			&&  ((yFin>=year) || (yFin>yDeb) || ((yFin==yDeb) && (mFin>mDeb)) || ((yFin==yDeb) && (mFin==mDeb)
			&& (dFin>=dDeb)))&& Integer.parseInt(capS.getText())>0 && Integer.parseInt(nbreH.getText())>0 && !heurFinS.getText().isEmpty() && Integer.parseInt(capS.getText())>0) {
	    	
			
			
			FormationModel f=new FormationModel();
	    	FormateurModel fo=new FormateurModel();
	    	Vector<FormateurModel> V = new Vector();
	    	
	    	SalleModel sa=new SalleModel();
	    	
	    	int G =ComFormation.getSelectionModel().getSelectedIndex();
	    	s.setFrmtion(resFormation.elementAt(G));
	    	
	    	int SS =ComSalle.getSelectionModel().getSelectedIndex();
	    	s.setSalle(resSalle.elementAt(SS));
	    	
	    ObservableList<Integer> O= ComFormateur.getSelectionModel().getSelectedIndices();   
	    for (int i =0; i<O.size();i++) {
	    	V.add(resFormateur.elementAt(O.get(i)));
	    }
	    	
	    	s.setFrmteur(V);
	    	
	    	
			s.setNomS(nomS.getText());
			s.setDescription(domS.getText());
			java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateDebut.getValue());
			s.setDateDebut(gettedDatePickerDate);
			java.sql.Date gettedDatePickerDateF = java.sql.Date.valueOf(dateFin.getValue());
			s.setDateFin(gettedDatePickerDateF);
	    	s.setCapacite(Integer.parseInt(capS.getText()));
			s.setHeurDebut(heurDebutS.getText());
			s.setJour(jour.getText());
			s.setHeurFin(heurFinS.getText());
			s.setFrais(Integer.parseInt(frais.getText()));
			s.setNbreHeur(Integer.parseInt(nbreH.getText()));
			sess.insertSession(s);
	    	showSessionList();
    		btnAnnuler(event);
		}
		else {
			if(ComFormation.getSelectionModel().isEmpty()) {
	  	       	 formationErr.setText("Le nom de Formation est obligatoire");
	  	   		}    
	  		else {
	  				formationErr.setText("");
	  			}
			
			if(ComFormateur.getSelectionModel().isEmpty()) {
	  	       	 formateurErr.setText("Le prenom de Formateur est obligatoire");
	  	   		}    
	  		else {
	  				formateurErr.setText("");
	  			}
			
			if(nomS.getText().isEmpty()) {
	  	       	 nomErr.setText("Le nom est obligatoire");
	  	   		}    
	  			else {
	  	       	nomErr.setText("");
	  			}
			
			if(heurDebutS.getText().isEmpty()) {
				heurDebutErr.setText("L'heure de début est obligatoire");
	  	   		}    
	  		else {
	  			if (heurDebutS.getText().length()==5 && heurDebutS.getText().substring(2,3).equals(":")) 
	  			{
		  			try{
		  	    		
		  	    		if((hD()>=8 && hD()<=21) && (mD() >=0 && mD()<=59)) {
		  	    	    	System.out.println("Est un numero positif!");
		  	    	    	heurDebutErr.setText("");        		
		  	    	    }    	 
		  	    		else {
		  	    			 heurDebutErr.setText("Vérifier l'heure de début");  
		  	    		
		  	    			System.out.println("changez le numero !");
		  	    		}
		  	    	}catch(Exception parEx){
		  	          	
		  	        		 System.out.println("changez le texte !");
		  	       			 heurDebutErr.setText("L'heure de début est un nombre");
		  	        		}
	  			}
	  			else
	  			{
 	       			 heurDebutErr.setText("L'heure de début doit etre 5 caractere et le 3eme caractere est :");
	  			}
	  	    		  	    	
	  			}
			
			if(heurFinS.getText().isEmpty()) {
				heurFinErr.setText("L'heure de Fin est obligatoire");
	  	   		}    
	  		else {
	  			if (heurFinS.getText().length()==5 && heurFinS.getText().substring(2,3).equals(":")) 
	  			{
	  			try{
	  	    		
	  	    		if((hF()>=8 && hF()<=21) && (mF() >=0 && mF()<=59)) {
	  	    	    	System.out.println("Est un numero positif!");
	  	    	    	if (hF()< hD()) { 	heurFinErr.setText("L'heure de fin supérieur de l'heure de début "); }
	  	    	    	else {
	  	    	    		if (hF()==hD())
	  	    	    		{
	  	    	    			if(mF()<mD()) {
	  		  	    	    		heurFinErr.setText("les minutes de fin doivent etre superieur à les minutes de debut  ");}
	  	    	    			else if (mF()==mD())
	  	    	    			{
	  		  	    	    		heurFinErr.setText("L'heure de la session doit durée à la moins une heure");
	  	    	    			}
	  	    	    			else {
			  	    	    		heurFinErr.setText("");
	  	    	    			}	  	    	    		
	  	    	    		}else  {
	  	    	    			heurFinErr.setText("");
	  	    	    		}
	  	    	    	}
	  	    	    }    	 
	  	    		else {
	  	    			System.out.println("changez le numero !");
	  	    			heurFinErr.setText("L'heure de fin doit etre entre 8 et 20 ");  
	  	    		}
	  	    	}catch(Exception parEx){
	  	    		
	  	        			System.out.println("changez le texte !");
	  	        			heurFinErr.setText("L'heure de fin est un nombre");
	  	        		}
	  			}
	  			else
	  			{
 	       			 heurFinErr.setText("L'heure de fin doit etre 5 caractere et le 3eme caractere est :");
	  			}
	  	    		  	    	
	  			}
		    	
			   	 if( (((TextField)dateDebut.getEditor()).getText().isEmpty())) {
			    		dateDebutErr.setText("La date de début est obligatoire");
			    	}
			    	else {
			    		if(yDeb<year) {		   
				    		dateDebutErr.setText("Vérifier la date de debut");
				    	}
				    	else
				    		dateDebutErr.setText("");
					}	
			   	if( (((TextField)dateFin.getEditor()).getText().isEmpty())) {
		    		dateFinErr.setText("La date de fin est obligatoire");
		    	}
		    	else {
		    		if(yFin<year) {		   
			    		dateFinErr.setText("la date de Fin doit etre superieur ou egale date actuelle ");
			    	}
			    	else
			    	{
			    		if(yFin<yDeb) {
				    		dateFinErr.setText("L'année de fin doit etre superieur ou égale l'année de début");
			    		}
			    		else if(yFin==yDeb)
			    		{
			    			if (mFin<mDeb) {
					    		dateFinErr.setText("Le mois de fin doit etre superieur ou égale le mois de début");
			    			}
			    			else if(mFin==mDeb) {
			    				if(dFin<dDeb) {
						    		dateFinErr.setText("Le jour de fin doit etre superieur ou égale le jour de début");
			    				}
			    				else {			  
					    			dateFinErr.setText("");
					    			}
			    			}
			    			else {			  
				    			dateFinErr.setText("");
				    			}
			    		}
			    		else {			  
			    			dateFinErr.setText("");
			    			}
			    	}
				}
			   	
			   	
		    	if(jour.getText().isEmpty()) {
		   	       	 jourErr.setText("Séléctionner un champs appartir du combobox jour");
		   	   		}    
		   			else {
		   				jourErr.setText("");

		   			}
		}
    }
	 public void showSessionList(){
	    	ObservableList<SessionModel> list = sess.showSessions();
	    	idCol.setCellValueFactory(new PropertyValueFactory<>("idS"));
			nomCol.setCellValueFactory(new PropertyValueFactory<>("nomS"));
			formationCol.setCellValueFactory(new PropertyValueFactory<>("nomFormation"));
			salleCol.setCellValueFactory(new PropertyValueFactory<>("nomSalle"));
			dateDebCol.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
			dateFinCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
			capCol.setCellValueFactory(new PropertyValueFactory<>("capacite"));
			heurDebutCol.setCellValueFactory(new PropertyValueFactory<>("heurDebut"));
			heurFinCol.setCellValueFactory(new PropertyValueFactory<>("heurFin"));
			dateDebut.setValue(LocalDate.now());
	   	    dateFin.setValue(LocalDate.now());
			tableview.setItems(list);
			addButtonToTable();
	    } 
	  @FXML
	    void btnAnnuler(ActionEvent event) {
			 ComFormateur.getSelectionModel().clearSelection();
			ComFormateur.setDisable(false);
			 ComFormation.setValue("");
			 ComSalle.setValue("");
			 comJour.setValue("");
	  		 nomS.setText("");
	  		domS.setText("");
	  		dateDebut.setValue(LocalDate.now());
	   	    dateFin.setValue(LocalDate.now());
	  		capS.setText("");
	  		heurDebutS.setText("");
	  		 heurFinS.setText("");
	  		 frais.setText("");
	  		 jour.setText("");
	      	idS.setText("");
			tableview.getSelectionModel().clearSelection();
			formationErr.setText("");
			formateurErr.setText("");
			nomErr.setText("");
			dateDebutErr.setText("");
			dateFinErr.setText("");
			capErr.setText("");
			heurDebutErr.setText("");
			salleErr.setText("");
			heurFinErr.setText("");
			fraisErr.setText("");
			jourErr.setText("");
	    	modifier.setDisable(true);
	    	supprimer.setDisable(true);
	    	nbreH.setDisable(true);
			capS.setDisable(true);
			frais.setDisable(true);
			domS.setDisable(true); 
	    }

	    @FXML
	    void btnModifierC(ActionEvent event) {
	    	if(tableview.getSelectionModel().getSelectedItem() != null) 
		    { int year = Calendar.getInstance().get(Calendar.YEAR);
			
			
	    	int yDeb= Integer.parseInt(((TextField)dateDebut.getEditor()).getText().substring(6,10));
	    	int yFin= Integer.parseInt(((TextField)dateFin.getEditor()).getText().substring(6,10));

	    	int mDeb= Integer.parseInt(((TextField)dateDebut.getEditor()).getText().substring(3,5));
	    	int mFin= Integer.parseInt(((TextField)dateFin.getEditor()).getText().substring(3,5));

	    	int dDeb= Integer.parseInt(((TextField)dateDebut.getEditor()).getText().substring(0,2));
	    	int dFin= Integer.parseInt(((TextField)dateFin.getEditor()).getText().substring(0,2));

	    	
			try{
	    	    int entier = Integer.parseInt(capS.getText());
	    	    capErr.setText("");       		
	    		if(entier>0) {
	    	    	capErr.setText("");        		
	    	    }    	 
	    		else {
	    			capErr.setText("La capacité doit etre positif et différent de zéro");  
	    		}
	    	}catch(Exception parEx){
	    		 if(capS.getText().equals("")) {
	    			 capErr.setText("La capacité est obligatoire");
	          	}else
	          		capErr.setText("La capacité est un nombre");
	    		}
			try{
	    	    int entier = Integer.parseInt(nbreH.getText());
	    	    nbreErr.setText("");       		
	    		if(entier>0) {
	    			nbreErr.setText("");        		
	    	    }    	 
	    		else {
	    			System.out.println("changez le numero !");
	    			nbreErr.setText("Le nombre d'heure doit etre positif et différent de zéro");  
	    		}
	    	}catch(Exception parEx){
	    		 if(nbreH.getText().equals("")) {
	    			 nbreErr.setText("Le nombre d'heure est obligatoire");
	          	}else
	          		nbreErr.setText("Le nombre d'heure est un nombre");
	    		}
			try{
	    	    int entier = Integer.parseInt(frais.getText());
	    	    fraisErr.setText("");       		
	    		if(entier>0) {
	    	    	System.out.println("Est un numero positif!");
	    	    	fraisErr.setText("");        		
	    	    }    	 
	    		else {
	    			System.out.println("changez le numero !");
	    			fraisErr.setText("Le frais doit etre positif et différent de zéro");  
	    		}
	    	}catch(Exception parEx){
	    		 if(frais.getText().equals("")) {
	    			 fraisErr.setText("Le frais  est obligatoire");
	          	}else
	          		fraisErr.setText("Le frais est un nombre");
	    		}
			
			if( !ComFormation.getSelectionModel().isEmpty() && !ComFormateur.getSelectionModel().isEmpty() 
					&& !nomS.getText().isEmpty() && (!heurDebutS.getText().isEmpty() && hD()>=8 && hD()<=21 && mD() >=0 && mD()<=59) && (!heurFinS.getText().isEmpty() && hF()>=8 && hF()<=21 && mF() >=0 && mF()<=59)  
					&& !jour.getText().isEmpty()	&& !domS.getText().isEmpty()  
				&&  ((yFin>=year) || (yFin>yDeb) || ((yFin==yDeb) && (mFin>mDeb)) || ((yFin==yDeb) && (mFin==mDeb)
				&& (dFin>=dDeb)))&& Integer.parseInt(capS.getText())>0 && Integer.parseInt(nbreH.getText())>0 && !heurFinS.getText().isEmpty() && Integer.parseInt(capS.getText())>0) {
		    	
				
				
				FormationModel f=new FormationModel();
		    	FormateurModel fo=new FormateurModel();
		    	Vector<FormateurModel> V = new Vector();
		    	
		    	SalleModel sa=new SalleModel();
		    	
		    	int G =ComFormation.getSelectionModel().getSelectedIndex();
		    	s.setFrmtion(resFormation.elementAt(G));
		    	
		    	int SS =ComSalle.getSelectionModel().getSelectedIndex();
		    	s.setSalle(resSalle.elementAt(SS));
		    	
		    ObservableList<Integer> O= ComFormateur.getSelectionModel().getSelectedIndices();   
		    for (int i =0; i<O.size();i++) {
		    	V.add(resFormateur.elementAt(O.get(i)));
		    }
		    	
		    	s.setFrmteur(V);
		    	
		    	
				s.setNomS(nomS.getText());
				s.setDescription(domS.getText());
				java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(dateDebut.getValue());
				s.setDateDebut(gettedDatePickerDate);
				java.sql.Date gettedDatePickerDateF = java.sql.Date.valueOf(dateFin.getValue());
				s.setDateFin(gettedDatePickerDateF);
				s.setCapacite(Integer.parseInt(capS.getText()));
				s.setHeurDebut(heurDebutS.getText());
				s.setJour(jour.getText());
				s.setHeurFin(heurFinS.getText());
				s.setFrais(Integer.parseInt(frais.getText()));
				s.setNbreHeur(Integer.parseInt(nbreH.getText()));
				s.setIdS(Integer.parseInt(idS.getText()));

        		sess.updateSession(s);
        		btnAnnuler(event);
        		showSessionList();
			}
			else {
				if(ComFormation.getSelectionModel().isEmpty()) {
		  	       	 formationErr.setText("Le nom de Formation est obligatoire");
		  	   		}    
		  		else {
		  				formationErr.setText("");
		  			}
				
				if(ComFormateur.getSelectionModel().isEmpty()) {
		  	       	 formateurErr.setText("Le prenom de Formateur est obligatoire");
		  	   		}    
		  		else {
		  				formateurErr.setText("");
		  			}
				
				if(nomS.getText().isEmpty()) {
		  	       	 nomErr.setText("Le nom est obligatoire");
		  	   		}    
		  			else {
		  	       	nomErr.setText("");
		  			}
				
				if(heurDebutS.getText().isEmpty()) {
					heurDebutErr.setText("L'heure de début est obligatoire");
		  	   		}    
		  		else {
		  			if (heurDebutS.getText().length()==5 && heurDebutS.getText().substring(2,3).equals(":")) 
		  			{
			  			try{
			  	    		
			  	    		if((hD()>=8 && hD()<=21) && (mD() >=0 && mD()<=59)) {
			  	    	    	System.out.println("Est un numero positif!");
			  	    	    	heurDebutErr.setText("");        		
			  	    	    }    	 
			  	    		else {
			  	    			 heurDebutErr.setText("Vérifier l'heure de début");  
			  	    		
			  	    			System.out.println("changez le numero !");
			  	    		}
			  	    	}catch(Exception parEx){
			  	          	
			  	        		 System.out.println("changez le texte !");
			  	       			 heurDebutErr.setText("L'heure de début est un nombre");
			  	        		}
		  			}
		  			else
		  			{
	 	       			 heurDebutErr.setText("L'heure de début doit etre 5 caractere et le 3eme caractere est :");
		  			}
		  	    		  	    	
		  			}
				
				if(heurFinS.getText().isEmpty()) {
					heurFinErr.setText("L'heure de Fin est obligatoire");
		  	   		}    
		  		else {
		  			if (heurFinS.getText().length()==5 && heurFinS.getText().substring(2,3).equals(":")) 
		  			{
		  			try{
		  	    		
		  	    		if((hF()>=8 && hF()<=21) && (mF() >=0 && mF()<=59)) {
		  	    	    	System.out.println("Est un numero positif!");
		  	    	    	if (hF()< hD()) { 	heurFinErr.setText("L'heure de fin supérieur de l'heure de début "); }
		  	    	    	else {
		  	    	    		if (hF()==hD())
		  	    	    		{
		  	    	    			if(mF()<mD()) {
		  		  	    	    		heurFinErr.setText("les minutes de fin doivent etre superieur à les minutes de debut  ");}
		  	    	    			else if (mF()==mD())
		  	    	    			{
		  		  	    	    		heurFinErr.setText("L'heure de la session doit durée à la moins une heure");
		  	    	    			}
		  	    	    			else {
				  	    	    		heurFinErr.setText("");
		  	    	    			}	  	    	    		
		  	    	    		}else  {
		  	    	    			heurFinErr.setText("");
		  	    	    		}
		  	    	    	}
		  	    	    }    	 
		  	    		else {
		  	    			System.out.println("changez le numero !");
		  	    			heurFinErr.setText("L'heure de fin doit etre entre 8 et 20 ");  
		  	    		}
		  	    	}catch(Exception parEx){
		  	    		
		  	        			System.out.println("changez le texte !");
		  	        			heurFinErr.setText("L'heure de fin est un nombre");
		  	        		}
		  			}
		  			else
		  			{
	 	       			 heurFinErr.setText("L'heure de fin doit etre 5 caractere et le 3eme caractere est :");
		  			}
		  	    		  	    	
		  			}
			    	
				   	 if( (((TextField)dateDebut.getEditor()).getText().isEmpty())) {
				    		dateDebutErr.setText("La date de début est obligatoire");
				    	}
				    	else {
				    		if(yDeb<year) {		   
					    		dateDebutErr.setText("Vérifier la date de debut");
					    	}
					    	else
					    		dateDebutErr.setText("");
						}	
				   	if( (((TextField)dateFin.getEditor()).getText().isEmpty())) {
			    		dateFinErr.setText("La date de fin est obligatoire");
			    	}
			    	else {
			    		if(yFin<year) {		   
				    		dateFinErr.setText("la date de Fin doit etre superieur ou egale date actuelle ");
				    	}
				    	else
				    	{
				    		if(yFin<yDeb) {
					    		dateFinErr.setText("L'année de fin doit etre superieur ou égale l'année de début");
				    		}
				    		else if(yFin==yDeb)
				    		{
				    			if (mFin<mDeb) {
						    		dateFinErr.setText("Le mois de fin doit etre superieur ou égale le mois de début");
				    			}
				    			else if(mFin==mDeb) {
				    				if(dFin<dDeb) {
							    		dateFinErr.setText("Le jour de fin doit etre superieur ou égale le jour de début");
				    				}
				    				else {			  
						    			dateFinErr.setText("");
						    			}
				    			}
				    			else {			  
					    			dateFinErr.setText("");
					    			}
				    		}
				    		else {			  
				    			dateFinErr.setText("");
				    			}
				    	}
					}
				   	
				   	
			    	if(jour.getText().isEmpty()) {
			   	       	 jourErr.setText("Séléctionner un champs appartir du combobox jour");
			   	   		}    
			   			else {
			   				jourErr.setText("");

			   			}
			}
		    }
	    		
			else
		    {
		    	Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setTitle("Avertissement");
		        alert.setHeaderText(null);
		        alert.setContentText("il faut que Vous choisissez une session à modifier");
		        alert.showAndWait();
		    }
	    }
    @FXML
    void deleteSession(ActionEvent event) {
    	if(tableview.getSelectionModel().getSelectedItem() != null) 
	    { 
    		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setResizable(false);
			alert.setContentText("Voulez-vous supprimer cette session");
			Optional<ButtonType> result = alert.showAndWait();
			 if(result.get() == ButtonType.OK) {
				 s.setIdS(Integer.parseInt(idS.getText()));
				 sess.deleteSession(s);
				 showSessionList();
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
    	  ObservableList<SessionModel> list = sess.showSessions();
          FilteredList<SessionModel> filterData = new FilteredList<>(list, p -> true);
          searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
              filterData.setPredicate(sesss -> {

                  if (newvalue == null || newvalue.isEmpty()) {
                      return true;
                  }
                  String typedText = newvalue.toLowerCase();
                  if (sesss.getNomS().toLowerCase().indexOf(typedText) != -1) {

                      return true;
                  }
                 
                  return false;
              });
              SortedList<SessionModel> sortedList = new SortedList<>(filterData);
              sortedList.comparatorProperty().bind(tableview.comparatorProperty());
              tableview.setItems(sortedList);
                         
              
          });
    }
    
    
    @FXML
    void btnVerifieS(ActionEvent event) {
    	ResultSet resSalleNonDispo=sess.salleNonDispo(sess.salleId(ComSalle.getValue())); 
    	int  count =sess.salleDispo(sess.salleId(ComSalle.getValue())); 
    	
    	try {	
    		if(tableview.getSelectionModel().getSelectedItem() != null) 
    	    {
				AjoutSession.setDisable(true);

	    		if(count==0) 
				{
	    				salleErr.setText("La salle est disponible");
				}
	    		else {
				
   				while(resSalleNonDispo.next()) 
   				{
   					
					if (((TextField)dateDebut.getEditor()).getText().equals(resSalleNonDispo.getString("dateDebut"))
					&& ((TextField)dateFin.getEditor()).getText().equals(resSalleNonDispo.getString("dateFin"))
					&& (heurDebutS.getText().equals(resSalleNonDispo.getString("heurDebut")))
					&& (heurFinS.getText().equals(resSalleNonDispo.getString("heurFin")))
					&& (jour.getText().equals(resSalleNonDispo.getString("jour"))))
					{
						salleErr.setText("La salle n'est pas disponible");
					}
					else {
						salleErr.setText("La salle est disponible");
					}
				}}
    	    }
    		else {
    			  
    			if(count==0) 
				{
    				salleErr.setText("La salle est disponible");
    				nbreH.setDisable(false);
    				capS.setDisable(false);
    				frais.setDisable(false);
    				domS.setDisable(false);  				
    				
    			}
    		else {
			
				while(resSalleNonDispo.next()) 
				{
					
				if (((TextField)dateDebut.getEditor()).getText().equals(resSalleNonDispo.getString("dateDebut"))
				&& ((TextField)dateFin.getEditor()).getText().equals(resSalleNonDispo.getString("dateFin"))
				&& (heurDebutS.getText().equals(resSalleNonDispo.getString("heurDebut")))
				&& (heurFinS.getText().equals(resSalleNonDispo.getString("heurFin")))
				&& (jour.getText().equals(resSalleNonDispo.getString("jour"))))
				{
					salleErr.setText("La salle n'est pas disponible");
					nbreH.setDisable(true);
					capS.setDisable(true);
    				frais.setDisable(true);
    				domS.setDisable(true);  	
    			}
				else {
					salleErr.setText("La salle est disponible");
					capS.setDisable(false);
    				frais.setDisable(false);
    				domS.setDisable(false);  
    				nbreH.setDisable(false);  
				}
			}}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    
    private void addButtonToTable() {
        TableColumn<SessionModel, Void> colBtn = new TableColumn("Formateurs");

        Callback<TableColumn<SessionModel, Void>, TableCell<SessionModel, Void>> cellFactory = new Callback<TableColumn<SessionModel, Void>, TableCell<SessionModel, Void>>() {
            @Override
            public TableCell<SessionModel, Void> call(final TableColumn<SessionModel, Void> param) {
                final TableCell<SessionModel, Void> cell = new TableCell<SessionModel, Void>() {

                    private final Button btn = new Button("?");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	if(tableview.getSelectionModel().getSelectedItem() != null) 
            			    {   
       
            			    	SessionModel ss = tableview.getSelectionModel().getSelectedItem();
            			    	ResultSet re=sess.showFormateur(ss.getIdS());
                            	
                            	List<String> choices = new ArrayList<>();
                            	
                            	try {
									while(re.next()) {
		                            	choices.add(re.getString("nom")+" "+re.getString("prenom"));
}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

                            	ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
                            	dialog.setTitle("Formateurs");
                            	dialog.setHeaderText("Liste des formateurs participes à cette session");
                            	dialog.setContentText("Formateurs:");
                            	
                            	Optional<String> result = dialog.showAndWait();
                            	if (result.isPresent()){
                        			tableview.getSelectionModel().clearSelection();
                            	}
            			    }
                        	else {
                        		   Alert alert = new Alert(AlertType.WARNING);
                        	        alert.setTitle("Warning");
                        	        alert.setContentText("Choisissez une session !");
                        	 
                        	        alert.showAndWait();
                        	}
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                   
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableview.getColumns().add(colBtn);

    }
}
