package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Gender;
import Utils.Neighberhood;

public class Customer extends Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static int id= setId();
	private Neighberhood neighberhood;
	private boolean isSensitiveToLactose;
	private boolean isSensitiveToGluten;
	private String userName;
	private String password;
	
	public Customer(String firstName, String lastName, LocalDate birthDay, Gender gender,
			Neighberhood neighberhood,	boolean isSensitiveToLactose, boolean isSensitiveToGluten,
			String userName, String password) {
		super(setId(), firstName, lastName, birthDay, gender);
		this.neighberhood = neighberhood;
		this.isSensitiveToLactose = isSensitiveToLactose;
		this.isSensitiveToGluten = isSensitiveToGluten;
		this.userName=userName;
		this.password=password;
	}
	public static Integer setId() {
		int lastId=1;
		if(Restaurant.getInstance().getCustomers()==null) { // for the first
			id=1;
			return 1;
		}
		for(Customer d: Restaurant.getInstance().getCustomers().values()) { // find the highest id
			if(lastId< d.getId())
				lastId=d.getId();	
		}
		for(int i=1; i<lastId; i++) { // fill missing numbers between 1 and the highest id
			if(Restaurant.getInstance().getRealCustomer(i)==null) {
				id= i;
				return i;
			}
		}
		id= lastId+1;
		return (lastId+1); // in case there are no missing numbers
	}
	public Customer(int id) {
		super(id);
	}

	public Neighberhood getNeighberhood() {
		return neighberhood;
	}

	public void setNeighberhood(Neighberhood neighberhood) {
		this.neighberhood = neighberhood;
	}

	public boolean isSensitiveToLactose() {
		return isSensitiveToLactose;
	}

	public void setSensitiveToLactose(boolean isSensitiveToLactose) {
		this.isSensitiveToLactose = isSensitiveToLactose;
	}

	public boolean isSensitiveToGluten() {
		return isSensitiveToGluten;
	}

	public void setSensitiveToGluten(boolean isSensitiveToGluten) {
		this.isSensitiveToGluten = isSensitiveToGluten;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return super.toString()+"\n שכונת מגורים: "+neighberhood+"\nרגישות ללקטוז: " 
	+ isSensitiveToLactose + ", רגישות לגלוטן: " + isSensitiveToGluten+"\nשם משתמש: "+userName+", סיסמה: "+password;
	}

}
