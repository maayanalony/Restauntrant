package Model;

import java.io.Serializable;

import view.LoginController;

public class Component implements Serializable, Comparable<Component> {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String componentName;
	private boolean hasLactose;
	private boolean hasGluten;
	private Double price;
	
	public Component(String componentName, boolean hasLactose, boolean hasGluten, double price) {
		super();
		this.id=setId();
		this.componentName = componentName;
		this.hasLactose = hasLactose;
		this.hasGluten = hasGluten;
		setPrice(price);
	}
	
	public Component(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Integer setId() {
		int lastId=1;
		if(Restaurant.getInstance().getComponenets()==null)
			this.id=1;
		for(Component c: Restaurant.getInstance().getComponenets().values())
			lastId=c.getId();	
		for(int i=1; i<lastId; i++) {
			if(Restaurant.getInstance().getRealComponent(i)==null) 
				return i;
		}
		return (lastId+1);
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public boolean isHasLactose() {
		return hasLactose;
	}

	public void setHasLactose(boolean hasLactose) {
		this.hasLactose = hasLactose;
	}

	public boolean isHasGluten() {
		return hasGluten;
	}

	public void setHasGluten(boolean hasGluten) {
		this.hasGluten = hasGluten;
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
		Component other = (Component) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		if(LoginController.manager) {
			return componentName+"\nמספר מזהה: "+id+"\tמחיר: "+ price;//componentName+" מחיר: "+price;
		}
		return componentName;
	}

	@Override
	public int compareTo(Component o) {
		if(this.price.compareTo(o.getPrice())!=0)
			return this.price.compareTo(o.getPrice());
		return this.id.compareTo(o.getId());
	}
}
