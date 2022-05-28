package view;

import java.io.IOException;

import javax.swing.JOptionPane;

import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class EditDeliveryController {
	private ObservableList<Order> orderObserve = FXCollections.observableArrayList();

	boolean editMode=false;
	boolean invalidNumber=false;
	
	@FXML private GridPane deliveryData= new GridPane();
	@FXML private Label deliveryID;
	@FXML private Label area;
	@FXML private Label delType;
	@FXML private ListView<Order>orderList= new ListView<>();
	@FXML private Label delPerson;
	@FXML private CheckBox arrive;
	@FXML private Button btnEdit;
    @FXML private Button btnSave;
    @FXML private HBox removebox;
    @FXML private Button btnRemove= new Button();
	@FXML private Label status= new Label("");
	
	public void initialize() {
		if(ManagerController.chosenDelivery!=null) {
			loadOrders();
    		deliveryData.setDisable(true);
    		btnEdit.setVisible(true);
    		btnRemove.setVisible(true);
    		removebox.setVisible(false);
    		btnSave.setText("שמירה");
    		btnSave.setVisible(false);
    		setData();
		}
	}
	
	private void loadOrders() {
		orderObserve.clear();
		if(ManagerController.chosenDelivery instanceof RegularDelivery) {
			RegularDelivery rd= (RegularDelivery) ManagerController.chosenDelivery;
			orderObserve.addAll(rd.getOrders());
			delType.setText("רגיל");
		} else {
			ExpressDelivery ed= (ExpressDelivery) ManagerController.chosenDelivery;
			orderObserve.add(ed.getOrder());
			delType.setText("אקספרס");
		}
    	orderList.setItems(orderObserve);
    	orderList.refresh();
    }
	
    
    private void setData() {
    	deliveryID.setText(""+ManagerController.chosenDelivery.getId());
	    area.setText(ManagerController.chosenDelivery.getArea().getAreaName());
	    loadOrders();
	    delPerson.setText(ManagerController.chosenDelivery.getDeliveryPerson().getFirstName()
	    		+" "+ManagerController.chosenDelivery.getDeliveryPerson().getLastName());
	    if(ManagerController.chosenDelivery.isDelivered()) {
	    	arrive.setSelected(true);
	    	btnEdit.setVisible(false);
	    }
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
    public void arrived() throws IOException {
    	ManagerController.chosenDelivery.setDelivered(true);
    	Restaurant.getInstance().deliver(ManagerController.chosenDelivery);
    	arrive.setSelected(true);
    	JOptionPane.showMessageDialog(null, "השינוי בוצע");
    	GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
    }
    
    /*
    @FXML
    public void edit() {
    	deliveryData.setDisable(false);
    	btnSave.setVisible(true);
    	btnEdit.setVisible(false);
    	editMode= true;
    }
	
	
	@FXML
	private void addDishToOrder() {
		
	}
	
	@FXML
	private void removeDishFromOrder(ActionEvent event) 
	      
	}
	
	@FXML
	public void saveOrder() throws IOException {
		
	}*/
	
}
