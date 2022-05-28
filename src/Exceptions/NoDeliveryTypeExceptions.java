package Exceptions;


public class NoDeliveryTypeExceptions extends Exception{
	
	private static final long serialVersionUID = 1L;

	public NoDeliveryTypeExceptions() {
		super("יש לבחור את סוג המשלוח כדי לבצע הזמנה");
		
	}
	
}
