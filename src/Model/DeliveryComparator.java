package Model;

import java.io.Serializable;
import java.util.Comparator;

public class DeliveryComparator  implements Comparator<Delivery> ,Serializable{

	private static final long serialVersionUID = 1L;
	
	@Override
	public int compare(Delivery d1, Delivery d2) {
		if(d1.getDeliveredDate().getDayOfMonth() > d2.getDeliveredDate().getDayOfMonth())
			return 1;
		if(d1.getDeliveredDate().getDayOfMonth() < d2.getDeliveredDate().getDayOfMonth())
			return -1;
		return d1.getId()>d2.getId() ? 1 :-1;
	}


}
