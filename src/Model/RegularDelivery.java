package Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class RegularDelivery extends Delivery implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private TreeSet<Order> orders;
	
	public RegularDelivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered,LocalDateTime deliveredDate) {
		super(deliveryPerson, area, isDelivered, deliveredDate);
		this.orders = new TreeSet<>();
	}

	public RegularDelivery(TreeSet<Order> orders, DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered,LocalDateTime deliveredDate) {
		super(deliveryPerson, area, isDelivered, deliveredDate);
		this.orders = orders;
	}
	
	public RegularDelivery(int id) {
		super(id);
	}

	public SortedSet<Order> getOrders() {
		return Collections.unmodifiableSortedSet(orders);
	}
	
	public void setOrders(TreeSet<Order> orders) {
		this.orders = orders;
	}
	
	//Methods
	
	public boolean addOrder(Order o) {
		return orders.add(o);
	}
	
	public boolean removeOrder(Order o) {
		return orders.remove(o);
	}
	
	
	public String toString() {
		return super.toString()+"\nמספרים מזהים של ההזמנות במשלוח זה: "+printOrdersID();
	}
	
	public String printOrdersID() {
		String st= null;
		for(Order o: orders) {
			if(st==null)
				st=""+o.getId();
			else
				st= st+", "+o.getId();
		}
		return st;
	}

}
