package uk.heriotwatt.sef.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class LocationManager extends Observable {

	private List<Location> locations;

	public List<Location> getLocations() {
		return locations;
	}

	private GenericFileHandler<Cabin> cfh;
	private GenericFileHandler<Plot> lfh;

	public LocationManager(GenericFileHandler<Cabin> cfh,
		GenericFileHandler<Plot> lfh) {
		this.locations = new ArrayList<Location>();
		this.cfh = cfh;
		this.lfh = lfh;
		this.locations.addAll(this.cfh.readFromFile(new Cabin()));
		this.locations.addAll(this.lfh.readFromFile(new Plot()));
	}

	public void addLocation(Location cab) {
		this.locations.add(cab);
	}

	/**
	 * Attempts to find a cabin with the provided cabinNumber in the cabin-List.
	 *
	 * @param id
	 *            The cabinnumber of the cabin to be returned
	 * @return The first cabin in the list with the corresponding cabinnumber.
	 * @throws CabinNotFoundException
	 *             If no cabin with the provided number could be found.
	 */
	public Location findLocationById(String id) throws CabinNotFoundException {
		Location locationFound = null;
		for (Location location : this.locations) {
			if (location.getId().equals(id)) {
				locationFound = location;
				break;
			}
		}
		if (locationFound != null) {
			return locationFound;
		} else {
			throw new CabinNotFoundException(String.format(
				"The cabin with number %d was not in the list.", id));
		}
	}

	/**
	 * Retrieves that cabin at the specified position in the cabin list.
	 *
	 * @param index
	 *            The position for which the cabin should be returned.
	 * @return Tha cabin.
	 */
	public Location getCabinAtIndex(int index) {
		if (index < this.getNumberOfCabins()) {
			return this.locations.get(index);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Returns the maximum possible income that could be achieved. Therefore the
	 * cost for all cabins are added up.
	 *
	 * @return The added cost of all cabins.
	 */
	public double getMaximumPossibleIncome() {
		double result = 0;
		for (Location cabin : this.locations) {
			result += cabin.getCost();
		}
		return result;
	}

	/**
	 * Returns the cost for the cheapest cabin.
	 *
	 * @return The cost of the cheapest cabin.
	 * @throws NoCabinsException
	 */
	public double getCheapestCabinCost() throws NoCabinsException {
		if (this.locations.size() > 0) {
			Location cheapestCab = null;
			for (Location cab : this.locations) {
				if (cheapestCab == null) {
					cheapestCab = cab;
				}
				if (cab.getCost() < cheapestCab.getCost()) {
					cheapestCab = cab;
				}
			}
			return cheapestCab.getCost();
		} else {
			throw new NoCabinsException(
				"There are no cabins present. Insert cabins first.");
		}

	}

	/**
	 * Returns the cost for the most expensive cabin.
	 *
	 * @return The cost of the most expensive cabin.
	 * @throws NoCabinsException
	 */
	public double getExpensiveCabinCost() throws NoCabinsException {
		if (this.locations.size() > 0) {
			Location expensiveCab = null;
			for (Location cab : this.locations) {
				if (expensiveCab == null) {
					expensiveCab = cab;
				}
				if (cab.getCost() > expensiveCab.getCost()) {
					expensiveCab = cab;
				}
			}
			return expensiveCab.getCost();
		} else {
			throw new NoCabinsException(
				"There are no cabins present. Insert cabins first.");
		}
	}

	/**
	 * Returns the number of cabins currently registered in the list.
	 *
	 * @return The number of cabins.
	 */
	public int getNumberOfCabins() {
		return this.locations.size();
	}

	public void bookLocation(String bookedLocation)
		throws LocationAlreadyBookedException {
		try {
			Location loc = this.findLocationById(bookedLocation);
			if (!loc.isBooked) {
				loc.isBooked = true;
				loc.bookings++;
				this.setChanged();
				this.notifyObservers(true);
				this.clearChanged();
			} else {
				throw new LocationAlreadyBookedException(String.format(
					"The location with id %s is already booked.",
					bookedLocation));
			}
		} catch (CabinNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Orders the present list of locations.
	 * Either by their id, or by price.
	 * @param locations2 
	 *
	 * @param byId True of order by id, false if order by price.
	 */
	public List<Location> orderLocations(List<Location> locations2, boolean byId) {
		List<Location> returnList;
		if (byId) {
			Collections.sort(locations2, new IdComparator());
		} else {
			Collections.sort(locations2, new PriceComparator());
		}
		this.setChanged();
		this.notifyObservers(true);
		this.clearChanged();
		returnList = locations2;
		return returnList;
	}

	/**
	 * Searches the list of location for matches with the provided parameters.
	 *
	 * @param searchString The string that should be matched. In case of the compared attribute being not a string, only a perfect match will return true.
	 * @param category The category specifies the compared attributes: id, price, booked, area.
	 * @return A list of the matching locations. If no search string is provided then all locations are returned.
	 */
	public List<Location> searchLocations(String searchString, String category) {
		List<Location> returnList = new LinkedList<Location>();
		if (searchString != null) {
			if (!searchString.isEmpty()) {
				if (category.equals("Id")) {
					for (Location location : this.locations) {
						if (location.getId().contains(searchString)) {
							returnList.add(location);
						}
					}
				} else if (category.equals("Price")) {
					for (Location location : this.locations) {
						if (location.getCost() == (Double
							.parseDouble(searchString))) {
							returnList.add(location);
						}
					}
				} else if (category.equals("Area")) {
					for (Location location : this.locations) {
						if (location.getSize() == (Double
							.parseDouble(searchString))) {
							returnList.add(location);
						}
					}
				} else if (category.equals("Booked")) {
					for (Location location : this.locations) {
						if (location.isBooked() == (Boolean
							.parseBoolean(searchString))) {
							returnList.add(location);
						}
					}
				}
			} else {
				returnList = this.locations;
			}
		} else {
			returnList = this.locations;
		}
		return returnList;
	}

	public void saveLocations() {
		List<Cabin> cabins = new LinkedList<Cabin>();
		List<Plot> plots = new LinkedList<Plot>();
		for (Location loc : this.locations) {
			if (loc instanceof Cabin)
			{
				cabins.add((Cabin) loc);
			}
			else {
				plots.add((Plot) loc);
			}
		}
		this.cfh.writeToFile(cabins);
		this.lfh.writeToFile(plots);
	}

}