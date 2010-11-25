package uk.heriotwatt.sef.model;

import java.util.Comparator;

/**
 * Compares two locations based on their ids.
 *
 * @author florian
 */
public class IdComparator implements Comparator<Location> {

	@Override
	public int compare(Location o1, Location o2) {
		return o1.getId().compareTo(o2.getId());
	}

}
