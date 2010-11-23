package uk.heriotwatt.sef.model;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class CabinManager {

	private List<Cabin> cabins;

	private CabinFileHandler cfh;

	/**
	 * Creates a new cabin manager object from the provided arguments.
	 * 
	 * @param cfh
	 *            The filehandler that loads cabins from a file and saves
	 *            reports to a file.
	 */
	public CabinManager(CabinFileHandler cfh) {
		this.cabins = new ArrayList<Cabin>();
		this.cfh = cfh;
		this.cabins = this.cfh.readFromFile();
	}

	public void addCabin(Cabin cab) {
		this.cabins.add(cab);
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
			if (cabin.getCabinNumber() == cabinNumber) {
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
	 * Retrieves that cabin at the specified position in the cabin list.
	 * 
	 * @param index
	 *            The position for which the cabin should be returned.
	 * @return Tha cabin.
	 */
	public Cabin getCabinAtIndex(int index) {
		if (index < this.getNumberOfCabins()) {
			return this.cabins.get(index);
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
		if (this.cabins.size() > 0) {
			Cabin expensiveCab = null;
			for (Cabin cab : this.cabins) {
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
		return this.cabins.size();
	}

	/**
	 * Acquires all information from the reports and prints the to a file.
	 */
	public void printReportsToFile() {
		String printString = "";
		printString += "OVERVIEW OF CABIN DETAILS:\n\n";
		StringBuilder sb = getAllCabinDetails();
		printString += sb.toString();
		printString += "SINGLE CABIN INFORMATION: \n\n";
		for (Cabin cab : this.cabins) {
			StringBuilder db = this.getCabinDetails(cab);
			printString += db.toString();
		}
		try {
			printString += "MOST EXPENSIVE CABIN: "
					+ this.getExpensiveCabinCost() + "\n\n";
			printString += "CHEAPEST CABIN: " + this.getCheapestCabinCost()
					+ "\n\n";
		} catch (NoCabinsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printString += "MAXIMUM INCOME PER NIGHT: "
				+ this.getMaximumPossibleIncome() + "\n\n";
		printString += "CONDITION REPORT: \n\n";
		printString += this.getConditionReportPrint().toString();
		cfh.writeToFile(printString);
	}

	/**
	 * Prints the details of one cabin.
	 * 
	 * @param cab
	 *            The cabin which details should be printed.
	 */
	public void printCabDetails(Cabin cab) {
		StringBuilder sb = getCabinDetails(cab);
		System.out.println(sb.toString());
	}

	/**
	 * Prints the details for all cabins to the standard output.
	 */
	public void printAllCabins() {
		StringBuilder sb = getAllCabinDetails();
		System.out.println(sb.toString());
	}

	/**
	 * Prints the details of a specific cabin that is specified by its cabin
	 * number.
	 * 
	 * @param cabinNumber
	 *            The cabin number of the cabin whose details should be printed.
	 */
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

	/**
	 * Returns a formatted condition report.
	 * 
	 * @return A stringbuilder containing the formatted condition report.
	 */
	public StringBuilder getConditionReportPrint() {
		int[] conRep = getConditionReport();
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.UK);
		formatter.format("%1$15s | %2$15s | %3$15s | %4$15s | %5$15s %n",
				Condition.BAD.toString(), Condition.FAIR.toString(),
				Condition.GOOD.toString(), Condition.IN_SHAMBLES.toString(),
				Condition.PERFECT.toString(), Condition.UNKNOWN.toString());
		formatter.format("%1$15d | %2$15d | %3$15d | %4$15d | %5$15d %n",
				conRep[0], conRep[1], conRep[2], conRep[3], conRep[4],
				conRep[5]);
		formatter.format("%n");
		return sb;
	}

	/**
	 * Returns the values of the condition report.
	 * 
	 * @return String array containing the number of cabins of a certain
	 *         condition.
	 */
	public int[] getConditionReport() {
		int size = Condition.values().length;
		int[] frequencyOfConditions = new int[size];
		for (Cabin cabin : this.cabins) {
			switch (cabin.getCondition()) {
			case BAD:
				frequencyOfConditions[0]++;
				break;
			case FAIR:
				frequencyOfConditions[1]++;
				break;
			case GOOD:
				frequencyOfConditions[2]++;
				break;
			case IN_SHAMBLES:
				frequencyOfConditions[3]++;
				break;
			case PERFECT:
				frequencyOfConditions[4]++;
				break;
			case UNKNOWN:
				frequencyOfConditions[5]++;
				break;
			default:
				break;
			}
		}
		return frequencyOfConditions;
	}

	/**
	 * Returns the details of one cabin.
	 * 
	 * @param cab
	 *            The cabin which details should be returned
	 * @return A stringbuilder with formatted output.
	 */
	private StringBuilder getCabinDetails(Cabin cab) {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.UK);
		formatter
				.format("%1$10s | %2$15s | %3$20s | %4$15s | %5$5s | %6$5s | %7$5s | %8$5s %n",
						"NUMBER", "OWNER", "FACILITIES", "CONDITION", "BEDS",
						"ROOMS", "SIZE", "COST");
		formatter
				.format("%1$10d | %2$15s | %3$20s | %4$15s | %5$5d | %6$5d | %7$5.2f | %8$5.2f %n",
						cab.getCabinNumber(), cab.getOwner()
								.getFirstAndLastName(), cab.getFacilities()
								.toString().toLowerCase(), cab.getCondition()
								.toString().toLowerCase(), cab.getBeds(), cab
								.getNumberOfBeds().length, cab.getSize(), cab
								.getCost());
		formatter.format("%n");
		return sb;
	}

	/**
	 * Returns the details about all cabins
	 * 
	 * @return A stringbuilder with formatted output.
	 */
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
		formatter.format("%n");
		return sb;
	}
}
