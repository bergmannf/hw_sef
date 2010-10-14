package uk.heriotwatt.sef.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class CabinManager {

	private List<Cabin> cabins;
	
	private CabinFileHandler cfh;

	public CabinManager() {
		this.cabins = new ArrayList<Cabin>();
		cfh = new CabinFileHandler("./Cabins.csv", "./CabinReports.txt");
	}

	public void addCabin(Cabin cab) {
		this.cabins.add(cab);
	}

	public Cabin getCabinAtIndex(int index) {
		return this.cabins.get(index);
	}

	/**
	 * Attempts to find a cabin with the provided cabinNumber in the cabin-List.
	 * 
	 * @param cabinNumber
	 *            The cabinnumber of the cabin to be returned
	 * @return The first cabin in the list with the corresponding cabinnumber.
	 * @throws CabinNotFoundException
	 *             If no cabin with the provided number could be found.
	 */
	public Cabin findCabinByCabinNumber(int cabinNumber)
			throws CabinNotFoundException {
		Cabin cabinFound = null;
		for (Cabin cabin : this.cabins) {
			if (cabin.cabinNumber == cabinNumber) {
				cabinFound = cabin;
				break;
			}
		}
		if (cabinFound != null) {
			return cabinFound;
		} else {
			throw new CabinNotFoundException(String.format(
					"The cabin with number %d was not in the list.",
					cabinNumber));
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
		for (Cabin cabin : this.cabins) {
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
		if (this.cabins.size() > 0) {
			Cabin cheapestCab = null;
			for (Cabin cab : this.cabins) {
				if (cheapestCab == null) {
					cheapestCab = cab;
				}
				if (cab.getCost() < cheapestCab.getCabinNumber()) {
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
		if (this.cabins.size() > 0) {
			Cabin expensiveCab = null;
			for (Cabin cab : this.cabins) {
				if (expensiveCab == null) {
					expensiveCab = cab;
				}
				if (cab.getCost() > expensiveCab.getCabinNumber()) {
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
		return this.cabins.size();
	}

	public void printDetailsForCabinNumber(int cabinNumber) {
		try {
			Cabin cab = this.findCabinByCabinNumber(cabinNumber);
			this.printCabDetails(cab);
		} catch (CabinNotFoundException e) {
			System.out
					.println(String
							.format("Could not find the cabin for number %d. No details printed.",
									cabinNumber));
		}
	}

	public void printAllCabins() {
		StringBuilder sb = getAllCabinDetails();
		System.out.println(sb.toString());
	}
	
	public void printReportsToFile()
	{
		String printString = "";
		StringBuilder sb = getAllCabinDetails();
		printString = sb.toString();
		for (Cabin cab : this.cabins) {
			StringBuilder db = this.getCabinDetails(cab);
			printString += db.toString();
		}
		cfh.writeToFile(printString);
	}

	private StringBuilder getAllCabinDetails() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.UK);
		formatter.format("%1$10s | %2$10s | %3$20s | %4$5s %n", "NUMBER",
				"OWNER", "FACILITIES", "BEDS");
		for (Cabin cab : this.cabins) {
			// TODO Return the initials of the owner.
			formatter.format("%1$10d | %2$10s | %3$20s | %4$5d %n",
					cab.getCabinNumber(), cab.getOwner().getInitials(), cab
							.getFacilities().toString().toLowerCase(),
					cab.getBeds());
		}
		return sb;
	}

	public void printCabDetails(Cabin cab) {
		StringBuilder sb = getCabinDetails(cab);
		System.out.println(sb.toString());
	}

	private StringBuilder getCabinDetails(Cabin cab) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.UK);
		formatter.format(
				"%1$10s | %2$15s | %3$20s | %4$15s | %5$5s | %6$5s | %7$5s %n",
				"NUMBER", "OWNER", "FACILITIES", "CONDITION", "BEDS", "ROOMS", "COST");
		formatter.format(
				"%1$10d | %2$15s | %3$20s | %4$15s | %5$5d | %6$5d | %7$5.2f %n",
				cab.getCabinNumber(), cab.getOwner().getFirstAndLastName(), cab
						.getFacilities().toString().toLowerCase(), cab
						.getCondition().toString().toLowerCase(), 
				cab.getBeds(), cab.getNumberOfBeds().length, cab.getCost());
		return sb;
	}

}
