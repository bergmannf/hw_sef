package uk.heriotwatt.sef.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	
	private final int BED_TO_ROOM_RATIO_MULTIPLIER = 5;
	private final double MINIMUM_COST = 10;
	
	private Map<Condition, Double> conditionPrices;
	private Map<Facilities, Double> facilityPrices;
	
	public Cabin()
	{
		this.initializeConditionPriceMapping();
		this.initializeFacilityPriceMapping();
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
		
		double conditionModifier = this.conditionPrices.get(this.condition);
		double faciltiesModifier = this.facilityPrices.get(this.facilities);
		double sizeModifier = this.getSizeModifier();
		double bedToRoomRatio = this.calculateBedToRoomRation();
		
		minimalCost = minimalCost + conditionModifier + faciltiesModifier + sizeModifier + (bedToRoomRatio * BED_TO_ROOM_RATIO_MULTIPLIER);
		return minimalCost;
	}

	private int calculateNumberOfBeds(int[] numberOfBeds2) {
		int result = 0;
		for (int i : numberOfBeds2) {
			result += i;
		}
		return result;
	}
	
	/**
	 * Adds Condition - Price pairs to a map.
	 * Will be used in the getCost() method.
	 */
	private void initializeConditionPriceMapping() {
		this.conditionPrices = new HashMap<Condition, Double>();
		this.conditionPrices.put(Condition.PERFECT, PriceList.VERY_EXPENSIVE.cost());
		this.conditionPrices.put(Condition.GOOD, PriceList.EXPENSIVE.cost());
		this.conditionPrices.put(Condition.FAIR, PriceList.FAIR.cost());
		this.conditionPrices.put(Condition.BAD, PriceList.BUDGET.cost());
		this.conditionPrices.put(Condition.IN_SHAMBLES, PriceList.CHEAP.cost());
	}
	
	/**
	 * Adds Facilities - Price pairs to a map.
	 * Will be used in the getCost() method.
	 */
	private void initializeFacilityPriceMapping() {
		this.facilityPrices = new HashMap<Facilities, Double>();
		this.facilityPrices.put(Facilities.EN_SUITE, PriceList.VERY_EXPENSIVE.cost());
		this.facilityPrices.put(Facilities.SEPERATE_BATHROOM, PriceList.FAIR.cost());
		this.facilityPrices.put(Facilities.GENERAL_FACILITES, PriceList.BUDGET.cost());
	}
	
	private double getSizeModifier()
	{
		if (this.size < 20) {
			return PriceList.BUDGET.cost();
		}
		else if (this.size >= 20 && this.size < 30) {
			return PriceList.CHEAP.cost();
		}
		else if (this.size >= 30 && this.size < 40)
		{
			return PriceList.FAIR.cost();
		}
		else if (this.size >= 40 && this.size < 50)
		{
			return PriceList.EXPENSIVE.cost();
		}
		else
		{
			return PriceList.VERY_EXPENSIVE.cost();
		}
	}
	
	private double calculateBedToRoomRation()
	{
		int rooms = this.getNumberOfBeds().length;
		int beds = this.calculateNumberOfBeds(this.numberOfBeds);
		double bedToRoomRatio = beds / rooms;
		return bedToRoomRatio;
	}

}
