package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Expertise;
import Utils.Gender;

public class Cook extends Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static int id= setId();
	private Expertise expert;
	private boolean isChef;
	
	public Cook(String firstName, String lastName, LocalDate birthDay, Gender gender, Expertise expert,
			boolean isChef) {
		super(setId(), firstName, lastName, birthDay, gender);
		this.expert = expert;
		this.isChef = isChef;
	}
	public static Integer setId() {
		int lastId=1;
		if(Restaurant.getInstance().getCooks()==null) { // for the first
			id=1;
			return 1;
		}
		for(Cook d: Restaurant.getInstance().getCooks().values()) { // find the highest id
			if(lastId< d.getId())
				lastId=d.getId();	
		}
		for(int i=1; i<lastId; i++) { // fill missing numbers between 1 and the highest id
			if(Restaurant.getInstance().getRealCook(i)==null) {
				id=i;
				return i;
			}
		}
		id= lastId+1;
		return (lastId+1); // in case there are no missing numbers
	}
	public Cook(int id) {
		super(id);
	}

	public Expertise getExpert() {
		return expert;
	}

	public void setExpert(Expertise expert) {
		this.expert = expert;
	}

	public boolean isChef() {
		return isChef;
	}

	public void setChef(boolean isChef) {
		this.isChef = isChef;
	}

	@Override
	public String toString() {
		return super.toString()+", מומחיות: " + expert;
	}
	
}
