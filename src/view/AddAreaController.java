package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JOptionPane;

import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Restaurant;
import Utils.Neighberhood;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddAreaController {
	
	private ObservableList<Neighberhood> neighberhoodObserve = FXCollections.observableArrayList(Neighberhood.values());
	private ObservableList<String> neighberhoodsInAreasObserve = FXCollections.observableArrayList();
	private ObservableList<Neighberhood> chosenNeighberhoodObserve = FXCollections.observableArrayList();
	private ObservableList<DeliveryPerson> delPersonObserve = FXCollections.observableArrayList();
	
	private HashSet<Neighberhood> inputNeighberhood= new HashSet<>();
	boolean editMode=false;
	boolean invalidNumber=false;
	boolean invalidOccupiedName=false;
	
	@FXML private GridPane areaData= new GridPane();
	
	@FXML private Label lblareaID= new Label();
	@FXML private Label areaID;
	@FXML private TextField areaName;
    @FXML private ListView<Neighberhood> neighberhoodList;
    @FXML private ListView<String> neighberhoodsInAreasList= new ListView<>();
    @FXML private ListView<Neighberhood> chosenNeighberhoodList;
    @FXML private TextField deliveryTime;
    @FXML private ListView<DeliveryPerson> delPersonList= new ListView<>();
    @FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private HBox removebox;
    @FXML private Button btnRemove= new Button();
    @FXML private Label status= new Label(" ");
    @FXML private Label invalidName;
    @FXML private Label invalidNeighberhood;
    @FXML private Label invalidTime;
    
    public void initialize() {
	    neighberhoodList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	if(ManagerController.chosenDeliveryArea!=null) { // showing exist area
    		loadNeighberhoodWithChosen();
    		areaData.setDisable(true);
    		btnEdit.setVisible(true);
    		btnRemove.setVisible(true);
    		removebox.setVisible(false);
    		btnSave.setText("שמירה");
    		btnSave.setVisible(false);
    		delPersonList.setVisible(true);
    		loadDelPerson();
    		setData();
    	} else { // add a new area
    		loadNeighberhood();
    		areaData.setDisable(false);
    		btnEdit.setVisible(false);
    		btnRemove.setVisible(false);
    		removebox.setVisible(false);
    		btnSave.setText("הוספה");
    		btnSave.setVisible(true);
    		lblareaID.setVisible(false);
    		areaID.setVisible(false);
    		delPersonList.setVisible(false);
    	}
	}
    
    private void loadDelPerson() {
    	delPersonObserve.clear();
    	delPersonObserve.addAll(ManagerController.chosenDeliveryArea.getDelPersons());
    	delPersonList.getItems().addAll(delPersonObserve);
    	delPersonList.refresh();
    }
    
    /*
     *  each neighborhood can be in only one area
     *  the manager cannot choose neighborhood that belongs to area
     *  the manager can also see the neighborhoods that he cannot choose 
     */
    
    // new area
    private void loadNeighberhood() { 
		neighberhoodObserve.clear();
		neighberhoodsInAreasObserve.clear();
		chosenNeighberhoodObserve.clear();
		for(Neighberhood n: Neighberhood.values()) {
			String belongsToArea=isNeighberhoodInArea(n);
			if(belongsToArea!=null)
				neighberhoodsInAreasObserve.add(belongsToArea);
			else
				neighberhoodObserve.add(n);
				
		}
    	if(neighberhoodList==null)
    		System.out.println("neighberhoodList= NULL");
    	else {
    		neighberhoodList.getItems().addAll(neighberhoodObserve);
    		neighberhoodsInAreasList.getItems().addAll(neighberhoodsInAreasObserve);
    	}

    }
    
    // existing area
    private void loadNeighberhoodWithChosen() { 
		neighberhoodObserve.clear();
		neighberhoodsInAreasObserve.clear();
		chosenNeighberhoodObserve.clear();
		chosenNeighberhoodObserve.addAll(ManagerController.chosenDeliveryArea.getNeighberhoods());
		inputNeighberhood.addAll(ManagerController.chosenDeliveryArea.getNeighberhoods());
		for(Neighberhood n: Neighberhood.values()) {
			String belongsToArea=isNeighberhoodInArea(n);
			if(belongsToArea!=null) {
				 if(!belongsToArea.equals("_"))
					 neighberhoodsInAreasObserve.add(belongsToArea);
			}else
				neighberhoodObserve.add(n);
				
		}
    	if(neighberhoodList==null)
    		System.out.println("neighberhoodList= NULL");
    	else {
    		neighberhoodList.getItems().addAll(neighberhoodObserve);
    		neighberhoodsInAreasList.getItems().addAll(neighberhoodsInAreasObserve);
    		chosenNeighberhoodList.getItems().addAll(chosenNeighberhoodObserve);
    	}

    }
	
	public String isNeighberhoodInArea (Neighberhood n){
		for(DeliveryArea da: Restaurant.getInstance().getAreas().values()) {
			if(da.getNeighberhoods().contains(n)) {
				if(ManagerController.chosenDeliveryArea!=null && da.equals(ManagerController.chosenDeliveryArea))
					return "_";
				else
					return n.name()+" נמצאת באזור: "+da.getAreaName()+" (מספר מזהה: "+da.getId()+")";
			}
			
		}
		return null;
	}
	
    private void setData() {
    	areaID.setText(""+ManagerController.chosenDeliveryArea.getId());
		areaName.setText(ManagerController.chosenDeliveryArea.getAreaName());
		deliveryTime.setText(""+ManagerController.chosenDeliveryArea.getDeliverTime());

	}
    
    @FXML
    public void showRemove() {
    	removebox.setVisible(true);
    	Restaurant.getInstance().getDishes().remove(ManagerController.chosenDeliveryArea.getId());
    }
    @FXML
    public void remove() throws IOException {
    	Restaurant.getInstance().getDishes().remove(ManagerController.chosenDeliveryArea.getId());
    	JOptionPane.showMessageDialog(null, "האזור "+
    			ManagerController.chosenDeliveryArea.getAreaName()+" נמחק בהצלחה");
    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
    }
    @FXML
    public void dontRemove() {
    	removebox.setVisible(false);
    }
    
    @FXML
    public void edit() {
    	areaData.setDisable(false);
    	btnSave.setVisible(true);
    	btnEdit.setVisible(false);
    	delPersonList.setVisible(false);
    	editMode= true;
    }
	
	static ArrayList<Label> invalidLabels= new ArrayList<>();
    @FXML
	public void add() throws IOException {
    	
	    	for(Label l: invalidLabels) {
	     	   l.setVisible(false);
	        }
		   invalidLabels.clear();
	    	
		   String inputName= areaName.getText().toString();
		   if(inputName == null || inputName.isEmpty()) { System.out.println("HERE");
			   invalidLabels.add(invalidName);
		   } else if(Restaurant.getInstance().getAreas()!=null){
	    	   for(DeliveryArea a: Restaurant.getInstance().getAreas().values()) {
	    		   if(!editMode && a.getAreaName().equals(inputName)) {
	    			   invalidOccupiedName=true;
	    			   invalidLabels.add(invalidName);
	    		   }
	    	   }
	    	   
	       }
		   
		   String inputTimeString= deliveryTime.getText().toString();
	       int inputTime = 0;
		   if(inputTimeString == null || inputTimeString.isEmpty())  
			   invalidLabels.add(invalidTime);
		   else {
			   try {
		 			  inputTime=Integer.parseInt(inputTimeString);
		 		   } catch(NumberFormatException e) {
		 			  invalidLabels.add(invalidTime);
		 			  invalidNumber= true;
		 		   }
		   }
		   
		   if(inputNeighberhood.isEmpty()){ 
			   invalidLabels.add(invalidNeighberhood);
		   }
		   
		   if(invalidLabels.isEmpty()) {
			   if(!editMode) {
				   DeliveryArea newArea= new DeliveryArea(inputName, inputNeighberhood, inputTime);
			    	 if(Restaurant.getInstance().addDeliveryArea(newArea)) {
			    		 JOptionPane.showMessageDialog(null, "Area added successfuly");
			    		 GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
			    	 }else {
			    		 JOptionPane.showMessageDialog(null, "ERR");
			    		 
			    	 }
	    	   } else {
	    		   ManagerController.chosenDeliveryArea.setAreaName(inputName);
	    		   areaData.setDisable(true);
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
	           if(invalidNumber) 
	        	   invalidTime.setText("הוזן מספר לא תקין");
	           if(invalidOccupiedName) 
	        	   invalidName.setText("שם זה שייך לאזור הנמצא במערכת");
	       }
	 	  
	}
    
	@FXML
	private void addNeighberhood() {
		Neighberhood potential = neighberhoodList.getSelectionModel().getSelectedItem();
		String cantBeChoose= neighberhoodsInAreasList.getSelectionModel().getSelectedItem();
		if(cantBeChoose!=null)
			JOptionPane.showMessageDialog(null, "לא ניתן לבחור בשכונה אחת לשני אזורים "
					+ "\nשכונה זו כבר שייכת לאזור אחר");
		if (potential != null) {
		    	neighberhoodList.getSelectionModel().clearSelection();
		    	neighberhoodObserve.remove(potential);
		        chosenNeighberhoodObserve.add(potential);
		        chosenNeighberhoodList.setItems(chosenNeighberhoodObserve);
		        neighberhoodList.setItems(neighberhoodObserve);
		        inputNeighberhood.add(potential);
		        if(editMode)
		        	ManagerController.chosenDeliveryArea.addNeighberhood(potential);
		}
	}
	
	@FXML
	private void removeNeighberhood(ActionEvent event) {
		Neighberhood s = chosenNeighberhoodList.getSelectionModel().getSelectedItem();
	      if (s != null) {
	    	  chosenNeighberhoodList.getSelectionModel().clearSelection();
	          chosenNeighberhoodObserve.remove(s);
	          neighberhoodObserve.add(s);
	          inputNeighberhood.remove(s);
	          if(editMode)
		        	ManagerController.chosenDeliveryArea.removeNeighberhood(s);
	      }
	      
	}
    
	

}
