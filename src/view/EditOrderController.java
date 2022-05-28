package view;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Model.Dish;
import Model.Restaurant;
import Utils.Neighberhood;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EditOrderController {
	private ObservableList<Dish> dishObserve = FXCollections.observableArrayList(Restaurant.getInstance().getDishes().values());
	private ObservableList<Dish> chosenDishObserve = FXCollections.observableArrayList();

	private ArrayList<Dish> dishes= new ArrayList<>();
	
	boolean editMode=false;
	boolean invalidNumber=false;
	
	@FXML private GridPane orderData= new GridPane();
	@FXML private Label orderID;
	@FXML private Label customer;
	@FXML private ListView<Dish> dishList= new ListView<>();
	@FXML private ListView<Dish> chosenDishList= new ListView<>();
	@FXML private Label deliveryNum;
	@FXML private Label neighberhood;
	@FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private HBox removebox;
    @FXML private Button btnRemove= new Button();
	@FXML private Label status= new Label("");
	
	public void initialize() {
		if(ManagerController.chosenOrder!=null) { // exist order
			loadDishWithChosen();
    		orderData.setDisable(true);
    		btnEdit.setVisible(true);
    		btnRemove.setVisible(true);
    		removebox.setVisible(false);
    		btnSave.setText("שמירה");
    		btnSave.setVisible(false);
    		setData();
		}
	}
	
	private void loadDishWithChosen() {
		dishObserve.clear();
		chosenDishObserve.clear();
    	for(Dish d: Restaurant.getInstance().getDishes().values()) {
    		dishObserve.add(d);
    		if(ManagerController.chosenOrder.getDishes().contains(d)) {
    			chosenDishObserve.add(d);
    			dishes.add(d);
    		} 
    	}
    	dishList.getItems().addAll(dishObserve);
    	chosenDishList.getItems().addAll(chosenDishObserve);
    }
	
    
    private void setData() {
    	orderID.setText(""+ManagerController.chosenOrder.getId());
	    customer.setText(ManagerController.chosenOrder.getCustomer().getFirstName()
	    		+" "+ManagerController.chosenOrder.getCustomer().getLastName());
	    deliveryNum.setText(""+ManagerController.chosenOrder.getId());
	    Neighberhood n= ManagerController.chosenOrder.getCustomer().getNeighberhood();
	    neighberhood.setText(n+" באזור: "+Restaurant.getInstance().findArea(n));

	}
    
    @FXML
    public void showRemove() {
    	removebox.setVisible(true);
    }
    @FXML
    public void remove() throws IOException {
    	Restaurant.getInstance().removeOrder(ManagerController.chosenOrder);
    	JOptionPane.showMessageDialog(null, "ההזמנה "+ManagerController.chosenOrder+" נמחק בהצלחה");
    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
    }
    @FXML
    public void dontRemove() {
    	removebox.setVisible(false);
    }
    
    @FXML
    public void edit() {
    	orderData.setDisable(false);
    	btnSave.setVisible(true);
    	btnEdit.setVisible(false);
    	editMode= true;
    }
	
	
	@FXML
	private void addDishToOrder() {
		Dish potential = dishList.getSelectionModel().getSelectedItem();
		if (potential != null) {
	        dishList.getSelectionModel().clearSelection();
	        dishObserve.remove(potential);
	        chosenDishObserve.add(potential);
	        chosenDishList.setItems(chosenDishObserve);
	        dishList.setItems(dishObserve);
	        dishes.add(potential);
	        if(editMode)
	        	ManagerController.chosenOrder.addDish(potential);
	      }      
	}
	
	@FXML
	private void removeDishFromOrder(ActionEvent event) {
		Dish s = chosenDishList.getSelectionModel().getSelectedItem();
	      if (s != null) {
	    	  chosenDishList.getSelectionModel().clearSelection();
	          chosenDishObserve.remove(s);
	          dishObserve.add(s);
	          chosenDishList.setItems(chosenDishObserve);
		      dishList.setItems(dishObserve);
		      dishes.remove(s);
	          if(editMode)
		        	ManagerController.chosenOrder.removeDish(s);
	      }
	      
	}
	
	@FXML
	public void saveOrder() throws IOException {
		if(dishes.isEmpty()) {
			status.setText("יש לבחור מנות או למחוק את ההזמנה");
			return;
		} else {
			JOptionPane.showMessageDialog(null, "הפרטים עודכנו בהצלחה");
 	    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
		}
	}
	
}
