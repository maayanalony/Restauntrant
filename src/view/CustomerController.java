package view;


import java.io.IOException;
import java.util.TreeSet;

import Model.Order;
import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class CustomerController {

	@FXML private BorderPane profileView= new BorderPane();
	@FXML private BorderPane menuOrBag= new BorderPane();
	@FXML private BorderPane shoppingView= new BorderPane();
	@FXML public BorderPane aboutView= new BorderPane();
	@FXML public Label welcome= new Label();
	
	//profile items
	private ObservableList<Order> ordersObserve = FXCollections.observableArrayList();
	@FXML private ListView<Order> ordersList;
	@FXML private Label status= new Label();
	
	// Menu buttons
	@FXML public Button btnBag;
	@FXML public Button btnMenu;

	@FXML
    public void initialize() {
		welcome.setText("שלום "+LoginController.user.getFirstName()+" "+LoginController.user.getLastName());	
    }
	
	@FXML
	public void openProfile() throws IOException  {
		GUIMain.setGeneralLayout(profileView, "/view/AddCustomer.fxml");
		loadReleventOrders();
	}
	
	private void loadReleventOrders() {  
    	ordersObserve.clear();
    	TreeSet<Order> ordersBycust= Restaurant.getInstance().getOrderByCustomer().get(LoginController.user);
     	if(ordersBycust == null) {
     		status.setText("אין הזמנות להצגה");
     		ordersList.setVisible(false);
     	} else {
     		ordersObserve.addAll(ordersBycust);
     		ordersList.setItems(ordersObserve);
     		ordersList.refresh();
     	}
     }
	
	@FXML
	public void openAbout() throws IOException  {
		GUIMain.setGeneralAccordionLayout(aboutView, "/view/About.fxml");
	}
	
	@FXML
	public void openMenu() throws IOException  {
		GUIMain.setGeneralLayout(shoppingView, "/view/Menu.fxml");
		if(btnMenu!=null &&  btnBag!=null) {
			btnMenu.setVisible(false);
			btnBag.setVisible(true);
		}
	}
	
	@FXML
	public void openShoppingBag() throws IOException {
		GUIMain.setGeneralLayout(shoppingView, "/view/ShoppingBag.fxml");
		if(btnMenu!=null &&  btnBag!=null) {
			btnMenu.setVisible(true);
			btnBag.setVisible(false);
		}
	}
	
	@FXML
	public void openEndPlaceOrder() throws IOException {
		GUIMain.setGeneralLayout(menuOrBag, "/view/EndPlaceOrder.fxml");
	}
	

	@FXML
	public void openAfterPlaceOrder() throws IOException {
		if(LoginController.manager) 
			GUIMain.setMainLayout("/view/ManagerLayout");
		else
			GUIMain.setMainLayout("/view/CustomerLayout.fxml");
	}
	
}
