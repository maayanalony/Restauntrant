package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Utils.DishType;

public class Dish  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String dishName;
	private DishType type;
	private ArrayList<Component> componenets;
	private double price;
	private int timeToMake;
	
	// constructors 
	
	public Dish(String dishName, DishType type, ArrayList<Component> componenets, int timeToMake) {
		super();
		System.out.println(componenets);
		this.id=setId();
		this.dishName = dishName;
		this.type = type;
		this.componenets = componenets;
		this.timeToMake = timeToMake;
		price = calcDishPrice();
	}
	
	public Dish(int id) {
		this.id = id;
	}
	
	// getters setters
	
	public Integer getId() {
		return id;
	}
	public Integer setId() {
		int lastId=1;
		if(Restaurant.getInstance().getDishes()==null)
			this.id=1;
		for(Dish d: Restaurant.getInstance().getDishes().values())
			lastId=d.getId();	
		for(int i=1; i<lastId; i++) {
			if(Restaurant.getInstance().getRealDish(i)==null) 
				return i;
		}
		return (lastId+1);
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public DishType getType() {
		return type;
	}
	public void setType(DishType type) {
		this.type = type;
	}
	public List<Component> getComponenets() {
		return Collections.unmodifiableList(this.componenets);
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		if(price > 0.0)
			this.price = price;
		else
			price = 0.0;
	}
	
	
	public int getTimeToMake() {
		return timeToMake;
	}
	public void setTimeToMake(int timeToMake) {
		this.timeToMake = timeToMake;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Dish other = (Dish) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String temp= String.format("\nמחיר: %1.2f ", price);
		return dishName+"\nמרכיבים: "+printCompNames()+temp;
				
	}
	
	private String printCompNames() {
		String comps="";
		int counter=0;
		for(Component c: getComponenets()) {
			if(counter==0)
				comps=c.getComponentName();
			else
				comps=comps+", "+c.getComponentName();
			counter++;
		}
		comps=comps+".";
		return comps;
		
	}
	
	
	// methods
	
	public double calcDishPrice() {
		double price = 0.0;
		for(Component c : getComponenets()) {
			price += c.getPrice();
		}
		price = price*3;
		return price;
	}
	
	public boolean addComponent(Component c) {
		return this.componenets.add(c);
	}
	
	public boolean removeComponent(Component c) {
		return this.componenets.remove(c);
	}
	
	
	
}
