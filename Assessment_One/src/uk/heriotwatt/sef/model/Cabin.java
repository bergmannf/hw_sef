package uk.heriotwatt.sef.model;

import java.util.Date;

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
	public Date buildDate;
	public Condition condition;
	
	private final double MINIMUM_COST = 10;
	
	private PriceManager data = new PriceManager(5);

	public Cabin()
	{
	}

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
			if (bedsInArray > 1 && bedsInArray < 9) {
				this.numberOfBeds = numberOfBeds;
			} else {
				throw new IllegalArgumentException("Only between 2 and 8 beds can be placed in a cabin.");
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
		if (size >=0 ) {
			this.size = size;
		}
		else {
			throw new IllegalArgumentException("Size must be positive.");
		}
	}

	public double getCost() {
		double minimalCost = MINIMUM_COST;
		
		double conditionModifier = this.data.getConditionPrice(this.condition);
		double faciltiesModifier = this.data.getFacilityPrice(this.facilities);
		double sizeModifier = this.data.getSizeModifier(this.size);
		double bedToRoomRatio = this.calculateBedToRoomRatio();
		
		minimalCost = minimalCost + conditionModifier + faciltiesModifier + sizeModifier + (data.BED_TO_ROOM_RATIO_MULTIPLIER * bedToRoomRatio);
		
		return minimalCost;
	}

	private int calculateNumberOfBeds(int[] numberOfBeds2) {
		int result = 0;
		for (int i : numberOfBeds2) {
			result += i;
		}
		return result;
	}
	
	private double calculateBedToRoomRatio()
	{
		int rooms = this.getNumberOfBeds().length;
		int beds = this.calculateNumberOfBeds(this.numberOfBeds);
		double bedToRoomRatio = beds / rooms;
		return bedToRoomRatio;
	}

}
