package Exceptions;


public class NoDishesInOrderExceptions extends Exception{
	
	private static final long serialVersionUID = 1L;

	public NoDishesInOrderExceptions() {
		super("לא קיימות מנות בהזמנה זו\nלא ניתן לבצע משלוח");
		
	}
	
}
