package uk.heriotwatt.sef.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to seperate the pricing mapping from the information of the cabin.
 * 
 * @author florian
 *
 */
public class PriceMapping {
	
	private Map<Condition, Double> conditionPrices;
	private Map<Facilities, Double> facilityPrices;

	public Map<Condition, Double> getConditionPrices() {
		return conditionPrices;
	}

	public Map<Facilities, Double> getFacilityPrices() {
		return facilityPrices;
	}

	public PriceMapping(int bED_TO_ROOM_RATIO_MULTIPLIER) {
		this.initializeConditionPriceMapping();
		this.initializeFacilityPriceMapping();
	}
	
	/*
	 * Getters and setters
	 */
	
	public double getFacilityPrice(Facilities facilities) {
		return facilityPrices.get(facilities);
	}

	public double getConditionPrice(Condition condition) {
		return conditionPrices.get(condition);
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
	
	/**
	 * Return the size modifier that can be used to calculate a price for a cabin.
	 * 
	 * @param size The size of the cabin.
	 * @return The size modifier according to the provided size of a room.
	 */
	double getSizeModifier(double size)
	{
		if (size < 20) {
			return PriceList.BUDGET.cost();
		}
		else if (size >= 20 && size < 30) {
			return PriceList.CHEAP.cost();
		}
		else if (size >= 30 && size < 40)
		{
			return PriceList.FAIR.cost();
		}
		else if (size >= 40 && size < 50)
		{
			return PriceList.EXPENSIVE.cost();
		}
		else
		{
			return PriceList.VERY_EXPENSIVE.cost();
		}
	}
}