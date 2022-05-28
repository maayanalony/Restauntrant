package Exceptions;

public class IllegalCustomerException extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalCustomerException() {
		super("לא ניתן לבצע את ההזמנה\nצרו קשר עם המסעדה");
	}

	
}
