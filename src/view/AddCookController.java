package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Cook;
import Model.Restaurant;
import Utils.Expertise;
import Utils.Gender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddCookController {
	
	ObservableList<Expertise> expertiseObserve = FXCollections.observableArrayList(Expertise.values());
  
	boolean editMode=false;
	
	@FXML private GridPane cookData= new GridPane();
	
	@FXML private Label lblcookID= new Label();
	@FXML private Label cookID;
    @FXML private TextField fname;
    @FXML private TextField lname;
    @FXML private DatePicker birthDate;
    @FXML private RadioButton btnMale;
    @FXML private RadioButton btnFemale;
    @FXML private RadioButton btnUnknown= new RadioButton();
    @FXML private ComboBox<Expertise> expertise;
    @FXML private RadioButton btnShef;
    @FXML private RadioButton btnNotShef;
    @FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private HBox removebox;
    @FXML private Button btnRemove= new Button();
    @FXML private Label status= new Label(" ");
    @FXML private Label invalidFN;
    @FXML private Label invalidLN;
    @FXML private Label invalidBD;
    @FXML private Label invalidGender;
    @FXML private Label invalidExpert;
    @FXML private Label invalidShef;
    
    public void initialize() {
    	expertise.setItems(expertiseObserve);
    	if(ManagerController.chosenCook!=null) { // show exist cook
    		cookData.setDisable(true);
    		btnEdit.setVisible(true);
    		btnRemove.setVisible(true);
    		removebox.setVisible(false);
    		btnSave.setText("שמירה");
    		btnSave.setVisible(false);
    		setData();
    	} else { // new cook
    		cookData.setDisable(false);
    		btnEdit.setVisible(false);
    		btnRemove.setVisible(false);
    		removebox.setVisible(false);
    		btnSave.setText("הוספה");
    		btnSave.setVisible(true);
    		lblcookID.setVisible(false);
    		cookID.setVisible(false);
    	}
	}
    
    private void setData() {
    	cookID.setText(""+ManagerController.chosenCook.getId());
		fname.setText(ManagerController.chosenCook.getFirstName());
		lname.setText(ManagerController.chosenCook.getLastName());
		birthDate.setValue(ManagerController.chosenCook.getBirthDay());
		if(ManagerController.chosenCook.getGender().equals(Gender.Female)) {
			 btnFemale.setSelected(true); 
			 btnMale.setSelected(false); 
			 btnUnknown.setSelected(false);
		} else if(ManagerController.chosenCook.getGender().equals(Gender.Male)) {
			 btnFemale.setSelected(false); 
			 btnMale.setSelected(true); 
			 btnUnknown.setSelected(false);
		} else if(ManagerController.chosenCook.getGender().equals(Gender.Unknown)) {
			 btnFemale.setSelected(false); 
			 btnMale.setSelected(false); 
			 btnUnknown.setSelected(true);
		}
		expertise.setValue(ManagerController.chosenCook.getExpert());
		if(ManagerController.chosenCook.isChef()) {
			 btnShef.setSelected(true); 
			 btnNotShef.setSelected(false);
		} else if(!ManagerController.chosenCook.isChef()) {
			 btnShef.setSelected(false); 
			 btnNotShef.setSelected(true);
		}
    }
    @FXML
    public void showRemove() {
    	removebox.setVisible(true);
    	
    }
    @FXML
    public void remove() throws IOException {
    	Restaurant.getInstance().removeCook(ManagerController.chosenCook);
    	JOptionPane.showMessageDialog(null, "הטבח "+ManagerController.chosenCook.getFirstName()
    		+" "+ManagerController.chosenCook.getLastName()+" נמחק בהצלחה");
    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
    }
    @FXML
    public void dontRemove() {
    	removebox.setVisible(false);
    }
    
    
    @FXML
    public void edit() {
    	cookData.setDisable(false);
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
	       
	       Expertise inputExpertise= expertise.getValue();
	       if(inputExpertise == null )
	    	   invalidLabels.add(invalidExpert);
	       
	       boolean inputShef = false;
	       if(btnShef.isSelected())
		 		  inputShef= true;
	       else if(btnNotShef.isSelected())
	 		  inputShef= false;
	       else
	    	   invalidLabels.add(invalidShef);
	       
	       if(invalidLabels.isEmpty()) {
	    	   if(!editMode) {
	    		   Cook newCook= new Cook(inputfn, inputln, birthD, inputGender, inputExpertise, inputShef);
		    	   if (Restaurant.getInstance().addCook(newCook)) {
		    		   JOptionPane.showMessageDialog(null, "הטבח "+newCook.getFirstName()+" "+newCook.getLastName()+" נוסף בהצלחה");
		    		   cookData.setDisable(false);
		 	    	   GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
		    	   } else {
		    		     JOptionPane.showMessageDialog(null, "ERR");
		 	    		 cookData.setDisable(true);
		 	       }
	    	   } else { // editmode is true
		 			ManagerController.chosenCook.setFirstName(inputfn);
		 			ManagerController.chosenCook.setLastName(inputln);
		 			ManagerController.chosenCook.setBirthDay(birthD);
		 			ManagerController.chosenCook.setGender(inputGender);
		 			ManagerController.chosenCook.setExpert(inputExpertise);
		 			ManagerController.chosenCook.setChef(inputShef);
		 			cookData.setDisable(true);
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
    }
    
}
