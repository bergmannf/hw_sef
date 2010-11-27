package uk.heriotwatt.sef.model;

import java.util.Formatter;
import java.util.Locale;

import org.apache.log4j.Logger;

import uk.heriotwatt.sef.model.interfaces.ICsvSerialisable;

public class Plot extends Location implements ICsvSerialisable<Plot> {

	private static final int ELECTRICITY_SURPLUS = 10;

	private final Logger logger = Logger.getLogger(getClass());

	private boolean hasElectricity;

	/**
	 * Initializes a new instance of the plot class with the provided
	 * attributes.
	 * 
	 * @param plotNumber
	 *            The plot number.
	 * @param hasElectricity
	 *            The boolean to determine wether the plot has an electric
	 *            outlet or not.
	 * @param area
	 *            The area provided to the traveller.
	 */
	public Plot(String plotNumber, boolean hasElectricity, double area,
			int bookings) {
		super(plotNumber, area, bookings);
		this.hasElectricity = hasElectricity;
	}

	public Plot() {
		super("", 0, 0);
	}

	public boolean isHasElectricity() {
		return hasElectricity;
	}

	public void setHasElectricity(boolean hasElectricity) {
		this.hasElectricity = hasElectricity;
	}

	public double getCost() {
		PriceMapping pm = new PriceMapping();
		double cost = pm.getSizeModifier(this.getSize());
		if (this.isHasElectricity()) {
			cost += ELECTRICITY_SURPLUS;
		}
		return cost;
	}

	public String toString() {
		String string = String.format("Id: %s\nElectricty: %b\nCost:%d",
				this.getId(), this.isHasElectricity(), this.getCost());
		return string;
	}

	@Override
	public String csvRepresentation() {
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb, Locale.UK);
		formatter.format("%s,%f,%b,%d", this.getId(),
				this.getSize(), this.isHasElectricity(), this.getBookings());
		return sb.toString();
	}

	@Override
	public Plot createFromString(String string) {
		Plot plot = null;
		String[] splitList = string.split(",");

		String plotNumber = "";
		double size = 0;
		int bookings = 0;
		boolean hasElectricity = false;
		plotNumber = splitList[0];
		try {
			size = Double.parseDouble(splitList[1]);
		} catch (NumberFormatException e) {
			logger.error("Could not parse plot size. Argument was: "
					+ splitList[1]);
		}
		hasElectricity = Boolean.parseBoolean(splitList[2]);
		try {
			bookings = Integer.parseInt(splitList[3]);
		} catch (NumberFormatException e) {
			logger.error("Could not parse bookings. Argument was: "
					+ splitList[3]);
		}
		plot = new Plot(plotNumber, hasElectricity, size, bookings);
		return plot;
	}
}