package Model;

import java.io.Serializable;
import java.util.Comparator;

public class ProfitDishComparator implements Comparator<Dish> ,Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Dish o1, Dish o2) {
		if((o2.getPrice()/o2.getTimeToMake())>(o1.getPrice()/o1.getTimeToMake()))
			return 1;
		else if((o2.getPrice()/o2.getTimeToMake())<(o1.getPrice()/o1.getTimeToMake()))
			return -1;
		return -1*o1.getId().compareTo(o2.getId());
	}

}
