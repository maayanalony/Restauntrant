package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Customer;
import Model.DeliveryArea;
import Model.Restaurant;
import Utils.Gender;
import Utils.Neighberhood;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddCustomerController3 {
	
		private ObservableList<Neighberhood> neighberhoodObserve = FXCollections.observableArrayList();
		
		Customer chosenCustomer;
		
		boolean editMode=false;
		boolean invalidOccupiedName=false;
		boolean invalidNotSame=false;
		
		@FXML private GridPane customerData= new GridPane();
		
		@FXML private Label lblcustomerID= new Label();
		@FXML private Label customerID;
	    @FXML private TextField fname;
	    @FXML private TextField lname;
	    @FXML private DatePicker birthDate;
	    @FXML private RadioButton btnMale;
	    @FXML private RadioButton btnFemale;
	    @FXML private RadioButton btnUnknown;
	    @FXML private CheckBox glutenBox;
	    @FXML private CheckBox lactoseBox;
	    @FXML private ComboBox<Neighberhood> neighberhood;
	    @FXML private TextField userName;
	    @FXML private PasswordField password;
	    @FXML private Label lblpasswordCheck= new Label();
	    @FXML private PasswordField passwordCheck;
	    

	    @FXML private Button btnEdit;
	    @FXML private Button btnSave;
	    @FXML private HBox removebox;
	    @FXML private Button btnRemove= new Button();
	    @FXML private Label status= new Label(" ");
	    @FXML private Label invalidFN;
	    @FXML private Label invalidLN;
	    @FXML private Label invalidBD;
	    @FXML private Label invalidGender;
	    @FXML private Label invalidNeighberhood;
	    @FXML private Label invalidUN;
	    @FXML private Label invalidPassword;
	    @FXML private Label invalidPasswordCheck;
	    @FXML private Button btnBackToLogin;
	    
	    @FXML private Label lblBlaclList= new Label();
	    @FXML private CheckBox blackListBox= new CheckBox();
	    
	    @FXML
		private void initialize() {
			loadNeighberhood();
			if(LoginController.manager || LoginController.user!=null) 
				// if add/ edit from manager or edit from customer
				btnBackToLogin.setVisible(false);
			if(ManagerController.chosenCustomer!=null) { // from manager view
				chosenCustomer= ManagerController.chosenCustomer;
				btnRemove.setVisible(true);
				lblBlaclList.setVisible(true);
				blackListBox.setVisible(true);
			} else if(LoginController.user!=null) { // from customer view
				chosenCustomer= LoginController.user;
				btnRemove.setVisible(false);
				lblBlaclList.setVisible(false);
				blackListBox.setVisible(false);
	    	}
			
			if(chosenCustomer!=null) { // show exist customer
	    		customerData.setDisable(true);
	    		btnEdit.setVisible(true);
	    		removebox.setVisible(false);
	    		btnSave.setText("שמירה");
	    		btnSave.setVisible(false);
	    		setData();
	    	} else { // new customer
	    		customerData.setDisable(false);
	    		btnEdit.setVisible(false);
	    		btnRemove.setVisible(false);
	    		removebox.setVisible(false);
	    		btnSave.setText("הוספה");
	    		btnSave.setVisible(true);
	    		lblcustomerID.setVisible(false);
	    		customerID.setVisible(false);
	    		lblBlaclList.setVisible(false);
				blackListBox.setVisible(false);
	    	}
		}	    
	    
	    private void setData() {
	    	lblpasswordCheck.setVisible(false);
	    	passwordCheck.setVisible(false);
	    	customerID.setText(""+chosenCustomer.getId());
			fname.setText(chosenCustomer.getFirstName());
			lname.setText(chosenCustomer.getLastName());
			birthDate.setValue(chosenCustomer.getBirthDay());
			if(chosenCustomer.getGender().equals(Gender.Female)) {
				 btnFemale.setSelected(true); 
				 btnMale.setSelected(false); 
				 btnUnknown.setSelected(false);
			} else if(chosenCustomer.getGender().equals(Gender.Male)) {
				 btnFemale.setSelected(false); 
				 btnMale.setSelected(true); 
				 btnUnknown.setSelected(false);
			} else if(chosenCustomer.getGender().equals(Gender.Unknown)) {
				 btnFemale.setSelected(false); 
				 btnMale.setSelected(false); 
				 btnUnknown.setSelected(true);
			}
			if(chosenCustomer.isSensitiveToGluten()) 
				 glutenBox.setSelected(true);
			else
				 glutenBox.setSelected(false);
			if(chosenCustomer.isSensitiveToLactose()) 
				 lactoseBox.setSelected(true);
			else
				 lactoseBox.setSelected(false);
			neighberhood.setValue(chosenCustomer.getNeighberhood());
			userName.setText(chosenCustomer.getUserName());
			password.setText(chosenCustomer.getPassword());
			if(Restaurant.getInstance().getBlackList().contains(chosenCustomer))
				blackListBox.setSelected(true);
			else
				blackListBox.setSelected(false);
	    }
	    @FXML
	    public void showRemove() {
	    	removebox.setVisible(true);
	    	
	    }
	    @FXML
	    public void remove() throws IOException {
	    	Restaurant.getInstance().removeCustomer(chosenCustomer);
	    	JOptionPane.showMessageDialog(null, "הלקוח "+chosenCustomer.getFirstName()+" "+chosenCustomer.getLastName()+" נמחק בהצלחה");
	    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
	    }
	    @FXML
	    public void dontRemove() {
	    	removebox.setVisible(false);
	    }
	    
	    
	    @FXML
	    public void edit() {
	    	customerData.setDisable(false);
	    	lblpasswordCheck.setVisible(true);
	    	passwordCheck.setVisible(true);
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
		       else if(btnUnknown.isSelected())
			 		  inputGender= Gender.Unknown;
		       else
				   invalidLabels.add(invalidGender);
	       
	       Neighberhood inputNeighberhood= neighberhood.getValue();
	       if(inputNeighberhood == null )
	    	   invalidLabels.add(invalidNeighberhood);
	       
	       boolean lactose= lactoseBox.isSelected();
	       boolean gluten= glutenBox.isSelected();
	 	   
	       String inputUser = userName.getText().toString();
	       if(inputUser == null || inputUser.isEmpty()) 
	    	   invalidLabels.add(invalidUN);
	       
	       else if(Restaurant.getInstance().getCustomers()!=null){
	    	   for(Customer c: Restaurant.getInstance().getCustomers().values()) {
	    		   if(c.getUserName().equals(inputUser)) {
	    			   if(!editMode || (editMode && !c.getUserName().equals(chosenCustomer.getUserName()))) {
	    				   invalidOccupiedName= true;
		    			   invalidLabels.add(invalidUN);
	    			   } 
	    		   }
	    		   
	    	   }
	    	   
	       }
	       String inputPw = password.getText().toString();
	       if(inputPw == null || inputPw.isEmpty()) 
	    	   invalidLabels.add(invalidPassword);
	    	String inputCheckPw = passwordCheck.getText().toString();
	  	    if(inputCheckPw == null || inputCheckPw.isEmpty()) 
	  	    	invalidLabels.add(invalidPasswordCheck);
	  	    else if(!inputCheckPw.equals(inputPw)) { 
	  	    	invalidNotSame= true;
	  	    	invalidLabels.add(invalidPasswordCheck);
	  	    }
	  	    
	       if(invalidLabels.isEmpty()) {
		    	   if(!editMode) {
		    		   Customer newCust= new Customer(inputfn, inputln, birthD, inputGender, 
		    				   inputNeighberhood, lactose, gluten, inputUser, inputPw);
			    	   if (Restaurant.getInstance().addCustomer(newCust)) {
			    		   if(!LoginController.manager && chosenCustomer==null) { 
			    			   // registration from login (customer view)
			    			   JOptionPane.showMessageDialog(null, "נוספת בהצלחה למערכת, כעת תועבר לאתר");
			    			   LoginController.user= newCust;
			    			   GUIMain.setMainLayout("/view/CustomerLayout.fxml");
			    		   } else {  // adding customer from manager view
			    			   blackList(newCust);
			    			   JOptionPane.showMessageDialog(null, "customer added");
			    			   GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
			    		   } 
			    	   } 
		    	   } else { // editmode is true
			 			chosenCustomer.setFirstName(inputfn);
			 			chosenCustomer.setLastName(inputln);
			 			chosenCustomer.setBirthDay(birthD);
			 			chosenCustomer.setGender(inputGender);
			 			chosenCustomer.setNeighberhood(inputNeighberhood);
			 			chosenCustomer.setSensitiveToGluten(gluten);
			 			chosenCustomer.setSensitiveToLactose(lactose);
			 			chosenCustomer.setUserName(inputUser);
			 			chosenCustomer.setPassword(inputPw);
			 			lblpasswordCheck.setVisible(false);
				    	passwordCheck.setVisible(false);
				    	customerData.setDisable(true);
				    	for(Label l: invalidLabels) 
					     	   l.setVisible(false);
			 	    	blackList(chosenCustomer);
			 	    	JOptionPane.showMessageDialog(null, "הפרטים עודכנו בהצלחה");
			 	    	if(LoginController.manager) 
			 	    		GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
			 	    	else
			 	    		GUIMain.setMainLayout("/view/CustomerLayout.fxml");
			 	    	
		       }
	     } else { // if not empty
	    	 
		           status.setText("קיימים "+invalidLabels.size()+" שדות לא תקינים");
		           for(Label l: invalidLabels) {
		        	   l.setVisible(true);
		        	   l.setText("X");
		           }
		           if(invalidOccupiedName) 
		        	   invalidUN.setText("שם משתמש זה תפוס \nאנא בחרו שם אחר");
		           if(invalidNotSame) 
		        	   invalidPasswordCheck.setText("הסיסמאות לא זהות");
		       }
	       	
	  }
	 	   
	
	   public void blackList(Customer cust) {
		   if(blackListBox.isSelected() && !Restaurant.getInstance().getBlackList().contains(cust)) {
	  	    	Restaurant.getInstance().addCustomerToBlackList(chosenCustomer);
				JOptionPane.showMessageDialog(null, "הלקוח "+chosenCustomer.getFirstName()+" "+chosenCustomer.getLastName()+" נוסף לרשימה השחורה");
	  	    } else if (!blackListBox.isSelected() && Restaurant.getInstance().getBlackList().contains(cust)) {
	  	    	Restaurant.getInstance().removeCustomerFromBlackList(cust);
	  	    }
	   }
	   
	   // only the neighborhoods that are in delivery areas and have delivery person will be shown
	   public void loadNeighberhood() {
		   neighberhoodObserve.clear();
		   for(DeliveryArea area: Restaurant.getInstance().getAreas().values()){
			   
			   if(area.getDelPersons()!=null && !area.getDelPersons().isEmpty()) 
				   neighberhoodObserve.addAll(area.getNeighberhoods());
		   }
		   neighberhood.setItems(neighberhoodObserve);
	   }
	   
		@FXML
	    private void clear() {
	    	fname.setText("");
	    	lname.setText("");
	    	btnFemale.setSelected(false);
	    	btnMale.setSelected(false);
	    	btnUnknown.setSelected(false);
	    	birthDate.setValue(null);
	    	glutenBox.setSelected(false);
	    	lactoseBox.setSelected(false);
	    	neighberhood.getSelectionModel().select(null);
	    	userName.setText("");
	    	password.setText("");
	    	passwordCheck.setText("");

	    }
		
		@FXML
	    private void returnToLogin(ActionEvent event) throws IOException {
			GUIMain.setSecLayout("/view/Login.fxml");
		}
			

}
