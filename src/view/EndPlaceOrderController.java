package view;

import java.io.IOException;
import java.time.LocalDateTime;

import Model.Dish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class EndPlaceOrderController extends ShoppingBagController /*implements Initializable*/{
	
	@FXML private Label date;
	@FXML private Label name;
	@FXML private Label orderID;
	@FXML private Label deliveryID;
	@FXML private Label area;
	@FXML private Label neighberhood;
	@FXML private Label deliveryPerson;
	private ObservableList<Dish> dishesObserve = FXCollections.observableArrayList();
	@FXML private ListView<Dish> dishesList;
	@FXML private Label payment;
	@FXML private Button backTo;
	
	@Override
	public void initialize(/*URL arg0, ResourceBundle arg1*/) { 
		loadDishes();
		setOrderData();
		
	}
	
	
	public void setOrderData() {
		date.setText(printDateAndTime(ShoppingBagController.thisOrder.getDelivery().getDeliveredDate()));
		name.setText(ShoppingBagController.chosenCustomer.toString()+" הזמנתך בוצעה בהצלחה!");
		orderID.setText(""+ShoppingBagController.thisOrder.getId());
		deliveryID.setText(""+ShoppingBagController.thisOrder.getDelivery().getId());
		area.setText(ShoppingBagController.thisOrder.getDelivery().getArea().toString());
		neighberhood.setText(LoginController.user.getNeighberhood().toString());
		deliveryPerson.setText(ShoppingBagController.thisOrder.getDelivery().getDeliveryPerson().toString());
		payment.setText(""+ShoppingBagController.sum);
	}
	
	
	public void loadDishes() {
		dishesObserve.clear();
	   	for(Dish d: MenuController.dishesInOrder) {
	   		dishesObserve.add(d);
	   	}
	   	if(dishesList==null) {
	   		System.out.println("dish= NULL");
	   	}
	   	else
	   		dishesList.setItems(dishesObserve);
	}
	
	@FXML
	public void goBack() throws IOException {
		openAfterPlaceOrder();
	}
	
	public String printDateAndTime(LocalDateTime d) {
		return d.getDayOfMonth()+"/"+ d.getMonthValue()+"/"+ d.getYear()+"\n"+ d.getHour()+":"+ d.getMinute();
	}

}
