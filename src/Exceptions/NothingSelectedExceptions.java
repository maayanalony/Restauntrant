package Exceptions;


public class NothingSelectedExceptions extends Exception{
	
	private static final long serialVersionUID = 1L;

	public NothingSelectedExceptions() {
		super("יש לבחור פריט מהרשימה כדי לבצע פעולה");
		
	}
	
}
