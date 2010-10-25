package uk.heriotwatt.sef.model;

/**
 * Stores the price modifiers for certain discrete price categories.
 * 
 * @author fhb2
 * 
 */
public enum PriceList {

	VERY_EXPENSIVE(10.0), EXPENSIVE(7.5), FAIR(5.0), BUDGET(2.5), CHEAP(1.0), UNKNOWN(
			0.0);

	private final double cost;

	private PriceList(double cost) {
		this.cost = cost;
	}

	public double cost() {
		return this.cost;
	}

}
