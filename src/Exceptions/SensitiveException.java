package Exceptions;

public class SensitiveException extends Exception {

	private static final long serialVersionUID = 1L;

	public SensitiveException(String customerName, String dishName) {
		super("�� ���� ������ ��� ��\n"+customerName + " ����/� ���� �������� ���� " + dishName + "\n���� �� �� ��������� ����� ������ �� �� ����� �������� ��� �� ��� ����� �����");
		
	}
	
}
