package uk.heriotwatt.sef.model;

/**
 * Stores the values associated with a cabin.
 * 
 * @author Florian Bergmann
 * 
 */
public class Cabin {

	private int cabinNumber;
	private int[] beds;
	private double size;
	private Facilities facilities;
	private Name owner;
	private Condition condition;

	private PriceMapping data;

	private final int MINIMUM_NUMBER_OF_BEDS = 2;
	private final int MAXIMUM_NUMBER_OF_BEDS = 8;
	private final double BASIC_COST = 10;
	private final int BED_TO_ROOM_RATIO_MULTIPLIER = 5;

	/**
	 * Creates a new cabin object without values.
	 */
	public Cabin() {
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
	 */
	public Cabin(int cabinNumber, int[] beds, double size,
			Facilities facilities, Name owner, Condition condition) {
		super();
		this.cabinNumber = cabinNumber;
		this.beds = beds;
		this.size = size;
		this.facilities = facilities;
		this.owner = owner;
		this.condition = condition;
		this.data = new PriceMapping();
	}

	/*
	 * Getters and setters
	 */

	/**
	 * Returns the cabin number.
	 * 
	 * @return The number of cabins stored in the manager.
	 */
	public int getCabinNumber() {
		return cabinNumber;
	}

	/**
	 * Sets the cabin number.
	 * 
	 * @param cabinNumber
	 *            Number to be set.
	 */
	public void setCabinNumber(int cabinNumber) {
		this.cabinNumber = cabinNumber;
	}

	/**
	 * Returns the beds of the cabin as array. Each array-cell represents a
	 * room, each value of a cell the number of beds in the room.
	 * 
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
	 * Returns the facilities of the cabin.
	 * 
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
	 * Returns the owner's name.
	 * 
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
	 * Returns the size of the cabin.
	 * 
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
	 * Returns the cabins condition.
	 * 
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
	 * Returns the number of beds in a cabin.
	 * 
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

	private int calculateNumberOfBeds(int[] numberOfBeds) {
		int result = 0;
		for (int i : numberOfBeds) {
			result += i;
		}
		return result;
	}
}
