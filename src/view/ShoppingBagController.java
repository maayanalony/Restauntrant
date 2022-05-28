package view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import Exceptions.NoDeliveryTypeExceptions;
import Exceptions.NoDishesInOrderExceptions;
import Model.Delivery;
import Model.DeliveryArea;
import Model.DeliveryPerson;
import Model.Dish;
import Model.ExpressDelivery;
import Model.Order;
import Model.RegularDelivery;
import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;

public class ShoppingBagController extends MenuController{
	
	public static double sum= 0;
	public String delType = null;
	
	private ObservableList<Dish> shoppingBagObserve = FXCollections.observableArrayList();
	@FXML private ListView<Dish> shoppingBagList= new ListView<Dish>();	
	@FXML private Label status= new Label();	 
	@FXML private Label shoppingSum= new Label();
	@FXML private RadioButton regDelivery;
    @FXML private RadioButton exDelivery;
	
	public void initialize() {

		if(MenuController.dishesInOrder.isEmpty() || MenuController.dishesInOrder==null) {
			status.setText("אין מנות בסל הקניות");
			status.setVisible(true);
			shoppingBagList.setVisible(false);
		} else {	
			status.setVisible(false);
			shoppingBagList.setVisible(true);
			loadDishesInOrder();
			shoppingBagList.setItems(shoppingBagObserve);
			shoppingSum.setText("סכום ההזמנה לא כולל משלוח: "+sum);
		}
		
	}
	
	@Override
	public void loadDishesInOrder() {
		shoppingBagObserve.clear();
	   	for(Dish d: MenuController.dishesInOrder) {
	   		shoppingBagObserve.add(d);
	   		sum=sum+d.getPrice();
	   	}
	   	if(shoppingBagList==null) {
	   		System.out.println("dish2= NULL");
	   	}
	   	else
	   		shoppingBagList.setItems(shoppingBagObserve);
	}
	
	@FXML
	private void chooseDelivery () {
		if(regDelivery.isSelected()) {
	    	   shoppingSum.setText("סכום ההזמנה כולל משלוח: "+sum);
	    	   delType= "Regular";
		}
		else if(exDelivery.isSelected()) {
			sum= sum+5;
			shoppingSum.setText("סכום ההזמנה כולל משלוח: "+sum);
			delType= "Express";
		}
	}
	
	public RegularDelivery rd;
	public ExpressDelivery ed;
	public LocalDateTime orderTime= LocalDateTime.now();
	
	public static Order thisOrder;
	
	@FXML
	public void placeOrder() throws IOException {
		try {
			if(MenuController.dishesInOrder.isEmpty() || MenuController.dishesInOrder==null)
				throw new NoDishesInOrderExceptions();
			addOrderAndDelivery();
			if(!LoginController.manager) {
				GUIMain.setMainLayout("/view/CustomerLayout.fxml");
				GUIMain.setSecLayout("/view/EndPlaceOrder.fxml");
			} else {
				GUIMain.setMainLayout("/view/ManagerLayout2.fxml");
			}
			
		} catch (NoDishesInOrderExceptions e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/*
	 * the customer can choose if to pay extra and take an express delivery
	 * or an order delivery that can be grouped to other orders.
	 * the system groups automatically regular orders that belongs to the same 
	 * area and the difference between them is 20 minutes
	 */
	
	@FXML
	public void addOrderAndDelivery() throws IOException {
		try {
			
			if(delType==null) 
				throw new NoDeliveryTypeExceptions();
			
			Order newOrder= new Order(chosenCustomer, MenuController.dishesInOrder, null);
			DeliveryArea area= Restaurant.getInstance().findArea(chosenCustomer.getNeighberhood());
			if(!Restaurant.getInstance().addOrder(newOrder)) {
				JOptionPane.showMessageDialog(null, "בעקבות תקלה המשלוח לא בוצע");
				return;
			}
			orderTime= LocalDateTime.now();
	 	    if(delType.equals("Regular")) {
	 	    	if(Restaurant.getInstance().getDeliveries().isEmpty()) { // if there are no regular deliveries
	 	    		newRegularDelivery(newOrder, area);
	 	    		return;
	 	    	} else  {
	 	    		ArrayList <Delivery> allDel= new ArrayList<>();
	 	    		allDel.addAll(Restaurant.getInstance().getDeliveries().values());
	 	    		for(Delivery d : allDel) {
	 	    		   if(d instanceof RegularDelivery) {
	 	    			   if(!d.isDelivered() && d.getArea().equals(area) && d.getDeliveredDate().plusMinutes(20).isAfter(orderTime)) {
	 		    			   // adding the order to exist regular delivery
	 	    				   // if there is a delivery to the same area and this delivery is after less then 20 minutes from it
	 	    				   rd = (RegularDelivery)d;
	 	    				   rd.addOrder(newOrder);
	 	    				   newOrder.setDelivery(rd);
	 	    				   thisOrder= newOrder;
	 	    				   JOptionPane.showMessageDialog(null, "המשלוח נקלט בהצלחה ויגיע אליכם תוך מקסימום שעה");
	 	    				   return;
	 		    		   } 
	 	    		   }
	 	    		   
	 	    	   } // end for	 
	 	    		
	 	    		newRegularDelivery(newOrder, area);
	 	    		return;
		    	} // end else- not first regular delivery 
	 	    			   
		    } else if(delType.equals("Express")) {
		    	   ed= new ExpressDelivery(getPersonForDel(area), area, false, newOrder,orderTime);
		    	   if(Restaurant.getInstance().addDelivery(ed)) {
		    		   thisOrder= newOrder;
			    	   JOptionPane.showMessageDialog(null, "המשלוח נקלט בהצלחה ויגיע אליכם תוך"+ thisOrder.orderWaitingTime(area)+" דקות");
			    	   return;
		    	   }
		    }
	 	    
	 	   
		} catch(NoDeliveryTypeExceptions e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void newRegularDelivery(Order newOrder, DeliveryArea area) throws IOException {
		rd= new RegularDelivery(getPersonForDel(area), area, false, orderTime);
		rd.addOrder(newOrder);
		newOrder.setDelivery(rd);
		if (Restaurant.getInstance().addDelivery(rd)) {
			JOptionPane.showMessageDialog(null, "המשלוח נקלט בהצלחה ויגיע אליכם תוך מקסימום שעה");
			thisOrder= newOrder;
		}
	}


	private DeliveryPerson getPersonForDel(DeliveryArea area) {
		Random rand = new Random();
		ArrayList<Integer> idOfDp= new ArrayList<>();
		for(DeliveryPerson dp: area.getDelPersons())
			idOfDp.add(dp.getId());
		int randomID = idOfDp.get(rand.nextInt(idOfDp.size()));
		return Restaurant.getInstance().getRealDeliveryPerson(randomID);
	}
	
	
}
