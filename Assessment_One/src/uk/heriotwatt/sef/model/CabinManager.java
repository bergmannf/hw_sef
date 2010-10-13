package uk.heriotwatt.sef.model;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class CabinManager {
	
	private List<Cabin> cabins;
	
	public CabinManager()
	{
		this.cabins = new ArrayList<Cabin>();
	}
	
	public void addCabin(Cabin cab)
	{
		this.cabins.add(cab);
	}
	
	public Cabin getCabinAtIndex(int index)
	{
		return this.cabins.get(index);
	}
	
	public void printAllCabins()
	{
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.UK);
		formatter.format("%1$10s | %2$10s | %3$10s | %4$5s", "NUMBER", "OWNER", "FACILITIES", "BEDS");
		for (Cabin cab : this.cabins) {
			// TODO Return the initials of the owner.
			sb.append("\n");
			formatter.format("%1$10d | %2$10s | %3$10s | %4$5d", cab.getCabinNumber(), cab.getOwner().getInitPeriodLast(), cab.getFacilities().toString().toLowerCase(), cab.getBeds());
		}
		System.out.println(sb.toString());
	}

}
