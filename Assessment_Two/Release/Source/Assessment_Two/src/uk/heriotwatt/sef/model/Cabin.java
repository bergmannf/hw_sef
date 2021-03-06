package uk.heriotwatt.sef.model;

import java.util.Formatter;
import java.util.Locale;

import uk.heriotwatt.sef.model.interfaces.ICsvSerialisable;

/**
 * Stores values associated with a cabin.
 * 
 * @author fhb2
 * 
 */
public class Cabin extends Location implements ICsvSerialisable<Cabin> {

	private int[] beds;
	private Facilities facilities;
	private Name owner;
	private Condition condition;

	private PriceMapping data;

	private final int MINIMUM_NUMBER_OF_BEDS = 2;
	private final int MAXIMUM_NUMBER_OF_BEDS = 8;
	private final double BASIC_COST = 10;
	private final int BED_TO_ROOM_RATIO_MULTIPLIER = 5;

	public Cabin() {
		super(null, 0, 0);
		this.data = new PriceMapping();
	}

	/**
	 * Creates a new cabin object with the specified values.
	 * 
	 * @param cabinNumber
	 *            The cabin number.
	 * @param beds
	 *            The array of beds in the cabin..
	 * @param size
	 *            The size of the cabin.
	 * @param facilities
	 *            The facilites of the cabin.
	 * @param owner
	 *            The owner of the cabin.
	 * @param condition
	 *            The condition of the cabin.
	 * @param bookings 
	 */
	public Cabin(String cabinNumber, int[] beds, double size,
			Facilities facilities, Name owner, Condition condition, int bookings) {
		super(cabinNumber, size, bookings);
		this.beds = beds;
		this.facilities = facilities;
		this.owner = owner;
		this.condition = condition;
		this.data = new PriceMapping();
	}

	/**
	 * @return Array of beds.
	 */
	public int[] getNumberOfBeds() {
		return beds;
	}

	/**
	 * Sets the beds.
	 * 
	 * @param numberOfBeds
	 *            The new array of beds.
	 */
	public void setNumberOfBeds(int[] numberOfBeds) {
		if (numberOfBeds.length > 0) {
			int bedsInArray = this.calculateNumberOfBeds(numberOfBeds);
			if (bedsInArray >= MINIMUM_NUMBER_OF_BEDS
					&& bedsInArray <= MAXIMUM_NUMBER_OF_BEDS) {
				this.beds = numberOfBeds;
			} else {
				throw new IllegalArgumentException(
						String.format(
								"Only between %d and %d beds can be placed in a cabin.",
								MINIMUM_NUMBER_OF_BEDS, MAXIMUM_NUMBER_OF_BEDS));
			}
		} else {
			throw new IllegalArgumentException(
					"The number of beds must be greater than 0.");
		}
	}

	/**
	 * @return The facilities of the cabin.
	 */
	public Facilities getFacilities() {
		return facilities;
	}

	/**
	 * Attempts to set the facilites of the cabin.
	 * 
	 * @param facilities
	 */
	public void setFacilities(Facilities facilities) {
		this.facilities = facilities;
	}

	/**
	 * @return The owner of the cabin.
	 */
	public Name getOwner() {
		return owner;
	}

	/**
	 * Sets the owner of the cabin.
	 * 
	 * @param owner
	 */
	public void setOwner(Name owner) {
		this.owner = owner;
	}

	/**
	 * @return The size of the cabin.
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Sets the size of the cabin.
	 * 
	 * @param size
	 *            The new size (must be bigger than 0)
	 */
	public void setSize(double size) {
		if (size >= 0) {
			this.size = size;
		} else {
			throw new IllegalArgumentException("Size must be positive.");
		}
	}

	/**
	 * The cost is calculated based on different factors: - The condition. - The
	 * facilities. - The size. - The beds/rooms present (The less beds per room
	 * the more expensive). The values associated with the first three are
	 * stored in {@link PriceMapping}
	 * 
	 * @return The cost if the cabin.
	 */
	public double getCost() {
		double cost = BASIC_COST;

		double conditionModifier = this.data.getConditionPrice(this.condition);
		double faciltiesModifier = this.data.getFacilityPrice(this.facilities);
		double sizeModifier = this.data.getSizeModifier(this.size);
		double bedToRoomRatio = this.calculateRoomToBedRatio();

		cost = BASIC_COST + conditionModifier + faciltiesModifier
				+ sizeModifier
				+ (BED_TO_ROOM_RATIO_MULTIPLIER * bedToRoomRatio);

		return cost;
	}

	/**
	 * @return The condition.
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * Sets the condition of the cabin.
	 * 
	 * @param condition
	 *            New condition to be set.
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * @return The number of beds in the cabin.
	 */
	public int getBeds() {
		return this.calculateNumberOfBeds(this.beds);
	}

	/**
	 * Calculates the room to bed ratio.
	 * 
	 * @return The room to bed ratio.
	 */
	public double calculateRoomToBedRatio() {
		int rooms = this.getNumberOfBeds().length;
		int beds = this.calculateNumberOfBeds(this.beds);
		double bedToRoomRatio = rooms / beds;
		return bedToRoomRatio;
	}

	public String toString() {
		String string = String
				.format("Number: %s\nCondition: %s\nFacilities: %s\nOwner: %s\nNumer of beds: %d",
						this.getId(), this.getCondition(),
						this.getFacilities(), this.owner.toString(),
						this.getBeds());
		return string;
	}

	private int calculateNumberOfBeds(int[] numberOfBeds) {
		int result = 0;
		for (int i : numberOfBeds) {
			result += i;
		}
		return result;
	}

	@Override
	public String csvRepresentation() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.UK);
		formatter.format("%s,%f,%s,%s,%s,%s,%s,%d", this.getId(), this
				.getSize(), this.facilities.toString(), this.condition
				.toString(), this.getOwner().getFirstName(), this.getOwner()
				.getMiddleName(), this.getOwner().getLastName(), this.getBookings());
		for (int i : this.beds)
		{
			formatter.format(",%d", i);
		}
		return sb.toString();
	}

	@Override
	public Cabin createFromString(String string) {
		Cabin cabin = null;
		String[] splitList = string.split(",");
		String errorString = "";

		String cabinNumber = "";
		double size = 0;
		Facilities facilities = null;
		Condition condition = null;
		Name name;
		int bookings = 0;
		int[] beds;
		try {
			cabinNumber = splitList[0];
		} catch (NumberFormatException e) {
			errorString += splitList[0];
		}
		try {
			size = Double.parseDouble(splitList[1]);
		} catch (NumberFormatException e) {
			errorString += ", " + splitList[1];
		}
		try {
			facilities = Facilities.valueOf(splitList[2]);
		} catch (IllegalArgumentException e) {
			errorString += ", " + splitList[2];
		}
		try {
			condition = Condition.valueOf(splitList[3]);
		} catch (IllegalArgumentException e) {
			errorString += ", " + splitList[3];
		}
		name = new Name(splitList[4], splitList[5], splitList[6]);
		try {
			bookings = Integer.parseInt(splitList[7]);
		} catch (IllegalArgumentException e) {
			errorString += ", " + splitList[7];
		}
		beds = new int[splitList.length - 8];
		for (int i = 8; i < splitList.length; i++) {
			try {
				beds[i - 8] = Integer.parseInt(splitList[i]);
			} catch (Exception e) {
				errorString += ", " + beds[i - 8];
			}
		}
		if (errorString.length() == 0) {
			cabin = new Cabin(cabinNumber, beds, size, facilities, name,
					condition, bookings);
			return cabin;
		} else {
			throw new IllegalArgumentException(
					String.format(
							"There were errors parsing the line:\n%s\nThe following arguments were violating the format:%s",
							string, errorString));
		}
	}
}
