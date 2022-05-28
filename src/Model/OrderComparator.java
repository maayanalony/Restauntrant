package Model;

import java.io.Serializable;
import java.util.Comparator;

public class OrderComparator implements Comparator<Order> ,Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Order o1, Order o2) {  
		return o1.getDelivery().getDeliveredDate().compareTo(o2.getDelivery().getDeliveredDate());
	}

}


