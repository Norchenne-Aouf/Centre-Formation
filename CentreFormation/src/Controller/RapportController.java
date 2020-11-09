package Controller;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Model.DepenseModel;
import Model.RevenueModel;
import Service.DepenseService;
import Service.RevenueService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class RapportController implements Initializable{
	DepenseService dp=new DepenseService();
	RevenueService rv=new RevenueService();
    Vector<RevenueModel> res;
    Vector<DepenseModel> res1;
    	
	@FXML
    private RadioButton a,m;
    @FXML
    private ToggleGroup type;   
    @FXML
    private ComboBox<String>  md;
    @FXML
    private TextField ad;    
    @FXML
    private Label aErr,mErr;
    
	Document document = new Document();
	
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	 md.getItems().addAll("Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre");
    	 md.setVisibleRowCount(4);
    	
    	 if (a.isSelected()) {
    		 ad.setDisable(false); md.setDisable(true);
    		 } 
    	 m.selectedProperty().addListener(new ChangeListener<Boolean>() {
    		    @Override
    		    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
    		    	 if (m.isSelected()) {
    	        		 ad.setDisable(false); md.setDisable(false);
    	        		 }    	
    		    	 else {
    		    		 ad.setDisable(false); md.setDisable(true);

    		    	 }
    		    	 
    		    }
    		}); 
    	//creation d'un dossier rapport 
 		File files = new File("C:\\rapport");
         if (!files.exists()) {
             if (files.mkdirs()) {
                 System.out.println("Multiple directories are created!");
             } else {
                 System.out.println("Failed to create multiple directories!");
              }
         }

    	
         }   
    
    
    @FXML
    void pdf(ActionEvent event) throws  IOException, DocumentException, SQLException, InterruptedException {
	     if (a.isSelected()) {
	    	 try{
	    		if(ad.getText().length()==4) { 
		     	    int entier = Integer.parseInt(ad.getText());
		     	    aErr.setText("");       		
		     		if(entier>0) {
		     	    	aErr.setText("");        		
		     	    }    	 
		     		else {
		     			aErr.setText("L'année doit etre positif");  
		     		}
		     	}
	    		else
	     			aErr.setText("L'année composé de 4 nombres");      		
	    		
	     	}catch(Exception parEx){
	     		 if(ad.getText().equals("")) {
	     			 aErr.setText("L'année est obligatoire");
	           	}else
	           		aErr.setText("L'année est un nombre");
	     		}
	    	 
	    	if(ad.getText().length()==4 && Integer.parseInt(ad.getText())>0 ) {
	    		File files = new File("C:/rapport/"+ad.getText()+".pdf");
	    		int r=rv.totalRevenuesRapportAnnuel(Integer.parseInt(ad.getText().substring(2, 4)));
	    		 int d=dp.totalDepensesRapportAnnuel(Integer.parseInt(ad.getText().substring(2, 4)));

	    	if(r!=0 && d!=0) {
	            if (!files.exists()) {
	            	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(files));
	            	
	            	annuel();
	            	Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " +files);
	    		            p.waitFor();			        
	                writer.close();
	            }
	            else
	            {
	            	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    			alert.setTitle("Confirmation");
	    			alert.setResizable(false);
	    			alert.setContentText(" Un  rapport annuel de "+ ad.getText()+" est déja existant \n  voulez-vous ecraser ?");
	    			Optional<ButtonType> result = alert.showAndWait();
	    			
	    			 if(result.get() == ButtonType.OK) {
	    				 PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(files));
	 	                annuel();
	 	                Process p = Runtime
	 	    		               .getRuntime()
	 	    		               .exec("rundll32 url.dll,FileProtocolHandler " +files);
	 	    		            p.waitFor();			        
	 	                writer.close();
	    			}	
	    			 else {
	    					Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    					alert1.setTitle("Information");
	    					alert1.setHeaderText(null);
	    					alert1.setContentText("Le rapport ne sera pas créé");
	    					alert1.showAndWait();
	    			 }
	    	}}
	    			 else
	    				{
	    					Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    					alert1.setTitle("Information");
	    					alert1.setHeaderText(null);
	    					alert1.setContentText("La date n'existe pas");
	    					alert1.showAndWait();
	    				}
	                		 
	    	}
	     	
	     }	     
	     if (m.isSelected()) {
	    	 try{
	    		if(ad.getText().length()==4) { 
		     	    int entier = Integer.parseInt(ad.getText());
		     	    aErr.setText("");       		
		     		if(entier>0) {
		     	    	aErr.setText("");        		
		     	    }    	 
		     		else {
		     			aErr.setText("L'année doit etre positif");  
		     		}
		     	}
	    		else
	     			aErr.setText("L'année composé de 4 nombres");      		
	    		
	     	}catch(Exception parEx){
	     		 if(ad.getText().equals("")) {
	     			 aErr.setText("L'année est obligatoire");
	           	}else
	           		aErr.setText("L'année est un nombre");
	     		}
	    	 
	    	 if(md.getSelectionModel().isEmpty() ) {
	   	       	 mErr.setText("Séléctionner un mois");
	   	   		}    
	   			else {
	   				mErr.setText("");

	   			}   
	    	 
	    	 if(ad.getText().length()==4 && Integer.parseInt(ad.getText())>0 && !md.getSelectionModel().isEmpty()) {
				
		    	File files = new File("C:/rapport/"+md.getSelectionModel().getSelectedItem()+""+ad.getText()+".pdf");
	    		int r=rv.totalRevenuesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1), Integer.parseInt(ad.getText().substring(2, 4)));
	    		 int d=dp.totalDepensesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1),Integer.parseInt(ad.getText().substring(2, 4)));

	    	if(r!=0 && d!=0) {
	            if (!files.exists()) {
	            	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(files));
					mensuel();	
	                Process p = Runtime
	    		               .getRuntime()
	    		               .exec("rundll32 url.dll,FileProtocolHandler " +files);
	    		            p.waitFor();			        
	                writer.close();
	            }
	            else
	            {
	            	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    			alert.setTitle("Confirmation");
	    			alert.setResizable(false);
	    			alert.setContentText(" Un  rapport mensuelle de "+md.getSelectionModel().getSelectedItem()+" "+ ad.getText()+" est déja existant \n  voulez-vous ecraser ?");
	    			Optional<ButtonType> result = alert.showAndWait();
	    			
	    			 if(result.get() == ButtonType.OK) {
	    				 PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(files));
	    					mensuel();	
	 	                Process p = Runtime
	 	    		               .getRuntime()
	 	    		               .exec("rundll32 url.dll,FileProtocolHandler " +files);
	 	    		            p.waitFor();			        
	 	                writer.close();
	    			}	
	    			 else {
	    					Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    					alert1.setTitle("Information");
	    					alert1.setHeaderText(null);
	    					alert1.setContentText("Le rapport ne sera pas créé");
	    					alert1.showAndWait();
	    			 }
	    	}}
	    			 else
	    				{
	    					Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    					alert1.setTitle("Information");
	    					alert1.setHeaderText(null);
	    					alert1.setContentText("La date n'existe pas");
	    					alert1.showAndWait();
	    				}
	                		 
	    	}
	    	 
	    	 
	     }
}

    
    public void annuel() throws FileNotFoundException, DocumentException, SQLException {
    	
        document.open();
        document.newPage();
        document.addTitle("Rapport financier Annuel "+ ad.getText());
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD,BaseColor.GREEN);
        Font catFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font catFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD,BaseColor.BLUE);
       
      
        
        PdfPTable table1 = new PdfPTable(1); 
		 table1.setWidthPercentage(100); 
		 table1.setSpacingBefore(10f);
		 table1.setSpacingAfter(10f);	
		 PdfPCell cell1 = new PdfPCell(new Paragraph("Rapport financier Annuel "+ ad.getText(),catFont));
		 cell1.setPaddingLeft(10);
		 cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cell1.setVerticalAlignment(Element.ALIGN_CENTER);
		 cell1.setBorderColor(BaseColor.WHITE);
		 table1.addCell(cell1);
		 document.add(table1);
		 
		 
		 PdfPTable table2 = new PdfPTable(1); 
		 table2.setWidthPercentage(100); 
		 table2.setSpacingBefore(10f);
		 table2.setSpacingAfter(10f);
		 PdfPCell cell21 = new PdfPCell(new Paragraph("\n\n - Les résultats et les renseignements contenu dans le présent rapport annuel \n relèvent de notre responsabilité."
		 		+ "\n\n - Cette résponsabilité concerne la fiabilité de l'information et des qui y figurent ainsi que celle \n des controles afférents."
		 		+ "\n\n - A notre connaissance, les données et les renseignements présentés dans ce rapport annuel \nde gestion ainsi que les controles afférents sont fiables, de sorte qu'ils traduisent la situation \ntelle qu'elle se présentait."));
		 cell21.setPaddingLeft(10);
		 cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
		 cell21.setVerticalAlignment(Element.ALIGN_LEFT);
		 cell21.setBorderColor(BaseColor.WHITE);
		 table2.addCell(cell21);
		
		 document.add(table2);
		 res= rv.showRevenuesRapportAnnuel(Integer.parseInt(ad.getText().substring(2, 4)));
 
		 PdfPTable table3 = new PdfPTable(3); 
		 table3.setSpacingBefore(10f);
		 table3.setWidthPercentage(100); 

		 table3.setSpacingAfter(10f);
	 		 PdfPCell cell33 = new PdfPCell(new Paragraph("",catFont2));
	 		 cell33.setPadding(10);
	 		 cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		 cell33.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell33);
	 		
	 		 PdfPCell cell3366 = new PdfPCell(new Paragraph("Révenue",catFont2));
	 		cell3366.setPadding(10);
	 		cell3366.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell3366.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell3366);
	 		
	 		 PdfPCell cell33564 = new PdfPCell(new Paragraph("Dépense",catFont2));
	 		cell33564.setPadding(10);
	 		cell33564.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell33564.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell33564);
	 		

	 		PdfPCell cell34 = new PdfPCell(new Paragraph("Janvier ",catFont3));
			 cell34.setPadding(10);
			 cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell34.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell34);
			 
			 int rj= rv.totalRevenuesRapportMensuel(Integer.parseInt("01"), Integer.parseInt(ad.getText().substring(2, 4)));
				PdfPCell cell3564 = new PdfPCell(new Paragraph(""+rj));
				cell3564.setPadding(10);
				cell3564.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3564.setVerticalAlignment(Element.ALIGN_CENTER);
				 table3.addCell(cell3564); 
			 
			int dj= dp.totalDepensesRapportMensuel(Integer.parseInt("01"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564a = new PdfPCell(new Paragraph(""+dj));
			cell3564a.setPadding(10);
			cell3564a.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564a.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564a);
			 
	 		 PdfPCell cell31 = new PdfPCell(new Paragraph("Février",catFont3));
	 		 cell31.setPadding(10);
	 		 cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		 cell31.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell31);
	 		
	 		int rf= rv.totalRevenuesRapportMensuel(Integer.parseInt("02"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564f = new PdfPCell(new Paragraph(""+rf));
			cell3564f.setPadding(10);
			cell3564f.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564f.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564f); 
		 
		int df= dp.totalDepensesRapportMensuel(Integer.parseInt("02"), Integer.parseInt(ad.getText().substring(2, 4)));
		PdfPCell cell3564ff = new PdfPCell(new Paragraph(""+df));
		cell3564ff.setPadding(10);
		cell3564ff.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3564ff.setVerticalAlignment(Element.ALIGN_CENTER);
		 table3.addCell(cell3564ff);
		 
	 		PdfPCell cell32 = new PdfPCell(new Paragraph("Mars",catFont3));
	 		 cell32.setPadding(10);
	 		 cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		 cell32.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell32);
 		int rm= rv.totalRevenuesRapportMensuel(Integer.parseInt("03"), Integer.parseInt(ad.getText().substring(2, 4)));
		PdfPCell cell3564m = new PdfPCell(new Paragraph(""+rm));
		cell3564m.setPadding(10);
		cell3564m.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3564m.setVerticalAlignment(Element.ALIGN_CENTER);
		 table3.addCell(cell3564m); 
		 
		int dm= dp.totalDepensesRapportMensuel(Integer.parseInt("03"), Integer.parseInt(ad.getText().substring(2, 4)));
		PdfPCell cell3564mm = new PdfPCell(new Paragraph(""+dm));
		cell3564mm.setPadding(10);
		cell3564mm.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3564mm.setVerticalAlignment(Element.ALIGN_CENTER);
		 table3.addCell(cell3564mm);
	 		PdfPCell cell344 = new PdfPCell(new Paragraph("Avril",catFont3));
	 		 cell344.setPadding(10);
	 		cell344.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell344.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell344);
	 		int ra= rv.totalRevenuesRapportMensuel(Integer.parseInt("04"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564aa = new PdfPCell(new Paragraph(""+ra));
			cell3564aa.setPadding(10);
			cell3564aa.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564aa.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564aa); 
			 
			int da= dp.totalDepensesRapportMensuel(Integer.parseInt("04"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564aaa = new PdfPCell(new Paragraph(""+da));
			cell3564aaa.setPadding(10);
			cell3564aaa.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564aaa.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564aaa);
	 		PdfPCell cell3444 = new PdfPCell(new Paragraph("Mai",catFont3));
	 		cell3444.setPadding(10);
	 		cell3444.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell3444.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell3444);
	 		
	 		int rmm= rv.totalRevenuesRapportMensuel(Integer.parseInt("05"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564ma= new PdfPCell(new Paragraph(""+rmm));
			cell3564ma.setPadding(10);
			cell3564ma.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564ma.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564ma); 
			 
			int dmm= dp.totalDepensesRapportMensuel(Integer.parseInt("05"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564maa = new PdfPCell(new Paragraph(""+dmm));
			cell3564maa.setPadding(10);
			cell3564maa.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564maa.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564maa);
	 		
	 		
	 		PdfPCell cell355 = new PdfPCell(new Paragraph("Juin",catFont3));
	 		cell355.setPadding(10);
	 		cell355.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell355.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell355);
	 		
	 		
	 		int rjj= rv.totalRevenuesRapportMensuel(Integer.parseInt("06"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564jj= new PdfPCell(new Paragraph(""+rjj));
			cell3564jj.setPadding(10);
			cell3564jj.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564jj.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564jj); 
			 
			int djj= dp.totalDepensesRapportMensuel(Integer.parseInt("06"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564jjj = new PdfPCell(new Paragraph(""+djj));
			cell3564jjj.setPadding(10);
			cell3564jjj.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564jjj.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564jjj);
			 
			 
	 		PdfPCell cell356 = new PdfPCell(new Paragraph("Juillet",catFont3));
	 		cell356.setPadding(10);
	 		cell356.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell356.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell356);
	 		
	 		int rjjj= rv.totalRevenuesRapportMensuel(Integer.parseInt("07"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564jjjjj= new PdfPCell(new Paragraph(""+rjjj));
			cell3564jjjjj.setPadding(10);
			cell3564jjjjj.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564jjjjj.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564jjjjj); 
			 
			int djjj= dp.totalDepensesRapportMensuel(Integer.parseInt("07"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cell3564jjjj = new PdfPCell(new Paragraph(""+djjj));
			cell3564jjjj.setPadding(10);
			cell3564jjjj.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3564jjjj.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell3564jjjj);
			 
			 
	 		PdfPCell cell3566 = new PdfPCell(new Paragraph("Août",catFont3));
	 		cell3566.setPadding(10);
	 		cell3566.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell3566.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell3566);
	 		
	 		int raa= rv.totalRevenuesRapportMensuel(Integer.parseInt("08"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cella= new PdfPCell(new Paragraph(""+raa));
			cella.setPadding(10);
			cella.setHorizontalAlignment(Element.ALIGN_CENTER);
			cella.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cella); 
			 
			int daa= dp.totalDepensesRapportMensuel(Integer.parseInt("08"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cellaa = new PdfPCell(new Paragraph(""+daa));
			cellaa.setPadding(10);
			cellaa.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellaa.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cellaa);
			 

	 		PdfPCell cell35664 = new PdfPCell(new Paragraph("Septembre",catFont3));
	 		cell35664.setPadding(10);
	 		cell35664.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell35664.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell35664);
	 		
	 		int rs= rv.totalRevenuesRapportMensuel(Integer.parseInt("09"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cells= new PdfPCell(new Paragraph(""+rs));
			cells.setPadding(10);
			cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			cells.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cells); 
			 
			int ds= dp.totalDepensesRapportMensuel(Integer.parseInt("09"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cellss = new PdfPCell(new Paragraph(""+ds));
			cellss.setPadding(10);
			cellss.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellss.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cellss);
			 
			 
	 		PdfPCell cell35646 = new PdfPCell(new Paragraph("Octobre",catFont3));
	 		cell35646.setPadding(10);
	 		cell35646.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell35646.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell35646);
	 		
	 		int ro= rv.totalRevenuesRapportMensuel(Integer.parseInt("10"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cello= new PdfPCell(new Paragraph(""+ro));
			cello.setPadding(10);
			cello.setHorizontalAlignment(Element.ALIGN_CENTER);
			cello.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cello); 
			 
			int doo= dp.totalDepensesRapportMensuel(Integer.parseInt("10"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell celloo = new PdfPCell(new Paragraph(""+doo));
			celloo.setPadding(10);
			celloo.setHorizontalAlignment(Element.ALIGN_CENTER);
			celloo.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(celloo);
			 
	 		PdfPCell cell31455 = new PdfPCell(new Paragraph("Novembre",catFont3));
	 		cell31455.setPadding(10);
	 		cell31455.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell31455.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell31455);
	 		
	 		int rn= rv.totalRevenuesRapportMensuel(Integer.parseInt("11"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell celln= new PdfPCell(new Paragraph(""+rn));
			celln.setPadding(10);
			celln.setHorizontalAlignment(Element.ALIGN_CENTER);
			celln.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(celln); 
			 
			int dn= dp.totalDepensesRapportMensuel(Integer.parseInt("11"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cellnn = new PdfPCell(new Paragraph(""+dn));
			cellnn.setPadding(10);
			cellnn.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellnn.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cellnn);
	 		
	 		PdfPCell cell31544 = new PdfPCell(new Paragraph("Décembre",catFont3));
	 		cell31544.setPadding(10);
	 		cell31544.setHorizontalAlignment(Element.ALIGN_CENTER);
	 		cell31544.setVerticalAlignment(Element.ALIGN_CENTER);
	 		table3.addCell(cell31544);
	 		
	 		int rdd= rv.totalRevenuesRapportMensuel(Integer.parseInt("12"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell celldd= new PdfPCell(new Paragraph(""+rdd));
			celldd.setPadding(10);
			celldd.setHorizontalAlignment(Element.ALIGN_CENTER);
			celldd.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(celldd); 
			 
			int dd= dp.totalDepensesRapportMensuel(Integer.parseInt("12"), Integer.parseInt(ad.getText().substring(2, 4)));
			PdfPCell cellddd= new PdfPCell(new Paragraph(""+dd));
			cellddd.setPadding(10);
			cellddd.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellddd.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cellddd);
	 		
    		 
			 
			 PdfPCell cell56 = new PdfPCell(new Paragraph("Total :",catFont3));			
			 cell56.setPadding(10);

			 cell56.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell56.setVerticalAlignment(Element.ALIGN_CENTER);
			 table3.addCell(cell56);
			 int r=rv.totalRevenuesRapportAnnuel(Integer.parseInt(ad.getText().substring(2, 4)));
    		 int d=dp.totalDepensesRapportAnnuel(Integer.parseInt(ad.getText().substring(2, 4)));

			 PdfPCell cell57 = new PdfPCell(new Paragraph(""+r));
    		 cell57.setPadding(10);
    		 cell57.setBackgroundColor(BaseColor.CYAN);
    		 cell57.setHorizontalAlignment(Element.ALIGN_CENTER);
    		 cell57.setVerticalAlignment(Element.ALIGN_CENTER);
    		 table3.addCell(cell57);
    		 
    		 PdfPCell cell5c = new PdfPCell(new Paragraph(""+d));
    		 cell5c.setPadding(10);
    		 cell5c.setBackgroundColor(BaseColor.CYAN);
    		 cell5c.setHorizontalAlignment(Element.ALIGN_CENTER);
    		 cell5c.setVerticalAlignment(Element.ALIGN_CENTER);
    		 table3.addCell(cell5c);
    		 
    		
			 document.add(table3);
			 PdfPTable table6 = new PdfPTable(1); 
			 table6.setWidthPercentage(90); 
			 table6.setSpacingBefore(10f);
			 table6.setSpacingAfter(10f);
			  PdfPCell cell61 = new PdfPCell(new Paragraph("Bilan: "+(r-d),catFont3));
			 cell61.setPadding(10);
			 cell61.setBorderColor(BaseColor.WHITE);
			 cell61.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell61.setVerticalAlignment(Element.ALIGN_CENTER);
			 table6.addCell(cell61);
		
			 document.add(table6);

        document.close();
		
		
    }
    
    public void mensuel() throws DocumentException, FileNotFoundException {
         document.open();
         document.newPage();
         document.addTitle("Rapport financier Annuel "+ ad.getText());
         Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD,BaseColor.GREEN);
         Font catFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
         Font catFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD,BaseColor.BLUE);

         PdfPTable table1 = new PdfPTable(1); 
 		 table1.setWidthPercentage(100); 
 		 table1.setSpacingBefore(10f);
 		 table1.setSpacingAfter(10f);	
 		 PdfPCell cell1 = new PdfPCell(new Paragraph("Rapport financier Mensuel "+ md.getSelectionModel().getSelectedItem()+"/"+ad.getText(),catFont));
 		 cell1.setPaddingLeft(10);
 		 cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
 		 cell1.setVerticalAlignment(Element.ALIGN_CENTER);
 		 cell1.setBorderColor(BaseColor.WHITE);
 		 table1.addCell(cell1);
 		 document.add(table1);
 		 
 		 
 		 PdfPTable table2 = new PdfPTable(1); 
 		 table2.setWidthPercentage(100); 
 		 table2.setSpacingBefore(10f);
 		 table2.setSpacingAfter(10f);
 		 PdfPCell cell21 = new PdfPCell(new Paragraph("\n\n\n - Les résultats et les renseignements contenu dans le présent rapport mensuel \n relèvent de notre responsabilité."
 		 		+ "\n\n - Cette résponsabilité concerne la fiabilité de l'information et des qui y figurent ainsi que celle \n des controles afférents."
 		 		+ "\n\n - A notre connaissance, les données et les renseignements présentés dans ce rapport mensuel \nde gestion ainsi que les controles afférents sont fiables, de sorte qu'ils traduisent la situation \ntelle qu'elle se présentait."));
 		 cell21.setPaddingLeft(10);
 		 cell21.setHorizontalAlignment(Element.ALIGN_LEFT);
 		 cell21.setVerticalAlignment(Element.ALIGN_LEFT);
 		 cell21.setBorderColor(BaseColor.WHITE);
 		 table2.addCell(cell21);
 		
 		 document.add(table2);
 		 res= rv.showRevenuesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1),Integer.parseInt(ad.getText().substring(2, 4)));
 			
     		 
 		 PdfPTable table44 = new PdfPTable(3); 
 		table44.setWidthPercentage(90); 
 		table44.setSpacingBefore(10f);
 		table44.setSpacingAfter(10f);
 		 PdfPCell cell33 = new PdfPCell(new Paragraph("Révenues",catFont2));
 		 cell33.setPadding(10);
 		 cell33.setColspan(3);
 		 cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
 		 cell33.setVerticalAlignment(Element.ALIGN_CENTER);
 		table44.addCell(cell33);
 		PdfPCell cell34 = new PdfPCell(new Paragraph("Label ",catFont3));
		 cell34.setPadding(10);
		 cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
		 cell34.setVerticalAlignment(Element.ALIGN_CENTER);
		table44.addCell(cell34);
 		 PdfPCell cell31 = new PdfPCell(new Paragraph("Date de révenue",catFont3));
 		 cell31.setPadding(10);
 		 cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
 		 cell31.setVerticalAlignment(Element.ALIGN_CENTER);
 		table44.addCell(cell31);
 		 
 		 PdfPCell cell32 = new PdfPCell(new Paragraph("Montant",catFont3));
 		 cell32.setPadding(10);
 		 cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
 		 cell32.setVerticalAlignment(Element.ALIGN_CENTER);
 		table44.addCell(cell32);
 		 for (int i=0; i<res.size();i++) {
 			 
 			 PdfPCell cell43 = new PdfPCell(new Paragraph(""+res.elementAt(i).getLabel()));
     		 cell43.setPadding(10);
     		 cell43.setHorizontalAlignment(Element.ALIGN_CENTER);
     		 cell43.setVerticalAlignment(Element.ALIGN_CENTER);
     		table44.addCell(cell43);
     		PdfPCell cell41 = new PdfPCell(new Paragraph(""+res.elementAt(i).getDateR()));
     		 cell41.setPadding(10);
     		 cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
     		 cell41.setVerticalAlignment(Element.ALIGN_CENTER);
     		table44.addCell(cell41);
     		
 			 
 			 PdfPCell cell42 = new PdfPCell(new Paragraph(""+res.elementAt(i).getMontant()));
     		 cell42.setPadding(10);
 			 cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
 			 cell42.setVerticalAlignment(Element.ALIGN_CENTER);
 			table44.addCell(cell42);
 			}
 		 
 			 PdfPCell cell44 = new PdfPCell(new Paragraph("Total :",catFont3));			
 			 cell44.setPadding(10);
     		 cell44.setColspan(2);
 			 cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
 			 cell44.setVerticalAlignment(Element.ALIGN_CENTER);
 			table44.addCell(cell44);
     		 
     		 PdfPCell cell45 = new PdfPCell(new Paragraph(""+rv.totalRevenuesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1),Integer.parseInt(ad.getText().substring(2, 4)))));
     		 cell45.setPadding(10);
     		 cell45.setHorizontalAlignment(Element.ALIGN_CENTER);
     		 cell45.setVerticalAlignment(Element.ALIGN_CENTER);
     		table44.addCell(cell45);
    
 			 document.add(table44);
 			 
 			 res1= dp.showDepensesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1),Integer.parseInt(ad.getText().substring(2, 4)));

 			 PdfPTable table5 = new PdfPTable(3); 
 			 table5.setWidthPercentage(90); 
 			 table5.setSpacingBefore(10f);
 			 table5.setSpacingAfter(10f);
 			 PdfPCell cell51 = new PdfPCell(new Paragraph("Dépenses",catFont2));
 			 cell51.setPadding(10);
 			 cell51.setColspan(3);
 			 cell51.setHorizontalAlignment(Element.ALIGN_CENTER);
 			 cell51.setVerticalAlignment(Element.ALIGN_CENTER);
 			 table5.addCell(cell51);
 			PdfPCell cell554 = new PdfPCell(new Paragraph("Label",catFont3));
			 cell554.setPadding(10);
			 cell554.setHorizontalAlignment(Element.ALIGN_CENTER);
			 cell554.setVerticalAlignment(Element.ALIGN_CENTER);
			 table5.addCell(cell554);
 			 PdfPCell cell52 = new PdfPCell(new Paragraph("Date de dépense",catFont3));
 			 cell52.setPadding(10);
 			 cell52.setHorizontalAlignment(Element.ALIGN_CENTER);
 			 cell52.setVerticalAlignment(Element.ALIGN_CENTER);
 			 table5.addCell(cell52);
 			 PdfPCell cell53 = new PdfPCell(new Paragraph("Montant",catFont3));
 			 cell53.setPadding(10);
 			 cell53.setHorizontalAlignment(Element.ALIGN_CENTER);
 			 cell53.setVerticalAlignment(Element.ALIGN_CENTER);
 			 table5.addCell(cell53);
 			 for (int i=0; i<res1.size();i++) {
 				 PdfPCell cell455 = new PdfPCell(new Paragraph(""+res1.elementAt(i).getLabel()));
				 cell455.setPadding(10);
				 cell455.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell455.setVerticalAlignment(Element.ALIGN_CENTER);
				 table5.addCell(cell455);
 	    		 PdfPCell cell54 = new PdfPCell(new Paragraph(""+res1.elementAt(i).getDateD()));
 	    		 cell54.setPadding(10);
 	    		 cell54.setHorizontalAlignment(Element.ALIGN_CENTER);
 	    		 cell54.setVerticalAlignment(Element.ALIGN_CENTER);
 	    		 table5.addCell(cell54);
 	    		 PdfPCell cell55 = new PdfPCell(new Paragraph(""+res1.elementAt(i).getMontant()));
 	    		 cell55.setPadding(10);
 	    		 cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
 	    		 cell55.setVerticalAlignment(Element.ALIGN_CENTER);
 				 table5.addCell(cell55);
 				 
 			
 				}
 			 
 				 PdfPCell cell56 = new PdfPCell(new Paragraph("Total :",catFont3));			
 				 cell56.setPadding(10);
 				 cell56.setColspan(2);
 				 cell56.setHorizontalAlignment(Element.ALIGN_CENTER);
 				 cell56.setVerticalAlignment(Element.ALIGN_CENTER);
 				 table5.addCell(cell56);
 	    		 
 	    		 PdfPCell cell57 = new PdfPCell(new Paragraph(""+dp.totalDepensesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1),Integer.parseInt(ad.getText().substring(2, 4)))));
 	    		 cell57.setPadding(10);
 	    		 cell57.setHorizontalAlignment(Element.ALIGN_CENTER);
 	    		 cell57.setVerticalAlignment(Element.ALIGN_CENTER);
 	    		 table5.addCell(cell57);
 	   
 				 document.add(table5);
 				 
 				 PdfPTable table6 = new PdfPTable(1); 
				 table6.setWidthPercentage(90); 
				 table6.setSpacingBefore(10f);
				 table6.setSpacingAfter(10f);
				 int r=rv.totalRevenuesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1),Integer.parseInt(ad.getText().substring(2, 4)));
				 int d=dp.totalDepensesRapportMensuel((md.getSelectionModel().getSelectedIndex()+1),Integer.parseInt(ad.getText().substring(2, 4)));
				 PdfPCell cell61 = new PdfPCell(new Paragraph("Bilan: "+(r-d),catFont3));
				 cell61.setPadding(10);
				 cell61.setBorderColor(BaseColor.WHITE);
				 cell61.setHorizontalAlignment(Element.ALIGN_CENTER);
				 cell61.setVerticalAlignment(Element.ALIGN_CENTER);
				 table6.addCell(cell61);
			
				 document.add(table6);
         document.close();
    }
    

}
