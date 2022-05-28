package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order implements Comparable<Order>, Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Customer customer;
	private ArrayList<Dish> dishes;
	private Delivery delivery;
	
	// constructors 
	
	public Order(Customer customer, ArrayList<Dish> dishes, Delivery delivery) {
		super();
		this.id = setId();
		this.customer = customer;
		this.dishes = dishes;
		this.delivery = delivery;
	}
	public Integer setId() {
		int lastId=1;
		if(Restaurant.getInstance().getOrders()==null)
			this.id=1;
		for(Order d: Restaurant.getInstance().getOrders().values())
			lastId=d.getId();	
		for(int i=1; i<lastId; i++) {
			if(Restaurant.getInstance().getRealOrder(i)==null) 
				return i;
		}
		return (lastId+1);
	}
	public Order(int id) {
		this.id = id;
	}
	
	// getters setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Dish> getDishes() {
		return Collections.unmodifiableList(dishes);
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		if(delivery==null)
			return null;
		return "מספר הזמנה: " + id +printDateAndTime() + "\nלקוח: " + customer+"\nמספר משלוח: "+delivery.getId()+" לאזור: "+delivery.getArea().getAreaName()+" שליח: "+delivery.getDeliveryPerson().getFirstName()+" "+delivery.getDeliveryPerson().getLastName()+"\nמנות: "+printDishesName();
	}
	
	public String printDateAndTime() {
		return " תאריך: "+ delivery.getDeliveredDate().getDayOfMonth()+"/"+ 
			delivery.getDeliveredDate().getMonthValue()+"/"+ delivery.getDeliveredDate().getYear()+
			" שעה: "+ delivery.getDeliveredDate().getHour()+":"+ delivery.getDeliveredDate().getMinute();
	}
	
	public String printDishesName() {
		String st= null;
		for(Dish d: dishes) {
			if(st==null)
				st=""+d.getDishName();
			else
				st= st+", "+d.getDishName();
		}
		return st;
	}
	
	// methods

	public double calcOrderRevenue() {
		double revenue = 0.0;
		for(Dish d : getDishes()) {
			double price = d.calcDishPrice();
			double cost = 0.0;
			for(Component c : d.getComponenets()) {
				cost += c.getPrice();
			}
			revenue += (price - cost);
		}
		return revenue;
	}
	
	public int orderWaitingTime(DeliveryArea da) {
		int time = 0;
		time += da.getDeliverTime();
		for(Dish d : getDishes()) {
			time += d.getTimeToMake();
		}
		return time;
	}

	
	public boolean addDish(Dish d) {
		return dishes.add(d);
	}
	
	public boolean removeDish(Dish d) {
		return dishes.remove(d);
	}
	
	@Override
	public int compareTo(Order o) {
		return this.id.compareTo(o.getId());
	}

}
