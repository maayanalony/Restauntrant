package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Gender;
import Utils.Vehicle;
import view.LoginController;

public class DeliveryPerson extends Person  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static int id= setId();
	private Vehicle vehicle;
	private DeliveryArea area;
	
	public DeliveryPerson(String firstName, String lastName, LocalDate birthDay, Gender gender, Vehicle vehicle,
			DeliveryArea area) {
		super(setId(), firstName, lastName, birthDay, gender);
		this.vehicle = vehicle;
		this.area = area;
	}
	public static Integer setId() {
		int lastId=1;
		if(Restaurant.getInstance().getDeliveryPersons()==null) { // for the first
			id=1;
			return 1;
		}
		for(DeliveryPerson d: Restaurant.getInstance().getDeliveryPersons().values()) { // find the highest id
			if(lastId< d.getId())
				lastId=d.getId();	
		}
		for(int i=1; i<lastId; i++) { // fill missing numbers between 1 and the highest id
			if(Restaurant.getInstance().getRealDeliveryPerson(i)==null) { 
				id=i;
				return i;
			}
		}
		id= lastId+1;
		return (lastId+1); // in case there are no missing numbers
	}
	
	public DeliveryPerson(int id) {
		super(id);
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public DeliveryArea getArea() {
		return area;
	}
	
	public void setArea(DeliveryArea area) {
		this.area = area;
	}
	
	@Override
	public String toString() {
		/*if(LoginController.manager)
			return*/
		return super.toString()+"\nאזור משלוחים:"+area+" אמצעי נסיעה: " + vehicle;
	}

}
