package view;


import java.io.IOException;
import java.util.HashSet;

import javax.swing.JOptionPane;

import Exceptions.NothingSelectedExceptions;
import Model.Customer;
import Model.DeliveryArea;
import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ManagerAddDelController {
	
	@FXML
	private BorderPane managerAddDelAndorder= new BorderPane();
	
	ObservableList<DeliveryArea> areaObserve = FXCollections.observableArrayList(Restaurant.getInstance().getAreas().values());
	private ObservableList<Customer> customerObserve = FXCollections.observableArrayList();
	

	@FXML private ListView<Customer> customerList= new ListView<>();
	@FXML private ComboBox<DeliveryArea> delArea= new ComboBox<>();
	@FXML private Label status= new Label();
	@FXML private Label status2= new Label();
	@FXML private Label customerNow= new Label();
	private int empty=0;
	@FXML private Button btnStartOrder= new Button();
	@FXML private Button btnShoppingBag= new Button();
	
	public void initialize() {
		delArea.setItems(areaObserve);
		btnShoppingBag.setVisible(false);
	}
		
	@FXML
	private void showReleventCustomers() {

	    DeliveryArea inputArea= delArea.getValue();
	    if(inputArea == null)
	    	empty++;
	    
	    if(empty!=0) {
	    	status.setText("לא נבחרו כל השדות הדרושים");
	    	empty=0;
	    } else {
	    	loadReleventCustomers(inputArea);
	    }
	}
	
	private void loadReleventCustomers(DeliveryArea da) {
		status2.setText(" ");
		customerObserve.clear();
    	HashSet<Customer> cust= Restaurant.getInstance().getCustomersByDeliveryArea().get(da);
    	if(cust==null) {
    		status2.setText("אין לקוחות באזור המשלוחים "+da.getAreaName());
    	} else {
    		customerObserve.addAll(cust);
	    	customerList.setItems(customerObserve);
	    	customerList.refresh();
    	}
	}
	
	
	@FXML
	private void startOrders() throws NothingSelectedExceptions {
		try {
			ManagerController.chosenCustomer= customerList.getSelectionModel().getSelectedItem();
			if(ManagerController.chosenCustomer== null) {
				throw new NothingSelectedExceptions(); 
			} 
			btnStartOrder.setVisible(false);
			btnShoppingBag.setVisible(true);
			customerNow.setText("הזמנה עבור הלקוח: "+ManagerController.chosenCustomer.getFirstName()
				+" "+ManagerController.chosenCustomer.getLastName()+" (מזהה לקוח: "+ManagerController.chosenCustomer.getId()+")");
			GUIMain.setGeneralLayout(managerAddDelAndorder, "/view/Menu.fxml");
		} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
		
	}
	
	@FXML
	private void showShoppingBag() throws IOException {
		GUIMain.setGeneralLayout(managerAddDelAndorder, "/view/ShoppingBag.fxml");
		btnShoppingBag.setVisible(false);
	}
	

}
