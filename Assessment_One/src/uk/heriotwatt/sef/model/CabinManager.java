package uk.heriotwatt.sef.model;

import java.util.ArrayList;
import java.util.List;

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
		for (Cabin cab : this.cabins) {
			
		}
	}

}
