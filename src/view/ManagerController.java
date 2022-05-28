package view;



import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import Exceptions.NothingSelectedExceptions;
import Model.*;
import Utils.Expertise;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;

public class ManagerController implements Initializable{

	
	public ObservableList<Component> componentObserve = FXCollections.observableArrayList();
	private ObservableList<Dish> dishObserve = FXCollections.observableArrayList();
	private ObservableList<Cook> cookObserve = FXCollections.observableArrayList();
	private ObservableList<Expertise> expertiseObserve = FXCollections.observableArrayList(Expertise.values());
	private ObservableList<DeliveryPerson> deliveryPersonObserve = FXCollections.observableArrayList();
	private ObservableList<DeliveryArea> areaObserve = FXCollections.observableArrayList();
	private ObservableList<Customer> customerObserve = FXCollections.observableArrayList();
	private ObservableList<Order> orderObserve = FXCollections.observableArrayList();
	private ObservableList<Delivery> deliveryObserve = FXCollections.observableArrayList();
	
      
    @FXML public ListView<Component> componentList;
    @FXML private ListView<Dish> dishList;
    @FXML private ListView<Cook> cookList;
    @FXML private ListView<DeliveryPerson> deliveryPersonList;
    @FXML private ListView<DeliveryArea> areaList;
    @FXML private ListView<Customer> customerList; 
    @FXML private ListView<Order> orderList;
    @FXML private ListView<Delivery> deliveryList;
    
    
    @FXML public BorderPane componentPane;
    @FXML public BorderPane dishPane;
    @FXML public BorderPane cookPane;
    @FXML public BorderPane deliveryPersonPane;
    @FXML public BorderPane customerPane;
    @FXML public BorderPane areaPane;
    @FXML public BorderPane orderPane;
    @FXML public BorderPane deliveryPane;
    @FXML public BorderPane orderAndDeliveryPane;
    
    @FXML private RadioButton byExpert= new RadioButton();
    @FXML private ComboBox <Expertise> expertises= new ComboBox<>();
    
    @FXML private Label regularNum= new Label();
    @FXML private Label expressNum= new Label();
    @FXML private Label expressRevenue= new Label();
    
    public static Component chosenComp;
    public static Dish chosenDish;
    public static Cook chosenCook;
    public static DeliveryArea chosenDeliveryArea;
    public static DeliveryPerson chosenDeliveryPerson;
    public static Customer chosenCustomer;
    public static Order chosenOrder;
    public static Delivery chosenDelivery;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	loadComponents();
    	loadCustomers();
    	loadDishes();
    	loadCook();
    	loadDeliveryPerson();
    	loadDeliveryArea();
    	loadOrder();
    	loadDelivery();
    	expertises.setItems(expertiseObserve);
    	expertises.setVisible(false);
	}
    
    @FXML
    public void loadComponents() {  
    	closeComp();
    	componentObserve.clear();
    	componentObserve.addAll(Restaurant.getInstance().getComponenets().values());
    	componentList.setItems(componentObserve);
    	componentList.refresh();
    }
    
    @FXML
    public void openComp() throws IOException {
    	chosenComp= componentList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenComp!=null) {
    			GUIMain.setGeneralLayout(componentPane, "/view/AddComponent.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 

    }
    @FXML
    public void addComp() throws IOException {
    	chosenComp= null;
    	GUIMain.setGeneralLayout(componentPane, "/view/AddComponent.fxml");
    	loadComponents();
    }
    @FXML
    private void filterByPopular() {
    	componentObserve.clear();
    	componentObserve.addAll(Restaurant.getInstance().getPopularComponents());
    	componentList.setItems(componentObserve);
    	componentList.refresh();
    }
    @FXML
    private void closeComp()  {
    	GUIMain.setGeneralLayout(componentPane, "/view/EmptyPane.fxml");
    }
    
    @FXML
    private void loadDishes() {  
    	closeDish();
    	dishObserve.clear();
    	dishObserve.addAll(Restaurant.getInstance().getDishes().values());
    	dishList.setItems(dishObserve);
    	dishList.refresh();
    	
    }
    
    
    @FXML
    public void openDish() throws IOException {
    	chosenDish= dishList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenDish!=null) {
    			GUIMain.setGeneralLayout(dishPane, "/view/AddDish.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
    }
    @FXML
    public void addDish() throws IOException {
    	chosenDish= null;
    	GUIMain.setGeneralLayout(dishPane, "/view/AddDish.fxml");
    }
    @FXML
    private void closeDish()  {
    	GUIMain.setGeneralLayout(dishPane, "/view/EmptyPane.fxml");
    }
    
    @FXML
    private void filterByProfit() {
    	 dishObserve.clear();
    	 TreeSet<Dish> dishes= Restaurant.getInstance().getProfitRelation();
		 dishObserve.addAll(dishes);
		 dishList.setItems(dishObserve);
		 dishList.refresh();
    }

    @FXML
    private void loadCook() {  
    	closeCook();
    	expertises.setVisible(false);
 	    cookObserve.clear();
 	    cookObserve.addAll(Restaurant.getInstance().getCooks().values());
 	    cookList.setItems(cookObserve);
 	    cookList.refresh();
     }
    
    
    @FXML
    public void openCook() throws IOException {
    	chosenCook= cookList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenCook!=null) {
    			GUIMain.setGeneralLayout(cookPane, "/view/AddCook.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
    }
    @FXML
    public void addCook() throws IOException {
    	chosenCook= null;
    	GUIMain.setGeneralLayout(cookPane, "/view/AddCook.fxml");
    }
    @FXML
    private void closeCook()  {
    	GUIMain.setGeneralLayout(cookPane, "/view/EmptyPane.fxml");
    }
    
    @FXML
    private void showExpertBox() {
    	if(byExpert.isSelected())
    		expertises.setVisible(true);
    }
    
    @FXML
    public void filterCookByExpert() {
		 cookObserve.clear();
		 Expertise inputExpertise= expertises.getValue();
		 ArrayList<Cook> cooks= Restaurant.getInstance().GetCooksByExpertise(inputExpertise);
		 cookObserve.addAll(cooks);
		 cookList.setItems(cookObserve);
		 cookList.refresh();
	}
    
    @FXML
    private void loadDeliveryArea() {  
    	closeArea();
    	areaObserve.clear();
     	areaObserve.addAll(Restaurant.getInstance().getAreas().values());
     	areaList.setItems(areaObserve);
     }
    
    
    @FXML
    public void openDeliveryArea() throws IOException {
    	chosenDeliveryArea= areaList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenDeliveryArea!=null) {
    			GUIMain.setGeneralLayout(areaPane, "/view/AddArea.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
    }
    @FXML
    public void addDeliveryArea() throws IOException {
    	chosenDeliveryArea= null;
    	GUIMain.setGeneralLayout(areaPane, "/view/AddArea.fxml");
    }
    @FXML
    private void closeArea()  {
    	GUIMain.setGeneralLayout(areaPane, "/view/EmptyPane.fxml");
    }
     
    @FXML
    private void loadDeliveryPerson() { 
    	closeDelPerson();
    	deliveryPersonObserve.clear();
    	deliveryPersonObserve.addAll(Restaurant.getInstance().getDeliveryPersons().values());
     	deliveryPersonList.setItems(deliveryPersonObserve);
     }
    
    
    @FXML
    public void openDeliveryPerson() throws IOException {
    	chosenDeliveryPerson= deliveryPersonList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenDeliveryPerson!=null) {    			
    			GUIMain.setGeneralLayout(deliveryPersonPane, "/view/AddDeliveryPerson.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
    }
    @FXML
    public void addDeliveryPerson() throws IOException {
    	chosenDeliveryPerson= null;
    	GUIMain.setGeneralLayout(deliveryPersonPane, "/view/AddDeliveryPerson.fxml");
    }
    @FXML
    private void closeDelPerson()  {
    	GUIMain.setGeneralLayout(deliveryPersonPane, "/view/EmptyPane.fxml");
    }
   
    @FXML
    private void loadCustomers() {  
    	closeCustomer();
    	customerObserve.clear();
    	customerObserve.addAll(Restaurant.getInstance().getCustomers().values());
    	customerList.setItems(customerObserve);
    	customerList.refresh();
    }
    
    
    @FXML
    public void openCustomer() throws IOException {
    	chosenCustomer= customerList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenCustomer!=null) {    			
    			GUIMain.setGeneralLayout(customerPane, "/view/AddCustomer.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
    }
    @FXML
    public void addCustomer() throws IOException {
    	chosenCustomer= null;
    	GUIMain.setGeneralLayout(customerPane, "/view/AddCustomer.fxml");
    }
    @FXML
    private void closeCustomer()  {
    	GUIMain.setGeneralLayout(customerPane, "/view/EmptyPane.fxml");
    }
    
    @FXML
    private void filterBlackList() {
    	customerObserve.clear();
    	customerObserve.addAll(Restaurant.getInstance().getBlackList());
    	customerList.setItems(customerObserve);
    	customerList.refresh();
    }
    @FXML
    private void filterNoBlackList() {
    	customerObserve.clear();
    	for(Customer c: Restaurant.getInstance().getCustomers().values()) {
    		if(!Restaurant.getInstance().getBlackList().contains(c))
    			customerObserve.add(c);
    	}
    	customerList.setItems(customerObserve);
    	customerList.refresh();
    }
    
    @FXML
    private void loadOrder() { 
    	closeOrder();
	  	orderObserve.clear();
	  	orderObserve.addAll(Restaurant.getInstance().getOrders().values());
     	orderList.setItems(orderObserve);
     }
    
    @FXML
    public void openOrder() throws IOException {
    	chosenOrder= orderList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenOrder!=null) {    			
    			GUIMain.setGeneralLayout(orderPane, "/view/EditOrder.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
    }
    
    @FXML
    private void closeOrder()  {
    	GUIMain.setGeneralLayout(orderPane, "/view/EmptyPane.fxml");
    }
    
    @FXML
    private void loadDelivery() {  
    	 closeDelivery();
    	 deliveryObserve.clear();
	     deliveryObserve.addAll(Restaurant.getInstance().getDeliveries().values());
	     deliveryList.setItems(deliveryObserve);
     }
    
    @FXML
    public void openDelivery() throws IOException {
    	chosenDelivery= deliveryList.getSelectionModel().getSelectedItem();
    	try {
    		if(chosenDelivery!=null) {    			
    			GUIMain.setGeneralLayout(deliveryPane, "/view/EditDelivery.fxml");
		    	
    		} else 
    			throw new NothingSelectedExceptions();
    	} catch (NothingSelectedExceptions e) {
    		JOptionPane.showMessageDialog(null, e.getMessage());
    	} 
    }
    
    @FXML
    private void closeDelivery()  {
    	GUIMain.setGeneralLayout(deliveryPane, "/view/EmptyPane.fxml");
    }   
   
    
    
    @FXML
    private void extraDelData() {
    	HashMap<String,Integer>delNum= Restaurant.getInstance().getNumberOfDeliveriesPerType();
    	regularNum.setText(""+delNum.get("Regular delivery"));
    	expressNum.setText(""+delNum.get("Express delivery"));
    	expressRevenue.setText(""+Restaurant.getInstance().revenueFromExpressDeliveries());
    }
    
    @FXML
    private void openAddDelAndOrder () throws IOException {
    	GUIMain.setGeneralLayout(orderAndDeliveryPane, "/view/ManagerAddDelivery.fxml");
    }
    
    @FXML
    private void filterTodayDeliveries() {
    	deliveryObserve.clear();
    	for(Delivery d: Restaurant.getInstance().getDeliveries().values()) {
    		if(LocalDateTime.now().toLocalDate().equals(d.getDeliveredDate().toLocalDate()))
    			deliveryObserve.add(d);
    	}
    	deliveryList.setItems(deliveryObserve);
    	deliveryList.refresh();
    }
    
    @FXML
    private void filterTodayOrders() {
    	orderObserve.clear();
    	for(Order o: Restaurant.getInstance().getOrders().values()) {
    		if(o== null || o.getDelivery()==null) {
    			
    		} else if(LocalDateTime.now().toLocalDate().equals(o.getDelivery().getDeliveredDate().toLocalDate()))
    			orderObserve.add(o);
    	}
   	    orderList.setItems(orderObserve);
   	    orderList.refresh();
    }
    
    

}

