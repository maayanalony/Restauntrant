package view;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Component;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddComponentController {
	
	boolean editMode=false;
	boolean invalidNumber=false;
	
	@FXML private GridPane componentData= new GridPane();
	
	@FXML private Label lblcompID= new Label();
	@FXML private Label compID;
	@FXML private TextField compName;
	@FXML private RadioButton btnGluten;
    @FXML private RadioButton btnNoGluten;
    @FXML private RadioButton btnLactose;
    @FXML private RadioButton btnNoLactose;
    @FXML private TextField price;    
    @FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private HBox removebox;
    @FXML private Button btnRemove= new Button();
    @FXML private Label status= new Label(" ");
    @FXML private Label invalidName;
    @FXML private Label invalidGluten;
    @FXML private Label invalidLactose;
    @FXML private Label invalidPrice;
    
    public void initialize() {
    	if(ManagerController.chosenComp!=null) { // show exist component
    		componentData.setDisable(true);
    		btnEdit.setVisible(true);
    		btnRemove.setVisible(true);
    		removebox.setVisible(false);
    		btnSave.setText("שמירה");
    		btnSave.setVisible(false);
    		setData();
    	} else { // new component
    		componentData.setDisable(false);
    		btnEdit.setVisible(false);
    		btnRemove.setVisible(false);
    		removebox.setVisible(false);
    		btnSave.setText("הוספה");
    		btnSave.setVisible(true);
    		lblcompID.setVisible(false);
    		compID.setVisible(false);
    	}
	}
    
    private void setData() {
    	compID.setText(""+ManagerController.chosenComp.getId());
		compName.setText(ManagerController.chosenComp.getComponentName());
		if(ManagerController.chosenComp.isHasGluten()) {
			 btnGluten.setSelected(true); 
			 btnNoGluten.setSelected(false); 	
		} else {
			 btnGluten.setSelected(false); 
			 btnNoGluten.setSelected(true);
		}
		if(ManagerController.chosenComp.isHasLactose()) {
			 btnLactose.setSelected(true); 
			 btnNoLactose.setSelected(false); 	
		}else {
			 btnLactose.setSelected(false); 
			 btnNoLactose.setSelected(true);
		}
		price.setText(""+ManagerController.chosenComp.getPrice());

	}
    
    
    @FXML
    public void showRemove() {
    	removebox.setVisible(true);
    	
    }
    @FXML
    public void remove() throws IOException {
    	Restaurant.getInstance().removeComponent(ManagerController.chosenComp);
    	JOptionPane.showMessageDialog(null, "המרכיב "+ManagerController.chosenComp.getComponentName()+" נמחק בהצלחה");
    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
    }
    @FXML
    public void dontRemove() {
    	removebox.setVisible(false);
    }
    
    
    @FXML
    public void edit() {
    	componentData.setDisable(false);
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
    	
		   String inputName= compName.getText().toString();
		   if(inputName == null || inputName.isEmpty())  
			   invalidLabels.add(invalidName);
		   
		   boolean inputGluten = false;
		   if(btnGluten.isSelected())
		 		  inputGluten= true;
	       else if(btnNoGluten.isSelected())
	    	      inputGluten= false;
	       else
	    	   invalidLabels.add(invalidGluten);
		   
		   boolean inputLactose = false;
		   if(btnLactose.isSelected())
		 		  inputLactose= true;
	       else if(btnNoLactose.isSelected())
	    	      inputLactose= false;
	       else
	    	   invalidLabels.add(invalidLactose);
		  
	       String inputPriceString= price.getText().toString();
	       double inputPrice = 0;
	 	   if(inputPriceString == null ) {
	 		  invalidLabels.add(invalidPrice);
	 	   } else { 
	 		   try {
	 			  inputPrice=Double.parseDouble(inputPriceString);
	 		   } catch(NumberFormatException e) {
	 			  invalidLabels.add(invalidPrice);
	 			  invalidNumber= true;
	 		   }
	 	   }
	 	   
	 	   if(invalidLabels.isEmpty()) {
	 		   if(!editMode) {
		 			 Component newComp= new Component(inputName, inputLactose, inputGluten, inputPrice);
		 	    	 if(Restaurant.getInstance().addComponent(newComp)) {
		 	    		 JOptionPane.showMessageDialog(null, "המרכיב "+newComp.getComponentName()+" נוסף בהצלחה");
		 	    		 componentData.setDisable(false);
		 	    		 GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
		 	    	 }else {
		 	    		 JOptionPane.showMessageDialog(null, "ERR");
		 	    		 clear();
		 	    		 componentData.setDisable(true);
		 	    	 }
		 	    	 
	 		   } else { // editmode is true
		 			ManagerController.chosenComp.setComponentName(inputName);
		 			ManagerController.chosenComp.setHasGluten(inputGluten);
		 			ManagerController.chosenComp.setHasLactose(inputLactose);
		 			ManagerController.chosenComp.setPrice(inputPrice);
		 			componentData.setDisable(true);
		 	    	for(Label l: invalidLabels) 
				     	   l.setVisible(false);
		 	    	clear();
		 	    	JOptionPane.showMessageDialog(null, "הפרטים עודכנו בהצלחה");
		 	    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
	 		   }
	 		 
	 	   } else { // if not empty
	           status.setText("קיימים "+invalidLabels.size()+" שדות לא תקינים");
	           for(Label l: invalidLabels) {
	        	   l.setVisible(true);
	        	   l.setText("X");
	           }
	           if(invalidNumber) {
	        	   invalidPrice.setText("הוזן מספר לא תקין");
	           }
	       }
	 	  
    }

	private void clear() {
		compName.setText(null);
    	btnGluten.setSelected(false);
    	btnNoGluten.setSelected(false);
    	btnLactose.setSelected(false);
    	btnNoLactose.setSelected(false);
    	price.setText(null);
    	lblcompID.setVisible(false);
    	compID.setVisible(false);
    	removebox.setVisible(false);
	}
    
}
