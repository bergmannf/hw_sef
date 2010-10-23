package uk.heriotwatt.sef.model;

/**
 * Stores values associated with a cabin.
 * 
 * @author fhb2
 * 
 */
public class Cabin {

	public int cabinNumber;
	public int[] numberOfBeds;
	public double size;
	public Facilities facilities;
	public Name owner;
	public Condition condition;

	private final int MINIMUM_NUMBER_OF_BEDS = 2;
	private final int MAXIMUM_NUMBER_OF_BEDS = 8;
	private final double BASIC_COST = 10;
	public final int BED_TO_ROOM_RATIO_MULTIPLIER = 5;

	private PriceMapping data;

	public Cabin() {
		this.data = new PriceMapping();
	}
	
	public Cabin(int cabinNumber, int[] numberOfBeds, double size,
			Facilities facilities, Name owner, Condition condition) {
		super();
		this.cabinNumber = cabinNumber;
		this.numberOfBeds = numberOfBeds;
		this.size = size;
		this.facilities = facilities;
		this.owner = owner;
		this.condition = condition;
		this.data = new PriceMapping();
	}

	/*
	 * Getters and setters
	 */

	public int getCabinNumber() {
		return cabinNumber;
	}

	public void setCabinNumber(int cabinNumber) {
		this.cabinNumber = cabinNumber;
	}

	public int[] getNumberOfBeds() {
		return numberOfBeds;
	}

	public void setNumberOfBeds(int[] numberOfBeds) {
		if (numberOfBeds.length > 0) {
			int bedsInArray = this.calculateNumberOfBeds(numberOfBeds);
			if (bedsInArray >= MINIMUM_NUMBER_OF_BEDS
					&& bedsInArray <= MAXIMUM_NUMBER_OF_BEDS) {
				this.numberOfBeds = numberOfBeds;
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

	public Facilities getFacilities() {
		return facilities;
	}

	public void setFacilities(Facilities facilities) {
		this.facilities = facilities;
	}

	public Name getOwner() {
		return owner;
	}

	public void setOwner(Name owner) {
		this.owner = owner;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		if (size >= 0) {
			this.size = size;
		} else {
			throw new IllegalArgumentException("Size must be positive.");
		}
	}

	public double getCost() {
		double cost = BASIC_COST;

		double conditionModifier = this.data.getConditionPrice(this.condition);
		double faciltiesModifier = this.data.getFacilityPrice(this.facilities);
		double sizeModifier = this.data.getSizeModifier(this.size);
		double bedToRoomRatio = this.calculateBedToRoomRatio();

		cost = BASIC_COST + conditionModifier + faciltiesModifier
				+ sizeModifier
				+ (BED_TO_ROOM_RATIO_MULTIPLIER * bedToRoomRatio);

		return cost;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	public int getBeds()
	{
		return this.calculateNumberOfBeds(this.numberOfBeds);
	}

	private int calculateNumberOfBeds(int[] numberOfBeds) {
		int result = 0;
		for (int i : numberOfBeds) {
			result += i;
		}
		return result;
	}

	public double calculateBedToRoomRatio() {
		int rooms = this.getNumberOfBeds().length;
		int beds = this.calculateNumberOfBeds(this.numberOfBeds);
		double bedToRoomRatio = beds / rooms;
		return bedToRoomRatio;
	}

}
