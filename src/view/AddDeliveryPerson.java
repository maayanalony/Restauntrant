package view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Restaurant;
import Utils.Gender;
import Utils.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddDeliveryPerson {
	
	ObservableList<Vehicle> vehicleObserve = FXCollections.observableArrayList(Vehicle.values());
	ObservableList<DeliveryArea> areaObserve = FXCollections.observableArrayList(Restaurant.getInstance().getAreas().values());
	ObservableList<Month> monthObserve = FXCollections.observableArrayList(Month.values());
	ObservableList<Delivery> deliveryObserve = FXCollections.observableArrayList();
	  
	boolean editMode=false;
	
	@FXML private GridPane delPersonData= new GridPane();
	
	@FXML private Label lblDelPersonID= new Label();
	@FXML private Label delPersonID;
    @FXML private TextField fname;
    @FXML private TextField lname;
    @FXML private DatePicker birthDate;
    @FXML private RadioButton btnMale;
    @FXML private RadioButton btnFemale;
    @FXML private RadioButton btnUnKnown;
    @FXML private ComboBox<Vehicle> vehicle;
    @FXML private ComboBox<DeliveryArea> area;
    @FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private HBox removebox;
    @FXML private Button btnRemove= new Button();
    @FXML private Label status= new Label(" ");
    @FXML private Label invalidFN;
    @FXML private Label invalidLN;
    @FXML private Label invalidBD;
    @FXML private Label invalidGender;
    @FXML private Label invalidVehicle;
    @FXML private Label invalidArea;
    
    @FXML private VBox deliveriesByDelPerson= new VBox();
    @FXML private ComboBox<Month> month= new ComboBox<>();
    @FXML private ListView<Delivery> deliveryList= new ListView<>();
    
    
    public void initialize() {
    	vehicle.setItems(vehicleObserve);
    	month.setItems(monthObserve);
    	area.setItems(areaObserve);
    	if(ManagerController.chosenDeliveryPerson!=null) { // exist delivery person
    		delPersonData.setDisable(true);
    		btnEdit.setVisible(true);
    		btnRemove.setVisible(true);
    		removebox.setVisible(false);
    		btnSave.setText("שמירה");
    		btnSave.setVisible(false);
    		setData();
    		deliveriesByDelPerson.setVisible(true);
    	} else { // new delivery person
    		delPersonData.setDisable(false);
    		btnEdit.setVisible(false);
    		btnRemove.setVisible(false);
    		removebox.setVisible(false);
    		btnSave.setText("הוספה");
    		btnSave.setVisible(true);
    		lblDelPersonID.setVisible(false);
    		delPersonID.setVisible(false);
    		deliveriesByDelPerson.setVisible(false);
    	}
	}
    
    private void setData() {

    	System.out.println(ManagerController.chosenDeliveryPerson);
    	delPersonID.setText(""+ManagerController.chosenDeliveryPerson.getId());
		fname.setText(ManagerController.chosenDeliveryPerson.getFirstName());
		lname.setText(ManagerController.chosenDeliveryPerson.getLastName());
		birthDate.setValue(ManagerController.chosenDeliveryPerson.getBirthDay());
		if(ManagerController.chosenDeliveryPerson.getGender().equals(Gender.Female)) {
			 btnFemale.setSelected(true); 
			 btnMale.setSelected(false); 
			 btnUnKnown.setSelected(false);
		} else if(ManagerController.chosenDeliveryPerson.getGender().equals(Gender.Male)) {
			 btnFemale.setSelected(false); 
			 btnMale.setSelected(true); 
			 btnUnKnown.setSelected(false);
		} else if(ManagerController.chosenDeliveryPerson.getGender().equals(Gender.Unknown)) {
			 btnFemale.setSelected(false); 
			 btnMale.setSelected(false); 
			 btnUnKnown.setSelected(true);
		}
		vehicle.setValue(ManagerController.chosenDeliveryPerson.getVehicle());
		area.setValue(ManagerController.chosenDeliveryPerson.getArea());
    }
    @FXML
    public void showRemove() {
    	removebox.setVisible(true);
    	
    }
    @FXML
    public void remove() throws IOException {
    	Restaurant.getInstance().removeDeliveryPerson(ManagerController.chosenDeliveryPerson);
    	JOptionPane.showMessageDialog(null, "השליח "+ManagerController.chosenDeliveryPerson.getFirstName()+" "+ManagerController.chosenDeliveryPerson.getLastName()+" נמחק בהצלחה");
    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
    }
    @FXML
    public void dontRemove() {
    	removebox.setVisible(false);
    }
    
    
    @FXML
    public void edit() {
    	delPersonData.setDisable(false);
    	btnSave.setVisible(true);
    	btnEdit.setVisible(false);
    	editMode= true;
    	
    }
    
    static ArrayList<Label> invalidLabels= new ArrayList<>();
    @FXML
	public void add() throws IOException {
    	for(Label l: invalidLabels) {
	     	   l.setVisible(false);
	        }
		   invalidLabels.clear();
		   
		   String inputfn= fname.getText().toString();
		   if(inputfn == null || inputfn.isEmpty()) 
			   invalidLabels.add(invalidFN);
		   
	       String inputln= lname.getText().toString();
	 	   if(inputln == null || inputln.isEmpty())  
			   invalidLabels.add(invalidLN);
	 	   
	 	  LocalDate birthD= (birthDate.getValue());
	       if(birthD == null) 
			   invalidLabels.add(invalidBD);
	       
	       Gender inputGender = null;
	       if(btnFemale.isSelected())
		 		  inputGender= Gender.Female;
	       else if(btnMale.isSelected())
	 		  inputGender= Gender.Male;
	       else if(btnUnKnown.isSelected())
		 		  inputGender= Gender.Unknown;
	       else
			   invalidLabels.add(invalidGender);
	       
	       Vehicle inputVehicle= vehicle.getValue();
	       if(inputVehicle == null )
	    	   invalidLabels.add(invalidVehicle);
	       
	       DeliveryArea inputArea= area.getValue();
	       if(inputArea == null )
	    	   invalidLabels.add(invalidArea);

	       if(invalidLabels.isEmpty()) {
	    	   if(!editMode) {
		    	   DeliveryPerson newDP= new DeliveryPerson(inputfn, inputln, birthD, inputGender, inputVehicle, inputArea);
		    	   if (Restaurant.getInstance().addDeliveryPerson(newDP, inputArea)) {
		    		   JOptionPane.showMessageDialog(null, "השליח "+newDP.getFirstName()+" "+newDP.getLastName()+" נוסף בהצלחה");
		    		   delPersonData.setDisable(false);
		 	    	   GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
		    	   } else {
		    		     JOptionPane.showMessageDialog(null, "ERR");
		 	    		 delPersonData.setDisable(true);
		 	       }
	    	   } else { // editmode is true
		 			ManagerController.chosenDeliveryPerson.setFirstName(inputfn);
		 			ManagerController.chosenDeliveryPerson.setLastName(inputln);
		 			ManagerController.chosenDeliveryPerson.setBirthDay(birthD);
		 			ManagerController.chosenDeliveryPerson.setGender(inputGender);
		 			ManagerController.chosenDeliveryPerson.setVehicle(inputVehicle);
		 			ManagerController.chosenDeliveryPerson.setArea(inputArea);
		 			delPersonData.setDisable(true);
		 	    	for(Label l: invalidLabels) 
				     	   l.setVisible(false);
		 	    	
		 	    	JOptionPane.showMessageDialog(null, "הפרטים עודכנו בהצלחה");
		 	    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
	    	   }
 
	       } else { // if not empty
	           status.setText("קיימים "+invalidLabels.size()+" שדות לא תקינים");
	           for(Label l: invalidLabels) {
	        	   l.setVisible(true);
	        	   l.setText("X");
	           }
	       }
	 } // end of add
    
    @FXML
    private void showPersonsDelivery() {
    	deliveryObserve.clear();
    	for(Delivery d: Restaurant.getInstance().getDeliveriesByPerson(
    			ManagerController.chosenDeliveryPerson, month.getValue().getValue())) {
    		deliveryObserve.add(d);
    	}
    	deliveryList.setItems(deliveryObserve);
    }
}
    


