package view;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Component;
import Model.Dish;
import Model.Restaurant;
import Utils.DishType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddDishController2 {

	private ObservableList<DishType> typeList = FXCollections.observableArrayList(DishType.values());
	private ObservableList<Component> componentObserve = FXCollections.observableArrayList();
	private ObservableList<Component> chosenCompObserve = FXCollections.observableArrayList();
    
	private ArrayList<Component> componenets= new ArrayList<>();
	
	boolean editMode=false;
	boolean invalidNumber=false;
	
	@FXML private GridPane dishData= new GridPane();
	@FXML private Label lbldishID= new Label();
	@FXML private Label dishID;
	@FXML private TextField dishName;
	@FXML private ComboBox <DishType> type;
	@FXML private ListView<Component> componentList= new ListView<>();
	@FXML private ListView<Component> chosenCompList= new ListView<>();
	@FXML private TextField timeToMake;
	@FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private HBox removebox;
    @FXML private Button btnRemove= new Button();
	@FXML private Label status= new Label("");
	@FXML private Label invalidName;
	@FXML private Label invalidType;
	@FXML private Label invalidComponents;
	@FXML private Label invalidTime;
	
	public void initialize() {
		type.setItems(typeList);
		componentList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	if(ManagerController.chosenDish!=null) { // exist dish
    		loadCompWithChosen();
    		dishData.setDisable(true);
    		btnEdit.setVisible(true);
    		btnRemove.setVisible(true);
    		removebox.setVisible(false);
    		btnSave.setText("שמירה");
    		btnSave.setVisible(false);
    		setData();
    	} else { // new dish
    		loadComponents();
    		dishData.setDisable(false);
    		btnEdit.setVisible(false);
    		btnRemove.setVisible(false);
    		removebox.setVisible(false);
    		btnSave.setText("הוספה");
    		btnSave.setVisible(true);
    		lbldishID.setVisible(false);
    		dishID.setVisible(false);
    	}
	}
	
	// separate between components that belongs to exist dish and to the others
	private void loadCompWithChosen() {
		componentObserve.clear();
		chosenCompObserve.clear();
    	for(Component comp: Restaurant.getInstance().getComponenets().values()) {
    		if(ManagerController.chosenDish.getComponenets().contains(comp)) {
    			chosenCompObserve.add(comp);
    			componenets.add(comp);
    		} else
    			componentObserve.add(comp);
    	}
    	componentList.getItems().addAll(componentObserve);
    	chosenCompList.getItems().addAll(chosenCompObserve);
    }
	
	// load all components
	private void loadComponents() {
		componentObserve.clear();
		chosenCompObserve.clear();
		componentObserve.addAll(Restaurant.getInstance().getComponenets().values());
		componentList.getItems().addAll(componentObserve);
    	chosenCompList.getItems().addAll(chosenCompObserve);
	}
    
    private void setData() {
    	dishID.setText(""+ManagerController.chosenDish.getId());
		dishName.setText(ManagerController.chosenDish.getDishName());
		type.setValue(ManagerController.chosenDish.getType());
		timeToMake.setText(""+ManagerController.chosenDish.getTimeToMake());

	}
    
    @FXML
    public void showRemove() {
    	removebox.setVisible(true);
    }
    @FXML
    public void remove() throws IOException {
    	Restaurant.getInstance().getDishes().remove(ManagerController.chosenDish.getId());
    	JOptionPane.showMessageDialog(null, "המרכיב "+ManagerController.chosenDish.getDishName()+" נמחק בהצלחה");
    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
    }
    @FXML
    public void dontRemove() {
    	removebox.setVisible(false);
    }
    
    @FXML
    public void edit() {
    	dishData.setDisable(false);
    	btnSave.setVisible(true);
    	btnEdit.setVisible(false);
    	editMode= true;
    }
	
	static ArrayList<Label> invalidLabels= new ArrayList<>();
	@FXML
	public void addDish() throws IOException {
		   
		   for(Label l: invalidLabels) {
	     	   l.setVisible(false);
	        }
		   invalidLabels.clear();
		   
		   String inputName= dishName.getText().toString();
		   if(inputName == null || inputName.isEmpty()) 
			   invalidLabels.add(invalidName);
		   
		   DishType inputType= type.getValue();
	       if(inputType == null )
	    	   invalidLabels.add(invalidType);
	       
	       String inputTimeString= timeToMake.getText().toString();
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
		   
		   if(componenets.isEmpty())
			   invalidLabels.add(invalidComponents);
	       
	       
	       if(invalidLabels.isEmpty()) {
	    	   if(!editMode) {
	    		   Dish newDish= new Dish(inputName, inputType, componenets, inputTime);
			    	 if(Restaurant.getInstance().addDish(newDish)) {
			    		 JOptionPane.showMessageDialog(null, "Dish added successfuly");
			    		 GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
			    	 }else {
			    		 JOptionPane.showMessageDialog(null, "ERR");
			    		 
			    	 }
	    	   } else {
	    		   ManagerController.chosenDish.setDishName(inputName);
	    		   ManagerController.chosenDish.setType(inputType);
	    		   ManagerController.chosenDish.setTimeToMake(inputTime);
	    		   dishData.setDisable(true);
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
	           if(invalidNumber) {
	        	   invalidTime.setText("הוזן מספר לא תקין");
	           }
	       }
	 	  
	}

	@FXML
	private void addCompToDish() {
		Component potential = componentList.getSelectionModel().getSelectedItem();
		if (potential != null) {
	        componentList.getSelectionModel().clearSelection();
	        componentObserve.remove(potential);
	        chosenCompObserve.add(potential);
	        chosenCompList.setItems(chosenCompObserve);
	        componentList.setItems(componentObserve);
	        componenets.add(potential);
	        if(editMode)
	        	ManagerController.chosenDish.addComponent(potential);
	      }      
	}
	
	@FXML
	private void removeCompFromDish(ActionEvent event) {
		Component s = chosenCompList.getSelectionModel().getSelectedItem();
	      if (s != null) {
	    	  chosenCompList.getSelectionModel().clearSelection();
	          chosenCompObserve.remove(s);
	          componentObserve.add(s);
	          chosenCompList.setItems(chosenCompObserve);
		      componentList.setItems(componentObserve);
	          componenets.remove(s);
	          if(editMode)
		        	ManagerController.chosenDish.removeComponent(s);
	      }
	      
	}

}

