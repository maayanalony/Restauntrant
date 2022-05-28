package Exceptions;

public class SensitiveException extends Exception {

	private static final long serialVersionUID = 1L;

	public SensitiveException(String customerName, String dishName) {
		super("לא ניתן להוסיף מנה זו\n"+customerName + " רגיש/ה לאחד המרכיבים במנה " + dishName + "\nשימו לב יש באפשרותכם להציג בתפריט רק את המנות המתאימות לכם על ידי ביצוע סינון");
		
	}
	
}
