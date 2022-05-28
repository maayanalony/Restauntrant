package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Delivery  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private DeliveryPerson deliveryPerson;
	private DeliveryArea area;
	private boolean isDelivered;
	private LocalDateTime deliveredDate;
	
	public Delivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered,LocalDateTime diliveredDate) {
		super();
		this.id = setId();
		this.deliveryPerson = deliveryPerson;
		this.area = area;
		this.isDelivered = isDelivered;
		this.deliveredDate = diliveredDate;
	}
	public Integer setId() {
		int lastId=1;
		if(Restaurant.getInstance().getDeliveries()==null)
			this.id=1;
		for(Delivery d: Restaurant.getInstance().getDeliveries().values())
			lastId=d.getId();	
		for(int i=1; i<lastId; i++) {
			if(Restaurant.getInstance().getRealDelivery(i)==null) 
				return i;
		}
		return (lastId+1);
	}
	public Delivery(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public DeliveryPerson getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	public DeliveryArea getArea() {
		return area;
	}

	public void setArea(DeliveryArea area) {
		this.area = area;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}
	

	public LocalDateTime getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(LocalDateTime deliveredDate) {
		this.deliveredDate = deliveredDate;
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
		Delivery other = (Delivery) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "מספר משלוח: "+id+" "+ printDateAndTime(deliveredDate) +"\nאזור: " + area.getAreaName() 
			+ " שליח: " + deliveryPerson.getFirstName()+" "+deliveryPerson.getLastName() 
			+  ", האם נשלח?"	+ isDelivered;
	}
	
	public String printDateAndTime(LocalDateTime d) {
		return d.getDayOfMonth()+"/"+ d.getMonthValue()+"/"+ d.getYear()+" בשעה: "+ d.getHour()+":"+ d.getMinute();
	}

}
