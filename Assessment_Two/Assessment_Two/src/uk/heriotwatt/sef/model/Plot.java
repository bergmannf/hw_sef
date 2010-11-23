package uk.heriotwatt.sef.model;

public class Plot extends Location {
	
	private boolean hasElectricity;
	
	/**
	 * Initializes a new instance of the plot class with the provided attributes.
	 * 
	 * @param plotNumber The plot number.
	 * @param hasElectricity The boolean to determine wether the plot has an electric outlet or not.
	 * @param area The area provided to the traveller.
	 */
	public Plot(String plotNumber, boolean hasElectricity, double area) {
		super(plotNumber, area);
		this.hasElectricity = hasElectricity;
	}
	
	public boolean isHasElectricity() {
		return hasElectricity;
	}
	
	public void setHasElectricity(boolean hasElectricity) {
		this.hasElectricity = hasElectricity;
	}
	
	public double getCost()
	{
		return 0.0;
	}
	
	public String toString()
	{
		String string = String.format("Id: %s\nElectricty: %b\nCost:%d", this.getId(), this.isHasElectricity(), this.getCost());
		return string;
	}
}