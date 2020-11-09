package Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.ClientModel;
import Model.DepenseModel;
import Model.FormationModel;
import Model.InscriptionModel;
import Model.RevenueModel;
import Model.SessionModel;
import Service.ClientService;
import Service.DepenseService;
import Service.InscriptionService;
import Service.RevenueService;
import Service.SessionService;
import dbConnexion.Connexion;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class StatistiqueController implements Initializable{
	@FXML
    private BarChart<String, Integer> barChart;
	@FXML
    private BarChart<String, Integer> bar;
	@FXML
    private BarChart<String, Integer> bard;
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    @FXML
    private PieChart pie;
    @FXML
    private PieChart pieIns;
    
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    SessionService sess=new SessionService();
    ClientService clt=new ClientService();
    InscriptionService ins=new InscriptionService();
    RevenueService rev=new RevenueService();   
    DepenseService dep=new DepenseService();   
    private ObservableList data= FXCollections.observableArrayList();
    private ObservableList dataCI= FXCollections.observableArrayList();
    @FXML
    private Tab tabA;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] months = DateFormatSymbols.getInstance(Locale.FRANCE).getMonths();
        monthNames.addAll(Arrays.asList(months));
        x.setCategories(monthNames);
        setFormationData(sess.showSessions()) ;
        try {
			setRevenue();
			setDepense();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        showInscriptionList();
        try {
			buildData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        pie.getData().addAll(data);
         pie.getData().forEach(data -> {
            data.nameProperty().bind(
            		
                    Bindings.concat(  String.format("%.2f%%", ((data.getPieValue() * 100)/clt.showClientsTOTAL()))," : ", data.getName()
                    )
            );
        });
         
         
         
         try {
        	 clientInscription();
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        
         pieIns.getData().addAll(dataCI);
          pieIns.getData().forEach(dataCI -> {
        	  dataCI.nameProperty().bind(
             		
                     Bindings.concat(  dataCI.getPieValue()," : ", dataCI.getName()
                     )
             );
         });
       
	}
	
	public void setFormationData(List<SessionModel> formations) {
        int[] monthCounter = new int[12];
        
        for (SessionModel f : formations) {
            int month = Integer.parseInt(f.getDateDebut().toString().substring(5,7)) - 1;
            monthCounter[month]++;
        }
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
        	series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }        
        barChart.getData().add(series);
    }
	
	
	public void setRevenue() throws SQLException {
        List<RevenueModel> revv=rev.showRevenus();
        ResultSet res1;
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (RevenueModel f : revv) {
           res1=rev.show(f.getDateR().toString().substring(2,4));
    	    String a=f.getDateR().toString().substring(0,4);
           while(res1.next())
           {
          	series.getData().add(new XYChart.Data<>(a, res1.getInt("m")));
           }
       } 
             
        bar.getData().add(series);
      
    }
	
	public void setDepense() throws SQLException {
        List<DepenseModel> depenses=dep.showDepenses();
        ResultSet res1;
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (DepenseModel f : depenses) {
           res1=dep.show(f.getDateD().toString().substring(2,4));
    	    String a=f.getDateD().toString().substring(0,4);
           while(res1.next())
           {
          	series.getData().add(new XYChart.Data<>(a, res1.getInt("m")));
           }
       } 
             
        bard.getData().add(series);
      
    }
	
	

    public void buildData() throws SQLException{
    	data = FXCollections.observableArrayList();
    	ResultSet res  = clt.showClientsMetier();
		  while(res.next()){			 
			  data.addAll(new PieChart.Data(res.getString(2),res.getInt(1)));
            }
		  
      }
    

    public void clientInscription() throws SQLException{
    	dataCI = FXCollections.observableArrayList();
    	ResultSet res  = ins.showClientsIncrtiptions();
		  while(res.next()){			 
			  dataCI.addAll(new PieChart.Data(res.getString(2)+" "+res.getString(3),res.getInt(1)));
            }
      }
    @FXML
    private TableView<InscriptionModel> tableview;
    @FXML
    private TableColumn<InscriptionModel, String> clientCol;  
    @FXML
    private TableColumn<InscriptionModel, Integer> idCol;
    public void showInscriptionList(){
    	ObservableList<InscriptionModel> list = ins.showClientsIncrtiptionsTable();

    
    	idCol.setCellValueFactory(new PropertyValueFactory<>("countValue"));
    	clientCol.setCellValueFactory(new PropertyValueFactory<>("clientValue"));
		
		tableview.setItems(list);
		
    } 
	
	
	
}
