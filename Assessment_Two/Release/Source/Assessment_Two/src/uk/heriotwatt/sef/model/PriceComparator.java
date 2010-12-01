package uk.heriotwatt.sef.model;

import java.util.Comparator;

/**
 * Compares two locations based on their price.
 *
 * @author florian
 */
public class PriceComparator implements Comparator<Location> {

	@Override
	public int compare(Location o1, Location o2) {
		double costLocationOne = o1.getCost();
		double costLocationTwo = o2.getCost();
		return Double.compare(costLocationOne, costLocationTwo);
	}


}
