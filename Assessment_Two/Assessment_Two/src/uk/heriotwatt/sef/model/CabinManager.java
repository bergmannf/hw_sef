package uk.heriotwatt.sef.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class CabinManager extends Observable {

	private List<Location> locations;

	public List<Location> getLocations() {
		return locations;
	}

	private GenericFileHandler<Cabin> cfh;
	private GenericFileHandler<Plot> lfh;

	public CabinManager(GenericFileHandler<Cabin> cfh,
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
		// TODO: Empty array;
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

	public void bookLocation(String bookedLocation, int nights)
			throws LocationAlreadyBookedException {
		try {
			Location loc = this.findLocationById(bookedLocation);
			if (!loc.isBooked) {
				loc.isBooked = true;
				this.setChanged();
				this.notifyObservers(true);
				this.clearChanged();
			} else {
				throw new LocationAlreadyBookedException(String.format(
						"The location with id %s is already booked.",
						bookedLocation));
			}
		} catch (CabinNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void orderLocations(boolean byId) {
		if (byId) {
			Collections.sort(locations, new PriceComparator());
		} else {
			Collections.sort(locations, new PriceComparator());
		}
		this.setChanged();
		this.notifyObservers(true);
		this.clearChanged();
	}
	
	public void searchLocations(String searchString, String category)
	{
		
	}

}
/** Obsolete methods: **/

/**
 * Returns a formatted condition report.
 * 
 * @return A strinbuilder containing the formatted condition report.
 * 
 *         public StringBuilder getConditionReportPrint() { int[] conRep =
 *         getConditionReport(); StringBuilder sb = new StringBuilder();
 *         Formatter formatter = new Formatter(sb, Locale.UK);
 *         formatter.format("%1$15s | %2$15s | %3$15s | %4$15s | %5$15s %n",
 *         Condition.BAD.toString(), Condition.FAIR.toString(),
 *         Condition.GOOD.toString(), Condition.IN_SHAMBLES.toString(),
 *         Condition.PERFECT.toString(), Condition.UNKNOWN.toString());
 *         formatter.format("%1$15d | %2$15d | %3$15d | %4$15d | %5$15d %n",
 *         conRep[0], conRep[1], conRep[2], conRep[3], conRep[4], conRep[5]);
 *         formatter.format("%n"); return sb; }
 * 
 *         /** Returns the values of the condition report.
 * 
 * @return String array containing the number of cabins of a certain condition.
 * 
 *         public int[] getConditionReport() { int size =
 *         Condition.values().length; int[] frequencyOfConditions = new
 *         int[size]; for (Location cabin : this.locations) { switch
 *         (cabin.getCondition()) { case BAD: frequencyOfConditions[0]++; break;
 *         case FAIR: frequencyOfConditions[1]++; break; case GOOD:
 *         frequencyOfConditions[2]++; break; case IN_SHAMBLES:
 *         frequencyOfConditions[3]++; break; case PERFECT:
 *         frequencyOfConditions[4]++; break; case UNKNOWN:
 *         frequencyOfConditions[5]++; break; default: break; } } return
 *         frequencyOfConditions; }
 * 
 *         /** Returns the details of one cabin.
 * 
 * @param cab
 *            The cabin which details should be returned
 * @return A stringbuilder with formatted output.
 * 
 *         private StringBuilder getCabinDetails(Location cab) { StringBuilder
 *         sb = new StringBuilder(); Formatter formatter = new Formatter(sb,
 *         Locale.UK); formatter .format(
 *         "%1$10s | %2$15s | %3$20s | %4$15s | %5$5s | %6$5s | %7$5s | %8$5s %n"
 *         , "NUMBER", "OWNER", "FACILITIES", "CONDITION", "BEDS", "ROOMS",
 *         "SIZE", "COST"); formatter .format(
 *         "%1$10d | %2$15s | %3$20s | %4$15s | %5$5d | %6$5d | %7$5.2f | %8$5.2f %n"
 *         , cab.getCabinNumber(), cab.getOwner() .getFirstAndLastName(),
 *         cab.getFacilities() .toString().toLowerCase(), cab.getCondition()
 *         .toString().toLowerCase(), cab.getBeds(), cab
 *         .getNumberOfBeds().length, cab.getSize(), cab .getCost());
 *         formatter.format("%n"); return sb; }
 * 
 *         /** Returns the details about all cabins
 * 
 * @return A stringbuilder with formatted output.
 * 
 *         private StringBuilder getAllCabinDetails() { StringBuilder sb = new
 *         StringBuilder(); Formatter formatter = new Formatter(sb, Locale.UK);
 *         formatter.format("%1$10s | %2$10s | %3$20s | %4$5s %n", "NUMBER",
 *         "OWNER", "FACILITIES", "BEDS"); for (Location cab : this.locations) {
 *         // TODO Return the initials of the owner.
 *         formatter.format("%1$10d | %2$10s | %3$20s | %4$5d %n",
 *         cab.getCabinNumber(), cab.getOwner().getInitials(), cab
 *         .getFacilities().toString().toLowerCase(), cab.getBeds()); }
 *         formatter.format("%n"); return sb; }
 * 
 *         /** Acquires all information from the reports and prints the to a
 *         file.
 * 
 *         public void printReportsToFile() { String printString = "";
 *         printString += "OVERVIEW OF CABIN DETAILS:\n\n"; StringBuilder sb =
 *         getAllCabinDetails(); printString += sb.toString(); printString +=
 *         "SINGLE CABIN INFORMATION: \n\n"; for (Location cab : this.locations)
 *         { StringBuilder db = this.getCabinDetails(cab); printString +=
 *         db.toString(); } try { printString += "MOST EXPENSIVE CABIN: " +
 *         this.getExpensiveCabinCost() + "\n\n"; printString +=
 *         "CHEAPEEST CABIN: " + this.getCheapestCabinCost() + "\n\n"; } catch
 *         (NoCabinsException e) { // TODO Auto-generated catch block
 *         e.printStackTrace(); } printString += "MAXIMUM INCOME PER NIGHT: " +
 *         this.getMaximumPossibleIncome() + "\n\n"; printString +=
 *         "CONDITION REPORT: \n\n"; printString +=
 *         this.getConditionReportPrint().toString();
 *         cfh.writeToFile(printString); }
 * 
 *         /** Prints the details of one cabin.
 * 
 * @param cab
 *            The cabin which details should be printed.
 * 
 *            public void printCabDetails(Location cab) { StringBuilder sb =
 *            getCabinDetails(cab); System.out.println(sb.toString()); }
 * 
 *            /** Prints the details for all cabins to the standard output.
 * 
 *            public void printAllCabins() { StringBuilder sb =
 *            getAllCabinDetails(); System.out.println(sb.toString()); }
 * 
 *            /** Prints the details of a specific cabin that is specified by
 *            its cabin number.
 * 
 * @param id
 *            The cabinnumber of the cabin whose details should be printed.
 * 
 *            public void printDetailsForCabinNumber(String id) { try { Location
 *            cab = this.findLocationById(id); this.printCabDetails(cab); }
 *            catch (CabinNotFoundException e) { System.out .println(String
 *            .format
 *            ("Could not find the cabin for number %d. No details printed.",
 *            id)); } }
 */
