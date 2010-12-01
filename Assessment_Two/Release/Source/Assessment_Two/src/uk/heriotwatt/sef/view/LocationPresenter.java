package uk.heriotwatt.sef.view;


import java.util.List;

import uk.heriotwatt.sef.model.CabinNotFoundException;
import uk.heriotwatt.sef.model.Location;
import uk.heriotwatt.sef.model.LocationManager;


public class LocationPresenter {

	private LocationManager manager;
	private List<Location> locations;

	public LocationPresenter(LocationManager manager2) {
		this.manager = manager2;
		this.locations = this.manager.getLocations();
	}
	
	public List<Location> getAllLocations()
	{
		this.locations = this.manager.getLocations();
		return this.locations;
	}
	
	public List<Location> getLocations()
	{
		return this.locations;
	}

	public List<Location> searchLocations(String search, String category) {
		this.locations = this.manager.searchLocations(search, category);
		return this.locations;
	}
	
	public List<Location> orderLocations(boolean byId)
	{
		return this.locations = this.manager.orderLocations(this.locations, byId);
	}

	public Location findLocationById(String id) throws CabinNotFoundException {
		return this.manager.findLocationById(id);
	}
}
