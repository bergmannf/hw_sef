package uk.heriotwatt.sef.model;

import java.util.Comparator;

public class IdComparator implements Comparator<Location> {

	@Override
	public int compare(Location o1, Location o2) {
		return o1.getId().compareTo(o2.getId());
	}

}
