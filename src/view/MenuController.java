package view;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import Exceptions.SensitiveException;
import Model.Component;
import Model.Customer;
import Model.Dish;
import Model.ProfitDishComparator;
import Model.Restaurant;
import Utils.DishType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;

public class MenuController extends CustomerController /*implements Initializable*/{
	
	private ObservableList<Dish> startObserve = FXCollections.observableArrayList();
	private ObservableList<Dish> mainObserve = FXCollections.observableArrayList();
	private ObservableList<Dish> dessertObserve = FXCollections.observableArrayList();
	private ObservableList<Dish> shoppingBagObserve = FXCollections.observableArrayList();
	
	public static Customer chosenCustomer;
	public static ArrayList<Dish> dishesInOrder= new ArrayList<>();
	
	private double sum=0;
	
	@FXML private ListView<Dish> startList;
	@FXML private ListView<Dish> mainList;
	@FXML private ListView<Dish> dessertList;
	@FXML private ListView<Dish> shoppingBagList;
	@FXML private Label shoppingSum;
	// menu filters
	@FXML private RadioButton all;
	@FXML private RadioButton relevent;
	@FXML private RadioButton other;  
	@FXML private CheckBox noGluten;
	@FXML private CheckBox noLactose;   

	public void initialize(/*URL arg0, ResourceBundle arg1*/) {
		loadDishes();
		loadDishesInOrder();
		if(LoginController.user != null) 
			chosenCustomer= LoginController.user;
		else if(ManagerController.chosenCustomer!=null)
			chosenCustomer= ManagerController.chosenCustomer;
	}
	
	@FXML
	private void addDishToOrder(ActionEvent e) {
		Dish addStart = startList.getSelectionModel().getSelectedItem();
		Dish addMain = mainList.getSelectionModel().getSelectedItem();
		Dish addDessert = dessertList.getSelectionModel().getSelectedItem();
		try {
			  if(addStart!=null) {
				  startList.getSelectionModel().clearSelection();
				  add(addStart, startList);
			  }
			  if(addMain!=null) {
				  mainList.getSelectionModel().clearSelection();
				  add(addMain, mainList);
			  }
			  if(addDessert!=null) {
				  dessertList.getSelectionModel().clearSelection();
				  add(addDessert, dessertList);
			  }
		}catch(SensitiveException exception) {
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
	}
	
	private void add(Dish d, ListView<Dish> list) throws SensitiveException {
		for(Component c: d.getComponenets()) {
			if( (chosenCustomer.isSensitiveToGluten() && c.isHasGluten()) ||
					(chosenCustomer.isSensitiveToLactose() && c.isHasLactose()) ){
				throw new SensitiveException(chosenCustomer.getFirstName() 
						+ " " +chosenCustomer.getLastName(), d.getDishName());
			}
	    }
		shoppingBagObserve.add(d);
        shoppingBagList.setItems(shoppingBagObserve);
        sum=sum+d.getPrice();
        shoppingSum.setText("סכום ההזמנה לא כולל משלוח: "+sum);
        dishesInOrder.add(d);
	}
	
	@FXML
	private void removeDishFromOrder(ActionEvent event) {
		Dish s = shoppingBagList.getSelectionModel().getSelectedItem();
	      if (s != null) {
	    	  shoppingBagList.getSelectionModel().clearSelection();
	          shoppingBagObserve.remove(s);
	          sum=sum-s.getPrice();
		      shoppingSum.setText("סכום ההזמנה לא כולל משלוח: "+sum);
		      dishesInOrder.remove(s);
	      }
	}
	
	private void addByDishType(Dish d) {
		   if(d.getType().equals(DishType.ראשונה))
	  			startObserve.add(d);
	  		if(d.getType().equals(DishType.עיקרית))
	  			mainObserve.add(d);
	  		if(d.getType().equals(DishType.קינוח))
	  			dessertObserve.add(d);
	   }
	   
	   private void removeByDishType(Dish d) {
		   if(d.getType().equals(DishType.ראשונה))
	  			startObserve.remove(d);
	  		if(d.getType().equals(DishType.עיקרית))
	  			mainObserve.remove(d);
	  		if(d.getType().equals(DishType.קינוח))
	  			dessertObserve.remove(d);
	   }
	   
	   private void setAndRefresh() {
		    startList.setItems(startObserve);
		   	mainList.setItems(mainObserve);
		   	dessertList.setItems(dessertObserve);
		    startList.refresh();
		    mainList.refresh();
		    dessertList.refresh();
	   }
    
   @FXML
   private void loadDishes() {  
	   	startObserve.clear();
	   	mainObserve.clear();
	   	dessertObserve.clear();
	   	for(Dish d: Restaurant.getInstance().getDishes().values()) 
	   		addByDishType(d);
	   	
	   	setAndRefresh();
   }
   
   @FXML
   public void loadDishesInOrder() {
		shoppingBagObserve.clear();
	   	for(Dish d: dishesInOrder) {
	   		shoppingBagObserve.add(d);
	   		sum=sum+d.getPrice();
	   	}
	   	if(shoppingBagList!=null) 
	   		shoppingBagList.setItems(shoppingBagObserve);	
		
	}
   
   // menu filters
   
   @FXML
   private void otherFilter() {
	   loadDishes();
	   boolean showNoGluten= noGluten.isSelected();
	   boolean showNoLactose= noLactose.isSelected();
	   for(Dish d: Restaurant.getInstance().getDishes().values()) {
	   		for(Component c: d.getComponenets()) {
	   			if(showNoGluten && c.isHasGluten())
	   				removeByDishType(d);
	   			if(showNoLactose && c.isHasLactose())
	   				removeByDishType(d);
	   		}
	   	}
	   setAndRefresh();
	   
   }
   
   @FXML
   private void filterReleventDish() {
	   startObserve.clear();
	   	mainObserve.clear();
	   	dessertObserve.clear();
	   	for(Dish d: Restaurant.getInstance().getReleventDishList(chosenCustomer)) 
	   		addByDishType(d);
	   	
	   	setAndRefresh();
   }
   
   @FXML
   private void orderFromCheap() {
	   startObserve.sort(new ProfitDishComparator());
	   mainObserve.sort(new ProfitDishComparator());
	   dessertObserve.sort(new ProfitDishComparator());
	   setAndRefresh();
   }
   

}
