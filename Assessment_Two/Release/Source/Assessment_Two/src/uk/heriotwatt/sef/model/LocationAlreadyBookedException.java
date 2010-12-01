package uk.heriotwatt.sef.model;

public class LocationAlreadyBookedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3180293198375887403L;

	public LocationAlreadyBookedException(String message)
	{
		super(message);
	}
}
